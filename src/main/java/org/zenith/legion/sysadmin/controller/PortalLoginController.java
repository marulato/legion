package org.zenith.legion.sysadmin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.zenith.legion.common.AppContext;
import org.zenith.legion.common.SessionManager;
import org.zenith.legion.common.aop.permission.Logical;
import org.zenith.legion.common.aop.permission.RequiresLogin;
import org.zenith.legion.common.aop.permission.RequiresRoles;
import org.zenith.legion.common.base.AjaxResponseBody;
import org.zenith.legion.common.base.AjaxResponseManager;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.utils.LogUtils;
import org.zenith.legion.common.utils.StringUtils;
import org.zenith.legion.common.validation.CommonValidator;
import org.zenith.legion.general.ex.PermissionDeniedException;
import org.zenith.legion.sysadmin.consts.LoginStatus;
import org.zenith.legion.sysadmin.entity.UserAccount;
import org.zenith.legion.sysadmin.entity.UserRole;
import org.zenith.legion.sysadmin.service.PortalLoginService;
import org.zenith.legion.sysadmin.service.UserAccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class PortalLoginController {

    private final PortalLoginService loginService;
    private final UserAccountService accountService;

    private static final Logger log = LoggerFactory.getLogger(PortalLoginController.class);

    @Autowired
    public PortalLoginController(PortalLoginService loginService, UserAccountService accountService) {
        this.loginService = loginService;
        this.accountService = accountService;
    }

    /**
     * Web View Call
     * @return Login page
     */
    @GetMapping("/web/login")
    public String getLoginPage(HttpServletRequest request) {
        log.info(LogUtils.around("Enter login page"));
        return "sysadmin/login";
    }

    @GetMapping("/web/home")
    @RequiresLogin
    public String getDefaultIndexPage(HttpServletRequest request) {
        log.info(LogUtils.around("Enter default Landing page"));
        return "sysadmin/index";
    }

    /**
     * Ajax Call
     * @return login validation result
     */
    @PostMapping("/web/login/landing")
    @ResponseBody
    public AjaxResponseBody login(UserAccount webUser, HttpServletRequest request) throws Exception {
        AjaxResponseManager responseMgr = AjaxResponseManager.create(AppConsts.RESPONSE_ALL_PASSED);
        Map<String, List<String>> errorMap = CommonValidator.doValidation(webUser, null);
        if (errorMap != null && !errorMap.isEmpty()) {
            responseMgr = AjaxResponseManager.create(AppConsts.RESPONSE_VALIDATION_NOT_PASS);
            responseMgr.addValidations(errorMap);
        } else {
            LoginStatus loginStatus = loginService.login(webUser, request);
            if (loginStatus == LoginStatus.SUCCESS) {
                AppContext context = AppContext.getAppContextFromCurrentThread();
                //user has multiple roles, should choose a role manually to proceed
                if (context.getAllRoles().size() > 1) {
                    responseMgr.addDataObjects(context.getAllRoles());
                } else {
                    responseMgr.addDataObject(0);
                }
            } else if (loginStatus == LoginStatus.ACCOUNT_EXPIRED) {
                responseMgr.addError("userId", "账户已过期");
            } else if (loginStatus == LoginStatus.ACCOUNT_LOCKED) {
                responseMgr.addError("userId", "账户已锁定");
            } else if (loginStatus == LoginStatus.ACCOUNT_INACTIVE) {
                responseMgr.addError("userId", "账户尚未启用");
            } else if (loginStatus == LoginStatus.ACCOUNT_FROZEN) {
                responseMgr.addError("userId", "账户已冻结");
            } else {
                responseMgr.addError("userId", "");
                responseMgr.addError("password", "用户名或密码不正确");
            }
        }
        return responseMgr.respond();
    }

    @GetMapping("/web/index/{roleId}")
    public String getLandingPage(@PathVariable String roleId, HttpServletRequest request) {
        AppContext context = AppContext.getAppContextFromCurrentThread();
        if (context == null) {
            return "redirect:/web/login";
        }
        context.setCurrentRole(null);
        if (StringUtils.isNotBlank(roleId) && context.getAllRoles() != null && context.getAllRoles().size() > 1) {
            context.setLoggedIn(true);
            for (UserRole role : context.getAllRoles()) {
                if (roleId.equals(role.getRoleId())) {
                    context.setCurrentRole(role);
                    break;
                }
            }
        } else if (context.getAllRoles() != null && context.getAllRoles().size() == 1) {
            context.setLoggedIn(true);
            context.setCurrentRole(context.getAllRoles().get(0));
        }
        if (context.getCurrentRole() == null) {
            throw new PermissionDeniedException();
        } else {
            UserAccount userAccount = accountService.getUserAccountByIdNo(context.getLoginId());
            request.getSession().setAttribute("profileName", userAccount.getDisplayName());
            request.getSession().setAttribute("roleId", context.getCurrentRole().getRoleId());
            return "redirect:" + context.getCurrentRole().getLandingPage();
        }
    }

    @GetMapping("/web/logout")
    @RequiresLogin
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/web/login";
    }
}
