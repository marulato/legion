package org.zenith.legion.general.entity;

import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

import java.util.Date;

@Persistant(tableName = "SA_FAILED_EMAIL")
public class FailedEmail extends EmailEntity {

    @PrimaryKey(autoIncrement = true)
    private Long failedEmailId;
    private Integer failedTimes;
    private Date lastFailedTime;
    private String failedReason;

    public static final String TABLE_NAME = "SA_FAILED_EMAIL";


    public Long getFailedEmailId() {
        return failedEmailId;
    }

    public void setFailedEmailId(Long failedEmailId) {
        this.failedEmailId = failedEmailId;
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
