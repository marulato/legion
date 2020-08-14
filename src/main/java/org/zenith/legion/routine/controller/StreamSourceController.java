package org.zenith.legion.routine.controller;

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

    @GetMapping("/web/data/portrait")
    @RequiresLogin
    public void getUserPortrait(HttpServletRequest request, HttpServletResponse response)  {
        AppContext appContext = AppContext.getAppContext(request);
        if (appContext != null) {
            StaffProfileDAO staffProfileDAO = SpringUtils.getBean(StaffProfileDAO.class);
            Staff staff = staffProfileDAO.getStaffById(appContext.getLoginId());
            if (staff != null && staff.getPortrait() != null) {
                if ("jpg".equals(staff.getPortraitExt())) {
                    response.setContentType(ContentConsts.MT_JPG);
                } else if ("jpeg".equals(staff.getPortraitExt())) {
                    response.setContentType(ContentConsts.MT_JPEG);
                } else if ("png".equals(staff.getPortraitExt())) {
                    response.setContentType(ContentConsts.MT_PNG);
                }

            }
        }
    }
}
