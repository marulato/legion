package org.zenith.legion.general.entity;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

import java.util.Date;

@Persistant(tableName = "CM_DOCUMENT")
public class Document extends BasePO {

    @PrimaryKey(autoIncrement = true)
    private Long id;
    private String fileName;
    private String docType;
    private String docRefNo;
    private Integer version;
    private String status;
    private String category;
    private String description;
    private Long fileNetId;
    private String isUploaded;
    private String uploadedFor;
    private String uploadedBy;
    private Date uploadedAt;
    private String isAutoGenerated;
    private String generatedFor;
    private String generatedBy;
    private Date generatedAt;
    private String isDeleted;
    private String deletedFor;
    private String deletedBy;
    private Date deletedAt;
    private String isVoid;
    private String voidedFor;
    private String voidedBy;
    private Date voidedAt;
    private String referenceTbl;
    private Long referenceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocRefNo() {
        return docRefNo;
    }

    public void setDocRefNo(String docRefNo) {
        this.docRefNo = docRefNo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFileNetId() {
        return fileNetId;
    }

    public void setFileNetId(Long fileNetId) {
        this.fileNetId = fileNetId;
    }

    public String getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(String isUploaded) {
        this.isUploaded = isUploaded;
    }

    public String getUploadedFor() {
        return uploadedFor;
    }

    public void setUploadedFor(String uploadedFor) {
        this.uploadedFor = uploadedFor;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getIsAutoGenerated() {
        return isAutoGenerated;
    }

    public void setIsAutoGenerated(String isAutoGenerated) {
        this.isAutoGenerated = isAutoGenerated;
    }

    public String getGeneratedFor() {
        return generatedFor;
    }

    public void setGeneratedFor(String generatedFor) {
        this.generatedFor = generatedFor;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public Date getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Date generatedAt) {
        this.generatedAt = generatedAt;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDeletedFor() {
        return deletedFor;
    }

    public void setDeletedFor(String deletedFor) {
        this.deletedFor = deletedFor;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getIsVoid() {
        return isVoid;
    }

    public void setIsVoid(String isVoid) {
        this.isVoid = isVoid;
    }

    public String getVoidedFor() {
        return voidedFor;
    }

    public void setVoidedFor(String voidedFor) {
        this.voidedFor = voidedFor;
    }

    public String getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(String voidedBy) {
        this.voidedBy = voidedBy;
    }

    public Date getVoidedAt() {
        return voidedAt;
    }

    public void setVoidedAt(Date voidedAt) {
        this.voidedAt = voidedAt;
    }

    public String getReferenceTbl() {
        return referenceTbl;
    }

    public void setReferenceTbl(String referenceTbl) {
        this.referenceTbl = referenceTbl;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }
}
