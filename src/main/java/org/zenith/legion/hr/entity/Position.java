package org.zenith.legion.hr.entity;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

@Persistant(tableName = "MC_POSITION")
public class Position extends BasePO {

    @PrimaryKey(autoIncrement = true)
    private Integer id;
    private Integer departmentId;
    private String roleId;
    private String positionName;
    private String description;
    private String appraisePrefix;
    private Integer appraiseLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setDepartmentId(Integer departmentId) {
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
