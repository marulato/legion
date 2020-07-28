package org.zenith.legion.sysadmin.entity;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

import java.util.Date;

@Persistant(tableName = "SA_FAILED_EMAIL")
public class FailedEmail extends BasePO {

    @PrimaryKey(autoIncrement = true)
    private String failedEmailId;
    private String sentFrom;
    private String sentTo;
    private String cc;
    private String subject;
    private byte[] content;
    private String attachedFileName;
    private String attachedFileId;
    private String attachedFilePath;
    private Integer failedTimes;
    private Date lastFailedTime;
    private String failedReason;

    public String getFailedEmailId() {
        return failedEmailId;
    }

    public void setFailedEmailId(String failedEmailId) {
        this.failedEmailId = failedEmailId;
    }

    public String getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getAttachedFileName() {
        return attachedFileName;
    }

    public void setAttachedFileName(String attachedFileName) {
        this.attachedFileName = attachedFileName;
    }

    public String getAttachedFileId() {
        return attachedFileId;
    }

    public void setAttachedFileId(String attachedFileId) {
        this.attachedFileId = attachedFileId;
    }

    public String getAttachedFilePath() {
        return attachedFilePath;
    }

    public void setAttachedFilePath(String attachedFilePath) {
        this.attachedFilePath = attachedFilePath;
    }

    public Integer getFailedTimes() {
        return failedTimes;
    }

    public void setFailedTimes(Integer failedTimes) {
        this.failedTimes = failedTimes;
    }

    public Date getLastFailedTime() {
        return lastFailedTime;
    }

    public void setLastFailedTime(Date lastFailedTime) {
        this.lastFailedTime = lastFailedTime;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }
}
