package org.zenith.legion.common.base;

import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

@Persistant(tableName = "CM_MASTERCODE")
public class MasterCode extends BasePO {

    @PrimaryKey(autoIncrement = true)
    private Integer mastercodeId;
    private String type;
    private String code;
    private String value;
    private String description;
    private String isCached;
    private String isSystem;
    private Integer displayOrder;
    private String isAdminable;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Integer getMastercodeId() {
        return mastercodeId;
    }

    public void setMastercodeId(Integer mastercodeId) {
        this.mastercodeId = mastercodeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
