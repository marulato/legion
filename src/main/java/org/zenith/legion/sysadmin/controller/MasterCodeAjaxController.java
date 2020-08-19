package org.zenith.legion.sysadmin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.zenith.legion.common.base.AjaxResponseBody;
import org.zenith.legion.common.base.AjaxResponseManager;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.utils.JsonMapper;
import org.zenith.legion.common.utils.MasterCodeUtils;
import org.zenith.legion.sysadmin.entity.MasterCode;

import java.util.List;

@RestController
public class MasterCodeAjaxController {

    @GetMapping("/web/mastercode/{type}")
    public AjaxResponseBody getMasterCode(@PathVariable("type") String type) {
        AjaxResponseManager mgr = AjaxResponseManager.create(AppConsts.RESPONSE_SUCCESS);
        List<MasterCode> masterCodes = MasterCodeUtils.getMasterCodeByType(type);
        JsonMapper jsonMapper = new JsonMapper();
        for (MasterCode masterCode : masterCodes) {
            jsonMapper.addJson(masterCode.getCode(), masterCode.getValue());
        }
        mgr.addDataObject(jsonMapper.toJson());
        return mgr.respond();
    }
}
