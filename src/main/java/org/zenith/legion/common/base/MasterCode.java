package org.zenith.legion.common.base;

import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

@Persistant(tableName = "CM_MASTERCODE")
public class MasterCode {

    @PrimaryKey(autoIncrement = true)
    private Integer codeId;
    private String codeType;
    private String codeValue;
    private String profile;
    private String description;
    private String isCached;
    private String isSystem;
    private Integer displayOrder;
    private String isAdminable;

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsCached() {
        return isCached;
    }

    public void setIsCached(String isCached) {
        this.isCached = isCached;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getIsAdminable() {
        return isAdminable;
    }

    public void setIsAdminable(String isAdminable) {
        this.isAdminable = isAdminable;
    }
}
