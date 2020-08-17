package org.zenith.legion.hr.dto;

import org.zenith.legion.common.base.BaseDto;
import org.zenith.legion.common.validation.NotNull;
import org.zenith.legion.common.validation.ValidateWithMethod;
import org.zenith.legion.common.validation.ValidateWithRegex;

public class EmployeeRegistrationDto extends BaseDto {

    @ValidateWithRegex(regex = "[\u4E00-\u9FA5]{2,16}(?:·[\u4E00-\u9FA5]{2,16})*", message = "")
    private String employeeName;
    private String idNo;
    private String gender;
    private String dob;
    private String age;
    private String phoneNo;
    private String emailAddress;
    private String province;
    private String prefecture;
    private String county;
    private String town;
    private String address;
    private String department;
    private String position;
    private String level;
    private String baseSalary;
    private String positionSubsidies;
    private String method;
    private String recommended;
    private String writeScore;
    private String interviewedAt;
    private String interviewedBy;
    private String interviewType;
    private String interviewScore;
    private String offerSentAt;
    private String requiredEntryDate;
    private String actualEntryDate;
    private String probationUntilDate;
    private String graduatedFromUni;
    private String graduatedDate;
    private String major;
    private String domicileProvince;
    private String domicilePrefecture;
    private String domicileCounty;
    private String domicileTown;
    private String domicileAddress;
    private String nation;
    private String politicalStatus;

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
