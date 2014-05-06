package org.fire.cost.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * 注释：
 * 时间：2014年05月06日 下午2:16
 * 作者：刘腾飞
 */
@Entity
@Table(name = "cost_user", schema = "", catalog = "cost_shasha")
public class CostUser {
    private Long userId;
    private String userName;
    private String password;
    private String loginName;
    private Integer userAge;
    private String userAddress;
    private String userEmail;
    private Integer userStatus;
    private Integer userType;
    private Timestamp loginTime;
    private String createUser;
    private Timestamp createTime;
    private String modifyUser;
    private Timestamp modifyTime;
    private String userRemark;
    private Collection<CostAccount> accountList;
    private Collection<CostIncome> incomeList;

    @Id
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_name", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password", nullable = false, insertable = true, updatable = true, length = 64, precision = 0)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "login_name", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Basic
    @Column(name = "user_age", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    @Basic
    @Column(name = "user_address", nullable = true, insertable = true, updatable = true, length = 150, precision = 0)
    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Basic
    @Column(name = "user_email", nullable = true, insertable = true, updatable = true, length = 50, precision = 0)
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Basic
    @Column(name = "user_status", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Basic
    @Column(name = "user_type", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "login_time", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    @Basic
    @Column(name = "create_user", nullable = true, insertable = true, updatable = true, length = 20, precision = 0)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "create_time", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modify_user", nullable = true, insertable = true, updatable = true, length = 20, precision = 0)
    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Basic
    @Column(name = "modify_time", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "user_remark", nullable = true, insertable = true, updatable = true, length = 200, precision = 0)
    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CostUser costUser = (CostUser) o;

        if (createTime != null ? !createTime.equals(costUser.createTime) : costUser.createTime != null) return false;
        if (createUser != null ? !createUser.equals(costUser.createUser) : costUser.createUser != null) return false;
        if (loginName != null ? !loginName.equals(costUser.loginName) : costUser.loginName != null) return false;
        if (loginTime != null ? !loginTime.equals(costUser.loginTime) : costUser.loginTime != null) return false;
        if (modifyTime != null ? !modifyTime.equals(costUser.modifyTime) : costUser.modifyTime != null) return false;
        if (modifyUser != null ? !modifyUser.equals(costUser.modifyUser) : costUser.modifyUser != null) return false;
        if (password != null ? !password.equals(costUser.password) : costUser.password != null) return false;
        if (userAddress != null ? !userAddress.equals(costUser.userAddress) : costUser.userAddress != null)
            return false;
        if (userAge != null ? !userAge.equals(costUser.userAge) : costUser.userAge != null) return false;
        if (userEmail != null ? !userEmail.equals(costUser.userEmail) : costUser.userEmail != null) return false;
        if (userId != null ? !userId.equals(costUser.userId) : costUser.userId != null) return false;
        if (userName != null ? !userName.equals(costUser.userName) : costUser.userName != null) return false;
        if (userRemark != null ? !userRemark.equals(costUser.userRemark) : costUser.userRemark != null) return false;
        if (userStatus != null ? !userStatus.equals(costUser.userStatus) : costUser.userStatus != null) return false;
        if (userType != null ? !userType.equals(costUser.userType) : costUser.userType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (userAge != null ? userAge.hashCode() : 0);
        result = 31 * result + (userAddress != null ? userAddress.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (loginTime != null ? loginTime.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifyUser != null ? modifyUser.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (userRemark != null ? userRemark.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "user")
    public Collection<CostAccount> getAccountList() {
        return accountList;
    }

    public void setAccountList(Collection<CostAccount> accountList) {
        this.accountList = accountList;
    }

    @OneToMany(mappedBy = "user")
    public Collection<CostIncome> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(Collection<CostIncome> incomeList) {
        this.incomeList = incomeList;
    }
}
