package org.zenith.legion.hr.entity;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

@Persistant(tableName = "STA_STAFF_DEMOGRAPHIC")
public class EmployeeDemographic extends BasePO {

    @PrimaryKey(autoIncrement = false)
    private String staffId;
    private String nationality;
    private String idCardNo;
    private String politicalStatus;
    private String domicileType;
    private Integer domicileProvince;
    private Integer domicilePrefecture;
    private Integer domicileCounty;
    private Integer domicileTown;
    private String domicileDetail;
    private Integer currentProvince;
    private Integer currentPrefecture;
    private Integer currentCounty;
    private Integer currentTown;
    private String currentDetail;
    private Integer nativePlace;
    private String nation;
    private String religion;
    private String isEthnicMinority;
    private String maritalStatus;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getDomicileType() {
        return domicileType;
    }

    public void setDomicileType(String domicileType) {
        this.domicileType = domicileType;
    }

    public Integer getDomicileProvince() {
        return domicileProvince;
    }

    public void setDomicileProvince(Integer domicileProvince) {
        this.domicileProvince = domicileProvince;
    }

    public Integer getDomicilePrefecture() {
        return domicilePrefecture;
    }

    public void setDomicilePrefecture(Integer domicilePrefecture) {
        this.domicilePrefecture = domicilePrefecture;
    }

    public Integer getDomicileCounty() {
        return domicileCounty;
    }

    public void setDomicileCounty(Integer domicileCounty) {
        this.domicileCounty = domicileCounty;
    }

    public Integer getDomicileTown() {
        return domicileTown;
    }

    public void setDomicileTown(Integer domicileTown) {
        this.domicileTown = domicileTown;
    }

    public String getDomicileDetail() {
        return domicileDetail;
    }

    public void setDomicileDetail(String domicileDetail) {
        this.domicileDetail = domicileDetail;
    }

    public Integer getCurrentProvince() {
        return currentProvince;
    }

    public void setCurrentProvince(Integer currentProvince) {
        this.currentProvince = currentProvince;
    }

    public Integer getCurrentPrefecture() {
        return currentPrefecture;
    }

    public void setCurrentPrefecture(Integer currentPrefecture) {
        this.currentPrefecture = currentPrefecture;
    }

    public Integer getCurrentCounty() {
        return currentCounty;
    }

    public void setCurrentCounty(Integer currentCounty) {
        this.currentCounty = currentCounty;
    }

    public Integer getCurrentTown() {
        return currentTown;
    }

    public void setCurrentTown(Integer currentTown) {
        this.currentTown = currentTown;
    }

    public String getCurrentDetail() {
        return currentDetail;
    }

    public void setCurrentDetail(String currentDetail) {
        this.currentDetail = currentDetail;
    }

    public Integer getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(Integer nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getIsEthnicMinority() {
        return isEthnicMinority;
    }

    public void setIsEthnicMinority(String isEthnicMinority) {
        this.isEthnicMinority = isEthnicMinority;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
