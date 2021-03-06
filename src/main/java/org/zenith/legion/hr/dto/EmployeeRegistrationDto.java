package org.zenith.legion.hr.dto;

import org.apache.commons.lang3.math.NumberUtils;
import org.zenith.legion.common.base.BaseDto;
import org.zenith.legion.common.base.PropertyMapping;
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
    @PropertyMapping("name")
    private String employeeName;

    @PropertyMapping("idCardNo")
    @ValidateWithMethod(method = "validateIdNo", message = "请输入正确的身份证号码")
    private String idNo;

    @ValidateWithMethod(method = "validateGender", message = "请选择与身份证标识相符的性别")
    private String gender;

    @ValidateWithMethod(method = "validateDob", message = "请输入与身份证标识相符的日期，格式为yyyy-mm-dd")
    private String dob;

    @ValidateWithMethod(method = "validateAge", message = "请输入正确年龄，其应该与生日相符")
    private String age;

    @ValidateWithRegex(regex = "^1[0-9]{10}", message = "请输入11位的手机号码")
    private String phoneNo;

    @ValidateWithMethod(method = "validateEmailAddress", message = "请输入正确的电子邮件地址")
    private String emailAddress;

    @ValidateWithMethodList(methodList = {
            @ValidateWithMethod(method = "validateDistrict", parameters = {"1"}, message = "请选择正确的地区"),
            @ValidateWithMethod(method = "validateAreaMatches", message = "选择的地址不在同一行政区域内")
    })
    @PropertyMapping("currentProvince")
    private String province;

    @ValidateWithMethod(method = "validateDistrict", parameters = {"2"}, message = "请选择正确的地区")
    @PropertyMapping("currentPrefecture")
    private String prefecture;

    @ValidateWithMethod(method = "validateDistrict", parameters = {"3"}, message = "请选择正确的地区")
    @PropertyMapping("currentCounty")
    private String county;

    @ValidateWithMethod(method = "validateDistrict", parameters = {"4"}, message = "请选择正确的地区")
    @PropertyMapping("currentTown")
    private String town;

    @PropertyMapping("currentDetail")
    private String address;

    @ValidateWithMethod(method = "validateDept", message = "请选择正确的部门，职位和级别")
    @PropertyMapping("departmentId")
    private String department;

    @PropertyMapping("positionId")
    private String position;

    @PropertyMapping("positionLevel")
    private String level;

    private String project;

    @ValidateWithRegex(regex = "^[1-9][0-9]{2,5}", message = "请输入3-6位数的金额")
    private String baseSalary;
    @ValidateWithRegex(regex = "^[1-9][0-9]{2,5}", message = "请输入3-6位数的金额")
    private String positionSubsidies;

    @PropertyMapping("recruitmentMethod")
    private String method;

    @PropertyMapping("recommendedBy")
    private String recommended;

    @PropertyMapping("writtenExamScore")
    private String writeScore;

    @ValidateWithMethod(method = "validateDate", message = "请输入正确的日期，格式为yyyy-mm-dd")
    private String interviewedAt;
    @NotEmpty(message = "请输入面试人")
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

    private String domicileDetail;
    private String nation;
    private String politicalStatus;
    private String education;

    //
    private Long resumeCmDocumentId;
    private Long merCmDocumentId;
    private Long graduationCmDocumentId;
    private Long diplomaCmDocumentId;
    private Long contractCmDocumentId;
    private Long offerCmDocumentId;

    public EmployeeRegistrationDto(HttpServletRequest request) throws Exception{
        mapParameters(request, this);
    }

    public EmployeeRegistrationDto() {}

    private boolean validateGender(String gender) {
        return StringUtils.isNotBlank(gender) && gender.equals(IDNoUtils.getGender(idNo));
    }

    private boolean validateIdNo(String idNo) {
        return IDNoUtils.isValidIDNo(idNo);
    }

    private boolean validateDate(String date) {
        return DateUtils.parseDate(date) != null;

    }

    private boolean validateDob(String dob) {
        if (StringUtils.isNotBlank(dob)) {
            return DateUtils.isSame(DateUtils.parseDate(dob), IDNoUtils.getBirthday(idNo));
        }
        return false;
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
        Department department = MasterCodeUtils.getDepartment(Integer.parseInt(dept));
        if (department != null) {
            Position position = MasterCodeUtils.getPosition(Integer.parseInt(getPosition()));
            if (position != null && position.getDepartmentId().equals(Integer.parseInt(dept)) && StringUtils.isNotBlank(level)) {
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
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

    public String getDomicileDetail() {
        return domicileDetail;
    }

    public void setDomicileDetail(String domicileDetail) {
        this.domicileDetail = domicileDetail;
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

    public Long getResumeCmDocumentId() {
        return resumeCmDocumentId;
    }

    public void setResumeCmDocumentId(Long resumeCmDocumentId) {
        this.resumeCmDocumentId = resumeCmDocumentId;
    }

    public Long getMerCmDocumentId() {
        return merCmDocumentId;
    }

    public void setMerCmDocumentId(Long merCmDocumentId) {
        this.merCmDocumentId = merCmDocumentId;
    }

    public Long getGraduationCmDocumentId() {
        return graduationCmDocumentId;
    }

    public void setGraduationCmDocumentId(Long graduationCmDocumentId) {
        this.graduationCmDocumentId = graduationCmDocumentId;
    }

    public Long getDiplomaCmDocumentId() {
        return diplomaCmDocumentId;
    }

    public void setDiplomaCmDocumentId(Long diplomaCmDocumentId) {
        this.diplomaCmDocumentId = diplomaCmDocumentId;
    }

    public Long getContractCmDocumentId() {
        return contractCmDocumentId;
    }

    public void setContractCmDocumentId(Long contractCmDocumentId) {
        this.contractCmDocumentId = contractCmDocumentId;
    }

    public Long getOfferCmDocumentId() {
        return offerCmDocumentId;
    }

    public void setOfferCmDocumentId(Long offerCmDocumentId) {
        this.offerCmDocumentId = offerCmDocumentId;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
