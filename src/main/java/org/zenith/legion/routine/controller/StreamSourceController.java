package org.zenith.legion.routine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.zenith.legion.common.AppContext;
import org.zenith.legion.common.aop.permission.RequiresLogin;
import org.zenith.legion.common.consts.ContentConsts;
import org.zenith.legion.common.utils.SpringUtils;
import org.zenith.legion.hr.dao.StaffProfileDAO;
import org.zenith.legion.hr.entity.Staff;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Controller
public class StreamSourceController {

    private static final Logger log = LoggerFactory.getLogger(StreamSourceController.class);
    @GetMapping("/web/data/portrait")
    @RequiresLogin
    public void getUserPortrait(HttpServletRequest request, HttpServletResponse response)  {
        AppContext appContext = AppContext.getAppContext(request);
        if (appContext != null) {

        }
    }
}
