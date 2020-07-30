package org.zenith.legion.sysadmin.entity;

import org.checkerframework.checker.units.qual.A;
import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

@Persistant(tableName = "?")
public class EmailEntity extends BasePO {

    @PrimaryKey(autoIncrement = true)
    private Long emailId;
    private String sentFrom;
    private String sentTo;
    private String cc;
    private String subject;
    private byte[] content;
    private String status;
    private String isHasAttachment;

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsHasAttachment() {
        return isHasAttachment;
    }

    public void setIsHasAttachment(String isHasAttachment) {
        this.isHasAttachment = isHasAttachment;
    }
}
