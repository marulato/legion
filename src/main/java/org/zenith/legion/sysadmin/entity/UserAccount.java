package org.zenith.legion.sysadmin.entity;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;
import org.zenith.legion.common.validation.NotEmpty;

import java.util.Date;

@Persistant(tableName = "AC_USER_ACCT")
public class UserAccount extends BasePO {

    @PrimaryKey(autoIncrement = true)
    private Long id;
    private Long staffId;
    private String staffNo;
    private String nickname;
    private String domain;
    private String name;
    private String displayName;
    private String type;
    private String password;
    private String email;
    private String phoneNo;
    private String status;
    private String isFirstLogin;
    private String isNeedChangePwd;
    private Date lastLoginSuccessDt;
    private Date lastLoginAttemptDt;
    private String lastLoginIp;
    private Integer loginFailedTimes;
    private Date activatedAt;
    private Date deactivatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(String isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public String getIsNeedChangePwd() {
        return isNeedChangePwd;
    }

    public void setIsNeedChangePwd(String isNeedChangePwd) {
        this.isNeedChangePwd = isNeedChangePwd;
    }

    public Date getLastLoginSuccessDt() {
        return lastLoginSuccessDt;
    }

    public void setLastLoginSuccessDt(Date lastLoginSuccessDt) {
        this.lastLoginSuccessDt = lastLoginSuccessDt;
    }

    public Date getLastLoginAttemptDt() {
        return lastLoginAttemptDt;
    }

    public void setLastLoginAttemptDt(Date lastLoginAttemptDt) {
        this.lastLoginAttemptDt = lastLoginAttemptDt;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getLoginFailedTimes() {
        return loginFailedTimes;
    }

    public void setLoginFailedTimes(Integer loginFailedTimes) {
        this.loginFailedTimes = loginFailedTimes;
    }

    public Date getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(Date activatedAt) {
        this.activatedAt = activatedAt;
    }

    public Date getDeactivatedAt() {
        return deactivatedAt;
    }

    public void setDeactivatedAt(Date deactivatedAt) {
        this.deactivatedAt = deactivatedAt;
    }
}
