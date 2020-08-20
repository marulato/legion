package org.zenith.legion.hr.entity;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;
import java.util.Date;

@Persistant(tableName = "STA_STAFF_REGISTRATION")
public class EmployeeRegistration extends BasePO {

    public static final String TABLE_NAME = "STA_STAFF_REGISTRATION";

    @PrimaryKey(autoIncrement = false)
    private String staffId;
    private Long resumeCmDocumentId;
    private Long merCmDocumentId;
    private Long graduationCmDocumentId;
    private Long diplomaCmDocumentId;
    private Long contractCmDocumentId;
    private String education;
    private String recruitmentMethod;
    private String recommendedBy;
    private Integer writtenExamScore;
    private String interviewType;
    private Integer interviewScore;
    private Date interviewedAt;
    private String interviewedBy;
    private Date offerSentAt;
    private Long offerCmDocumentId;
    private Date requiredEntryDate;
    private Date actualEntryDate;
    private Date probationUntilDate;
    private String graduatedFromUni;
    private String major;
    private Date graduatedDate;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getRecruitmentMethod() {
        return recruitmentMethod;
    }

    public void setRecruitmentMethod(String recruitmentMethod) {
        this.recruitmentMethod = recruitmentMethod;
    }

    public String getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(String recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public Integer getWrittenExamScore() {
        return writtenExamScore;
    }

    public void setWrittenExamScore(Integer writtenExamScore) {
        this.writtenExamScore = writtenExamScore;
    }

    public String getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(String interviewType) {
        this.interviewType = interviewType;
    }

    public Integer getInterviewScore() {
        return interviewScore;
    }

    public void setInterviewScore(Integer interviewScore) {
        this.interviewScore = interviewScore;
    }

    public Date getInterviewedAt() {
        return interviewedAt;
    }

    public void setInterviewedAt(Date interviewedAt) {
        this.interviewedAt = interviewedAt;
    }

    public String getInterviewedBy() {
        return interviewedBy;
    }

    public void setInterviewedBy(String interviewedBy) {
        this.interviewedBy = interviewedBy;
    }

    public Date getOfferSentAt() {
        return offerSentAt;
    }

    public void setOfferSentAt(Date offerSentAt) {
        this.offerSentAt = offerSentAt;
    }

    public Long getOfferCmDocumentId() {
        return offerCmDocumentId;
    }

    public void setOfferCmDocumentId(Long offerCmDocumentId) {
        this.offerCmDocumentId = offerCmDocumentId;
    }

    public Date getRequiredEntryDate() {
        return requiredEntryDate;
    }

    public void setRequiredEntryDate(Date requiredEntryDate) {
        this.requiredEntryDate = requiredEntryDate;
    }

    public Date getActualEntryDate() {
        return actualEntryDate;
    }

    public void setActualEntryDate(Date actualEntryDate) {
        this.actualEntryDate = actualEntryDate;
    }

    public Date getProbationUntilDate() {
        return probationUntilDate;
    }

    public void setProbationUntilDate(Date probationUntilDate) {
        this.probationUntilDate = probationUntilDate;
    }

    public String getGraduatedFromUni() {
        return graduatedFromUni;
    }

    public void setGraduatedFromUni(String graduatedFromUni) {
        this.graduatedFromUni = graduatedFromUni;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Date getGraduatedDate() {
        return graduatedDate;
    }

    public void setGraduatedDate(Date graduatedDate) {
        this.graduatedDate = graduatedDate;
    }
}
