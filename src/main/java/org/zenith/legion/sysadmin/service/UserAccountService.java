package org.zenith.legion.sysadmin.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zenith.legion.common.utils.DateUtils;
import org.zenith.legion.common.utils.StringUtils;
import org.zenith.legion.sysadmin.dao.UserAccountDAO;
import org.zenith.legion.sysadmin.entity.UserAccount;
import org.zenith.legion.sysadmin.entity.UserRoleAssign;

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

    public UserAccount getUserAccountByIdNo(String idNo) {
        if (StringUtils.isNotBlank(idNo)) {
            return userAccountDAO.getUserByIdNo(idNo);
        }
        return null;
    }

    public List<UserRoleAssign> getRoleAssignByIdNo(String idNo) {
        if (StringUtils.isNotBlank(idNo)) {
            return userAccountDAO.getUserRoleAssignment(idNo);
        }
        return null;
    }
}
