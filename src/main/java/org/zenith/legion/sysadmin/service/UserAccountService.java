package org.zenith.legion.sysadmin.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zenith.legion.common.AppContext;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.persistant.exec.SQLExecutor;
import org.zenith.legion.common.utils.DateUtils;
import org.zenith.legion.common.utils.MiscGenerator;
import org.zenith.legion.common.utils.StringUtils;
import org.zenith.legion.sysadmin.dao.UserAccountDAO;
import org.zenith.legion.sysadmin.entity.UserAccount;
import org.zenith.legion.sysadmin.entity.UserRole;
import org.zenith.legion.sysadmin.entity.UserRoleAssign;

import java.util.Date;
import java.util.List;

@Service
public class UserAccountService {

    private UserAccountDAO userAccountDAO;

    @Autowired
    public UserAccountService(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    public String encryptPassword(String pwd) {
        if (StringUtils.isNotBlank(pwd)) {
            String hash = pwd;
            for (int i = 0; i < 5; i++) {
                hash = DigestUtils.sha256Hex(hash);
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(hash);
        }
        return pwd;
    }

    public boolean isPasswordMatch(String plaintext, String ciphertext) {
        if (StringUtils.isNotEmpty(plaintext) && StringUtils.isNotEmpty(ciphertext)) {
            String hash = plaintext;
            for (int i = 0; i < 5; i++) {
                hash = DigestUtils.sha256Hex(hash);
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(hash, ciphertext);
        }
        return false;
    }

    public boolean isActive(UserAccount user) {
        if (user != null) {
            long now = DateUtils.now().getTime();
            return user.getActivatedAt().getTime() <= now && user.getDeactivatedAt().getTime() >= now;
        }
        return false;
    }

    public UserAccount getUserByStaffNo(String staffNo) {
        if (StringUtils.isNotBlank(staffNo)) {
            return userAccountDAO.getUserByStaffNo(staffNo);
        }
        return null;
    }

    public List<UserRoleAssign> getActiveRoleAssignById(Long userAcctId) {
        if (userAcctId != null && userAcctId > 0) {
            return userAccountDAO.getActiveUserRoleAssignment(userAcctId);
        }
        return null;
    }

    public UserRole getRoleById(String roleId) {
        if (StringUtils.isNotBlank(roleId)) {
            return userAccountDAO.getRoleById(roleId);
        }
        return null;
    }

    public String createDefaultUser(UserAccount account) {
        String pwd = null;
        if (account != null) {
            if (account.getActivatedAt() == null) {
                account.setActivatedAt(DateUtils.today());
            }
            if (account.getDeactivatedAt() == null) {
                account.setDeactivatedAt(DateUtils.addYear(DateUtils.today(), 3));
            }
            if (StringUtils.isBlank(account.getPassword())) {
                pwd = MiscGenerator.generateInitialPassword();
                account.setPassword(encryptPassword(pwd));
            }
            account.setIsFirstLogin(AppConsts.YES);
            account.setIsNeedChangePwd(AppConsts.YES);
            account.setStatus(AppConsts.ACCOUNT_STATUS_ACTIVE);
            account.setLoginFailedTimes(0);
            SQLExecutor.save(account);
        }
        return pwd;
    }

    public void assignRoles(UserAccount account, List<UserRole> roles) {
        if (account != null && roles != null && !roles.isEmpty()) {
            UserAccount user = userAccountDAO.getUserById(account.getId());
            if (user == null) {
                user = getUserByStaffNo(account.getStaffNo());
            }
            if (user != null) {
                for (UserRole role : roles) {
                    UserRoleAssign assign = new UserRoleAssign();
                    assign.setRoleId(role.getId());
                    assign.setUserAcctId(account.getId());
                    assign.setApprovedBy(AppContext.getFromWebThread().getLoginId());
                    assign.setActivatedAt(account.getActivatedAt());
                    assign.setDeactivatedAt(account.getDeactivatedAt());
                    SQLExecutor.save(assign);
                }
            }
        }
    }
}
