package org.zenith.legion.hr.entity;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;

@Persistant(tableName = "STA_STAFF_BASIC_SALARY")
public class BasicSalary extends BasePO {

    private String staffId;
    private Double basicSalary;
    private Double cateringSubsidies;
    private Double transportationSubsidies;
    private Double devicesSubsidies;
    private Double positionSubsidies;
    private Double otherSubsidies;
    private Double bonus;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Double getCateringSubsidies() {
        return cateringSubsidies;
    }

    public void setCateringSubsidies(Double cateringSubsidies) {
        this.cateringSubsidies = cateringSubsidies;
    }

    public Double getTransportationSubsidies() {
        return transportationSubsidies;
    }

    public void setTransportationSubsidies(Double transportationSubsidies) {
        this.transportationSubsidies = transportationSubsidies;
    }

    public Double getDevicesSubsidies() {
        return devicesSubsidies;
    }

    public void setDevicesSubsidies(Double devicesSubsidies) {
        this.devicesSubsidies = devicesSubsidies;
    }

    public Double getPositionSubsidies() {
        return positionSubsidies;
    }

    public void setPositionSubsidies(Double positionSubsidies) {
        this.positionSubsidies = positionSubsidies;
    }

    public Double getOtherSubsidies() {
        return otherSubsidies;
    }

    public void setOtherSubsidies(Double otherSubsidies) {
        this.otherSubsidies = otherSubsidies;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }
}
