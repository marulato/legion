package org.zenith.legion.sysadmin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zenith.legion.common.AppContext;
import org.zenith.legion.common.SessionManager;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.persistant.exec.SQLExecutor;
import org.zenith.legion.common.utils.ConfigUtils;
import org.zenith.legion.common.utils.DateUtils;
import org.zenith.legion.common.utils.StringUtils;
import org.zenith.legion.sysadmin.consts.LoginStatus;
import org.zenith.legion.sysadmin.entity.UserAccount;
import org.zenith.legion.sysadmin.entity.UserLoginHistory;
import org.zenith.legion.sysadmin.entity.UserRole;
import org.zenith.legion.sysadmin.entity.UserRoleAssign;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class PortalLoginService {

    private final UserAccountService userAcctService;

    @Autowired
    public PortalLoginService(UserAccountService userAcctService) {
        this.userAcctService = userAcctService;
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginStatus login(UserAccount webUser, HttpServletRequest request) {
        LoginStatus status = null;
        if (webUser != null) {
            UserAccount user = userAcctService.getUserAccountByIdNo(webUser.getUserId());
            if (user != null) {
                SessionManager.setSession(request);
                AppContext appContext = new AppContext();
                appContext.setLoginId(user.getUserId());
                appContext.setDomain(user.getDomain());
                AppContext.setWebThreadAppContext(appContext);
                boolean isPwdMatch = userAcctService.isPasswordMatch(webUser.getPassword(), user.getPassword());
                if (isPwdMatch) {
                    String accountStatus = checkStatus(user);
                    if (AppConsts.ACCOUNT_STATUS_ACTIVE.equals(accountStatus)) {
                        status = LoginStatus.SUCCESS;
                        List<UserRoleAssign> userRoleAssigns = userAcctService.getActiveRoleAssignByIdNo(user.getUserId());
                        List<UserRole> roles = new ArrayList<>();
                        for (UserRoleAssign userRoleAssign : userRoleAssigns) {
                            UserRole role = userAcctService.getRoleById(userRoleAssign.getRoleId());
                            roles.add(role);
                        }
                        appContext.setAllRoles(roles);
                    } else if (AppConsts.ACCOUNT_STATUS_INACTIVE.equals(accountStatus)) {
                        status = LoginStatus.ACCOUNT_INACTIVE;
                    } else if (AppConsts.ACCOUNT_STATUS_EXPIRED.equals(accountStatus)) {
                        status = LoginStatus.ACCOUNT_EXPIRED;
                    } else if (AppConsts.ACCOUNT_STATUS_LOCKED.equals(accountStatus)) {
                        status = LoginStatus.ACCOUNT_LOCKED;
                    } else if (AppConsts.ACCOUNT_STATUS_FROZEN.equals(accountStatus)) {
                        status = LoginStatus.ACCOUNT_FROZEN;
                    } else if (AppConsts.ACCOUNT_STATUS_VOIDED.equals(accountStatus)) {
                        status = LoginStatus.VOIDED;
                    }
                } else {
                    status = LoginStatus.INVALID_PASS;
                }
                logLogin(user, status, request);
            } else {
                status = LoginStatus.ACCOUNT_NOT_EXIST;
            }
        }
        return status;
    }


    public void logLogin(UserAccount user, LoginStatus status, HttpServletRequest request) {
        if (user != null && status != null) {
            user.setLastLoginAttemptDt(DateUtils.now());
            user.setLastLoginIp(user.getLastLoginIp());
            if (status == LoginStatus.SUCCESS) {
                user.setLastLoginIp(SessionManager.getIpAddress(request));
                user.setLastLoginSuccessDt(user.getLastLoginAttemptDt());
                user.setLoginFailedTimes(0);
                if (StringUtils.isBlank(user.getIsFirstLogin())) {
                    user.setIsFirstLogin(AppConsts.YES);
                }
            } else if (status == LoginStatus.INVALID_PASS) {
                user.setLoginFailedTimes(user.getLoginFailedTimes() + 1);
                int maxFailedTimes = 0;
                String maxFailedTimesStr = ConfigUtils.get("security.login.maxFailedTimes");
                if (StringUtils.isBlank(maxFailedTimesStr) || !StringUtils.isInteger(maxFailedTimesStr)
                        || Integer.parseInt(maxFailedTimesStr) <= 1 ) {
                    maxFailedTimes = 10;
                }
                if (user.getLoginFailedTimes() >= maxFailedTimes) {
                    user.setStatus(AppConsts.ACCOUNT_STATUS_LOCKED);
                }
            } else {
                user.setLoginFailedTimes(0);
            }
            UserLoginHistory loginHistory = new UserLoginHistory();
            loginHistory.setUserId(user.getUserId());
            loginHistory.setAcctStatus(user.getStatus());
            loginHistory.setIpAddress(user.getLastLoginIp());
            loginHistory.setLoginAt(user.getLastLoginAttemptDt());
            loginHistory.setLoginStatus(status.getValue());
            loginHistory.setIpAddress(SessionManager.getIpAddress(request));
            loginHistory.setBrowser(request.getParameter("browser"));
            SQLExecutor.save(loginHistory);
            SQLExecutor.update(user);
        }
    }

    private String checkStatus(UserAccount user) {
        if (!userAcctService.isActive(user)) {
            user.setStatus(AppConsts.ACCOUNT_STATUS_EXPIRED);
        }
        if (userAcctService.isActive(user) && AppConsts.ACCOUNT_STATUS_INACTIVE.equals(user.getStatus())) {
            user.setStatus(AppConsts.ACCOUNT_STATUS_ACTIVE);
        }
        if (AppConsts.ACCOUNT_STATUS_LOCKED.equals(user.getStatus()) &&
                DateUtils.getHoursBetween(user.getLastLoginAttemptDt(), DateUtils.now()) >= 24) {
            user.setStatus(userAcctService.isActive(user) ?
                    AppConsts.ACCOUNT_STATUS_ACTIVE : AppConsts.ACCOUNT_STATUS_EXPIRED);
        }
        return user.getStatus();
    }
}
