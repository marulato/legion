package org.zenith.legion.common.base;

import org.zenith.legion.common.AppContext;

import java.io.Serializable;
import java.util.Date;

public abstract class BasePO implements Serializable, Cloneable {

    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    public final void createAuditValues(AppContext context) {
        if (context != null) {
            Date now = new Date();
            setCreatedBy(context.getLoginId());
            setUpdatedBy(context.getLoginId());
            setCreatedAt(now);
            setUpdatedAt(now);
        }
    }

    public final void updateAuditValues(AppContext context) {
        if (context != null) {
            Date now = new Date();
            setUpdatedBy(context.getLoginId());
            setUpdatedAt(now);
        }
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
