package org.zenith.legion.sysadmin.entity;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

@Persistant(tableName = "CM_SEQUENCE", auditColumns = false)
public class Sequence extends BasePO {

    @PrimaryKey(autoIncrement = false)
    private String name;
    private Long value;
    private Long minValue;
    private Long maxValue;
    private Integer step;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
