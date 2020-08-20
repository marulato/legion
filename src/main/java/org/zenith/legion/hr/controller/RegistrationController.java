package org.zenith.legion.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.zenith.legion.common.aop.permission.RequiresLogin;
import org.zenith.legion.common.base.AjaxResponseBody;
import org.zenith.legion.common.base.AjaxResponseManager;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.utils.*;
import org.zenith.legion.common.validation.CommonValidator;
import org.zenith.legion.hr.dto.EmployeeRegistrationDto;
import org.zenith.legion.hr.entity.Position;
import org.zenith.legion.hr.service.RegistrationService;
import org.zenith.legion.sysadmin.entity.District;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/web/hr/registration")
    public String getRegistrationPage() {
        return "hr/registerEmployee";
    }

    @GetMapping("/web/hr/registration/district/{level}/{id}")
    @RequiresLogin
    @ResponseBody
    public AjaxResponseBody prepareDistrict(@PathVariable("level") String level, @PathVariable("id") String id) {
        AjaxResponseManager responseMgr = AjaxResponseManager.create(AppConsts.RESPONSE_SUCCESS);
        District district = new District();
        district.setName("请选择");
        district.setId(-1);
        if ("1".equals(level)) {
            List<District> provinces = MasterCodeUtils.getProvinces();
            if (!provinces.isEmpty()) {
                provinces.add(0, district);
            }
            responseMgr.addDataObjects(provinces);
        } else if ("2".equals(level)) {
            List<District> prefectures = MasterCodeUtils.getCitiesUnderProvince(Integer.parseInt(id));
            if (!prefectures.isEmpty()) {
                prefectures.add(0, district);
            }
            responseMgr.addDataObjects(prefectures);
        } else if ("3".equals(level) || "4".equals(level)) {
            List<District> cities = MasterCodeUtils.getCitiesByLevelAndParent(Integer.parseInt(level), Integer.parseInt(id));
            if (cities.isEmpty()) {
                district.setName("其他");
                district.setId(0);
            }
            cities.add(0, district);
            responseMgr.addDataObjects(cities);
        }
        return responseMgr.respond();
    }

    @GetMapping("/web/hr/registration/base/{idNo}")
    @RequiresLogin
    @ResponseBody
    public AjaxResponseBody prepareAutoFillByIdNo(@PathVariable("idNo") String idNo) throws Exception {
        AjaxResponseManager responseMgr = AjaxResponseManager.create(AppConsts.RESPONSE_SUCCESS);
        JsonMapper jsonMapper = new JsonMapper();
        if (IDNoUtils.isValidIDNo(idNo)) {
            jsonMapper.addJson("dob", DateUtils.getDateString(IDNoUtils.getBirthday(idNo), "yyyy-MM-dd"));
            jsonMapper.addJson("age", DateUtils.getAge(IDNoUtils.getBirthday(idNo)));
            jsonMapper.addJson("gender", IDNoUtils.getGender(idNo));
            String areaCode = IDNoUtils.getDistrictCode(idNo);
            if (StringUtils.isNotBlank(areaCode)) {
                District district = MasterCodeUtils.getDistrictById(Integer.parseInt(areaCode));
                if (district != null) {
                    jsonMapper.addJson("county", district);
                    if (district.getLevel().equals(3)) {
                        District level2 = MasterCodeUtils.getDistrictById(district.getParentId());
                        jsonMapper.addJson("prefecture", level2);
                        if (level2 != null) {
                            District level1 = MasterCodeUtils.getDistrictById(level2.getParentId());
                            jsonMapper.addJson("province", level1);
                        }
                    }
                }
            }

        } else {
            responseMgr = AjaxResponseManager.create(AppConsts.RESPONSE_VALIDATION_NOT_PASS);
            responseMgr.addError("idNo", "请输入正确的身份证号码");
        }
        responseMgr.addDataObject(jsonMapper.toJson());
        return responseMgr.respond();
    }

    @GetMapping("/web/hr/registration/base/department/{id}")
    @RequiresLogin
    @ResponseBody
    public AjaxResponseBody prepareDepartment(@PathVariable("id") String deptId) {
        AjaxResponseManager responseMgr = AjaxResponseManager.create(AppConsts.RESPONSE_SUCCESS);
        if ("0".equals(deptId)) {
            responseMgr.addDataObjects(MasterCodeUtils.getAllDepartments());
        } else {
            Position position = new Position();
            position.setPositionId("0");
            position.setPositionName("请选择");
            List<Position> positionList = MasterCodeUtils.getPositionsByDepartmentId(deptId);
            positionList.add(0, position);
            responseMgr.addDataObjects(positionList);
        }
        return responseMgr.respond();
    }

    @GetMapping("/web/hr/registration/base/position/{id}")
    @RequiresLogin
    @ResponseBody
    public AjaxResponseBody preparePositionLevel(@PathVariable("id") String positionId) {
        AjaxResponseManager responseMgr = AjaxResponseManager.create(AppConsts.RESPONSE_SUCCESS);
        Position position = MasterCodeUtils.getPosition(positionId);
        if (position != null) {
            String prefix = position.getAppraisePrefix();
            Integer maxLevel = position.getAppraiseLevel();
            responseMgr.addDataObject("请选择");
            for (int i = 1; i <= maxLevel; i++) {
                responseMgr.addDataObject(prefix + i);
            }
        }
        return responseMgr.respond();
    }

    @PostMapping("/web/hr/registration/submit")
    @ResponseBody
    public AjaxResponseBody submit(HttpServletRequest request) throws Exception {
        StandardMultipartHttpServletRequest multipartRequest = (StandardMultipartHttpServletRequest) request;
        AjaxResponseManager responseMgr = AjaxResponseManager.create(AppConsts.RESPONSE_SUCCESS);
        EmployeeRegistrationDto dto = new EmployeeRegistrationDto(request);
        Map<String, List<String>> errorMap = CommonValidator.doValidation(dto,null);
        if (!errorMap.isEmpty()) {
            responseMgr = AjaxResponseManager.create(AppConsts.RESPONSE_VALIDATION_NOT_PASS);
            responseMgr.addValidations(errorMap);
        } else {
            registrationService.registerEmployee(dto, request);
        }


        return responseMgr.respond();
    }
}
