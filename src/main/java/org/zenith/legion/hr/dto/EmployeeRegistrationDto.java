package org.zenith.legion.hr.dto;

import org.apache.commons.lang3.math.NumberUtils;
import org.zenith.legion.common.base.BaseDto;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.utils.*;
import org.zenith.legion.common.validation.*;
import org.zenith.legion.hr.entity.Department;
import org.zenith.legion.hr.entity.Position;
import org.zenith.legion.sysadmin.entity.District;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public class EmployeeRegistrationDto extends BaseDto {

    @ValidateWithRegex(regex = "[\u4E00-\u9FA5]{2,16}(?:·[\u4E00-\u9FA5]{2,16})*", message = "姓名为2-16个汉字")
    private String employeeName;
    private String idNo;
    @ValidateWithMethod(method = "validateGender", message = "请选择性别")
    private String gender;
    @ValidateWithMethod(method = "validateDate", message = "请输入正确的日期，格式为yyyy-mm-dd")
    private String dob;
    @ValidateWithMethod(method = "validateAge", message = "请输入正确年龄，其应该与生日相符")
    private String age;
    @ValidateWithRegex(regex = "^1[0-9]{10}", message = "请输入11位的手机号码")
    private String phoneNo;
    @ValidateWithMethod(method = "validateEmailAddress", message = "请输入正确的电子邮件地址")
    private String emailAddress;
    @ValidateWithMethodList(methodList = {
            @ValidateWithMethod(method = "validateDistrict",parameters = {"1"}, message = "请选择正确的地区"),
            @ValidateWithMethod(method = "validateAreaMatches", message = "选择的地址不在同一行政区域内")
    })
    private String province;
    @ValidateWithMethod(method = "validateDistrict", parameters = {"2"}, message = "请选择正确的地区")
    private String prefecture;
    @ValidateWithMethod(method = "validateDistrict", parameters = {"3"}, message = "请选择正确的地区")
    private String county;
    @ValidateWithMethod(method = "validateDistrict", parameters = {"4"}, message = "请选择正确的地区")
    private String town;
    private String address;
    @ValidateWithMethod(method = "validateDept", message = "请选择正确的部门，职位和级别")
    private String department;
    private String position;
    private String level;
    private String baseSalary;
    private String positionSubsidies;
    private String method;
    private String recommended;
    private String writeScore;
    @ValidateWithMethod(method = "validateDate", message = "请输入正确的日期，格式为yyyy-mm-dd")
    private String interviewedAt;
    private String interviewedBy;
    private String interviewType;
    private String interviewScore;
    @ValidateWithMethod(method = "validateDate", message = "请输入正确的日期，格式为yyyy-mm-dd")
    private String offerSentAt;
    @ValidateWithMethod(method = "validateDate", message = "请输入正确的日期，格式为yyyy-mm-dd")
    private String requiredEntryDate;
    @ValidateWithMethod(method = "validateDate", message = "请输入正确的日期，格式为yyyy-mm-dd")
    private String actualEntryDate;
    @ValidateWithMethod(method = "validateDate", message = "请输入正确的日期，格式为yyyy-mm-dd")
    private String probationUntilDate;
    private String graduatedFromUni;
    @ValidateWithMethod(method = "validateDate", message = "请输入正确的日期，格式为yyyy-mm-dd")
    private String graduatedDate;
    private String major;
    @ValidateWithMethodList(methodList = {
            @ValidateWithMethod(method = "validateDistrict", parameters = {"1"}, message = "请选择正确的地区"),
            @ValidateWithMethod(method = "validateMatchIdNo", message = "选择的地址与身份证不匹配")
    })
    private String domicileProvince;
    @ValidateWithMethod(method = "validateDistrict", parameters = {"2"}, message = "请选择正确的地区")
    private String domicilePrefecture;
    @ValidateWithMethod(method = "validateDistrict", parameters = {"3"}, message = "请选择正确的地区")
    private String domicileCounty;
    @ValidateWithMethod(method = "validateDistrict", parameters = {"4"}, message = "请选择正确的地区")
    private String domicileTown;
    private String domicileAddress;
    private String nation;
    private String politicalStatus;

    public EmployeeRegistrationDto(HttpServletRequest request) throws Exception{
        mapParameters(request, this);
    }

    public EmployeeRegistrationDto() {}

    private boolean validateGender(String gender) {
        return AppConsts.GENDER_MALE.equals(gender) || AppConsts.GENDER_FEMALE.equals(gender);
    }

    private boolean validateDate(String date) {
        return DateUtils.parseDate(date) != null;

    }

    private boolean validateAge(String age) {
        return StringUtils.isInteger(age) && Integer.parseInt(age) == DateUtils.getAge(DateUtils.parseDate(getDob()));
    }

    private boolean validateEmailAddress(String emailAddress) {
        return ValidationUtils.isValidEmail(emailAddress);
    }

    private boolean validateDistrict(String district, String level) {
        if (StringUtils.isInteger(district)) {
            District area = MasterCodeUtils.getDistrictById(Integer.parseInt(district));
            return area != null && area.getLevel().equals(Integer.parseInt(level));
        }
        return false;
    }

    private boolean validateMatchIdNo(String province) {
        String areaCode = IDNoUtils.getDistrictCode(idNo);
        if (StringUtils.isNotBlank(areaCode)) {
            District district = MasterCodeUtils.getDistrictById(Integer.parseInt(areaCode));
            if (district != null && domicileCounty.equals(String.valueOf(district.getId()))) {
                District level2 = MasterCodeUtils.getDistrictById(district.getParentId());
                if (level2 != null && domicilePrefecture.equals(String.valueOf(level2.getId()))) {
                    District level1 = MasterCodeUtils.getDistrictById(level2.getParentId());
                    return level1 != null && domicileProvince.equals(String.valueOf(level1.getId()));
                }
            }
        }
        return false;
    }

    private boolean validateAreaMatches(String province) {
        if (StringUtils.isInteger(province) && StringUtils.isInteger(prefecture)
                && StringUtils.isInteger(county) && StringUtils.isInteger(town)) {
            District prov = MasterCodeUtils.getDistrictById(Integer.parseInt(province));
            if (prov != null) {
                District level2 = MasterCodeUtils.getDistrictById(Integer.parseInt(prefecture));
                if (level2 != null && level2.getParentId().equals(prov.getId())) {
                    District level3 = MasterCodeUtils.getDistrictById(Integer.parseInt(county));
                    if (level3 != null && level3.getParentId().equals(level2.getId())) {
                        District level4 = MasterCodeUtils.getDistrictById(Integer.parseInt(town));
                        return level4 != null && level4.getParentId().equals(level3.getId());
                    }
                }
            }
        }
        return false;
    }

    private boolean validateDept(String dept) {
        Department department = MasterCodeUtils.getDepartment(dept);
        if (department != null) {
            Position position = MasterCodeUtils.getPosition(getPosition());
            if (position != null && position.getDepartmentId().equals(dept) && StringUtils.isNotBlank(level)) {
                return position.getAppraisePrefix().equals(level.substring(0, 1))
                        && Integer.parseInt(level.substring(1)) <= position.getAppraiseLevel()
;            }
        }
        return false;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(String baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getPositionSubsidies() {
        return positionSubsidies;
    }

    public void setPositionSubsidies(String positionSubsidies) {
        this.positionSubsidies = positionSubsidies;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getWriteScore() {
        return writeScore;
    }

    public void setWriteScore(String writeScore) {
        this.writeScore = writeScore;
    }

    public String getInterviewedAt() {
        return interviewedAt;
    }

    public void setInterviewedAt(String interviewedAt) {
        this.interviewedAt = interviewedAt;
    }

    public String getInterviewedBy() {
        return interviewedBy;
    }

    public void setInterviewedBy(String interviewedBy) {
        this.interviewedBy = interviewedBy;
    }

    public String getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(String interviewType) {
        this.interviewType = interviewType;
    }

    public String getInterviewScore() {
        return interviewScore;
    }

    public void setInterviewScore(String interviewScore) {
        this.interviewScore = interviewScore;
    }

    public String getOfferSentAt() {
        return offerSentAt;
    }

    public void setOfferSentAt(String offerSentAt) {
        this.offerSentAt = offerSentAt;
    }

    public String getRequiredEntryDate() {
        return requiredEntryDate;
    }

    public void setRequiredEntryDate(String requiredEntryDate) {
        this.requiredEntryDate = requiredEntryDate;
    }

    public String getActualEntryDate() {
        return actualEntryDate;
    }

    public void setActualEntryDate(String actualEntryDate) {
        this.actualEntryDate = actualEntryDate;
    }

    public String getProbationUntilDate() {
        return probationUntilDate;
    }

    public void setProbationUntilDate(String probationUntilDate) {
        this.probationUntilDate = probationUntilDate;
    }

    public String getGraduatedFromUni() {
        return graduatedFromUni;
    }

    public void setGraduatedFromUni(String graduatedFromUni) {
        this.graduatedFromUni = graduatedFromUni;
    }

    public String getGraduatedDate() {
        return graduatedDate;
    }

    public void setGraduatedDate(String graduatedDate) {
        this.graduatedDate = graduatedDate;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDomicileProvince() {
        return domicileProvince;
    }

    public void setDomicileProvince(String domicileProvince) {
        this.domicileProvince = domicileProvince;
    }

    public String getDomicilePrefecture() {
        return domicilePrefecture;
    }

    public void setDomicilePrefecture(String domicilePrefecture) {
        this.domicilePrefecture = domicilePrefecture;
    }

    public String getDomicileCounty() {
        return domicileCounty;
    }

    public void setDomicileCounty(String domicileCounty) {
        this.domicileCounty = domicileCounty;
    }

    public String getDomicileTown() {
        return domicileTown;
    }

    public void setDomicileTown(String domicileTown) {
        this.domicileTown = domicileTown;
    }

    public String getDomicileAddress() {
        return domicileAddress;
    }

    public void setDomicileAddress(String domicileAddress) {
        this.domicileAddress = domicileAddress;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }
}
