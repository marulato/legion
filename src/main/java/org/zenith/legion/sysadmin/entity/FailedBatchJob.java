package org.zenith.legion.sysadmin.entity;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

import java.util.Date;

@Persistant(tableName = "SA_FAILED_BATCH_JOB")
public class FailedBatchJob extends BasePO {

    @PrimaryKey(autoIncrement = true)
    private Long failedBatchJobId;
    private String batchJobId;
    private Date startAt;
    private Date endAt;
    private byte[] exception;
    private String isIgnored;

    public Long getFailedBatchJobId() {
        return failedBatchJobId;
    }

    public void setFailedBatchJobId(Long failedBatchJobId) {
        this.failedBatchJobId = failedBatchJobId;
    }

    public String getBatchJobId() {
        return batchJobId;
    }

    public void setBatchJobId(String batchJobId) {
        this.batchJobId = batchJobId;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public byte[] getException() {
        return exception;
    }

    public void setException(byte[] exception) {
        this.exception = exception;
    }

    public String getIsIgnored() {
        return isIgnored;
    }

    public void setIsIgnored(String isIgnored) {
        this.isIgnored = isIgnored;
    }
}
