package org.zenith.legion.hr.entity;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

@Persistant(tableName = "MC_POSITION")
public class Position extends BasePO {

    @PrimaryKey(autoIncrement = false)
    private String positionId;
    private String departmentId;
    private String positionName;
    private String description;
    private String appraisePrefix;
    private Integer appraiseLevel;

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppraisePrefix() {
        return appraisePrefix;
    }

    public void setAppraisePrefix(String appraisePrefix) {
        this.appraisePrefix = appraisePrefix;
    }

    public Integer getAppraiseLevel() {
        return appraiseLevel;
    }

    public void setAppraiseLevel(Integer appraiseLevel) {
        this.appraiseLevel = appraiseLevel;
    }
}
