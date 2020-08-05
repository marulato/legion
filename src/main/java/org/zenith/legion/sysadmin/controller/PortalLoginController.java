package org.zenith.legion.sysadmin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zenith.legion.common.AppContext;
import org.zenith.legion.common.SessionManager;
import org.zenith.legion.common.aop.permission.Logical;
import org.zenith.legion.common.aop.permission.RequiresRoles;
import org.zenith.legion.common.base.AjaxResponseBody;
import org.zenith.legion.common.base.AjaxResponseBuilder;
import org.zenith.legion.common.utils.LogUtils;
import org.zenith.legion.sysadmin.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
public class PortalLoginController {

    private static final Logger log = LoggerFactory.getLogger(PortalLoginController.class);

    /**
     * Web View Call
     * @return Login page
     */
    @GetMapping("/web/login")
    public String getLoginPage(HttpServletRequest request) {
        log.info(LogUtils.around("Enter login Landing page"));
        return "sysadmin/login";
    }

    /**
     * Ajax Call
     * @return login validation result
     */
    @PostMapping("/web/login/submit")
    @ResponseBody
    @RequiresRoles(value = "ENEN", logical = Logical.OR)
    public AjaxResponseBody login(String username, String password) {
        AjaxResponseBuilder responseBuilder = AjaxResponseBuilder.build(0);
        return responseBuilder.respond();
    }

    @GetMapping("/web/index")
    public String getLandingPage() {
        return "sysadmin/index";
    }
}
