package org.fire.cost.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 注释：
 * 时间：2014年05月06日 下午2:16
 * 作者：刘腾飞
 */
@Entity
@Table(name = "cost_account", schema = "", catalog = "cost_shasha")
public class CostAccount {
    private Long accountId;
    private BigDecimal accountMoney;
    private Date accountTime;
    private Integer accountType;
    private Integer accountStatus;
    private String createUser;
    private Timestamp createTime;
    private String modifyUser;
    private Timestamp modifyTime;
    private String accountRemark;
    private CostUser user;

    @Id
    @Column(name = "account_id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "account_money", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    @Basic
    @Column(name = "account_time", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Date getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(Date accountTime) {
        this.accountTime = accountTime;
    }

    @Basic
    @Column(name = "account_type", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    @Basic
    @Column(name = "account_status", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
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
    @Column(name = "account_remark", nullable = true, insertable = true, updatable = true, length = 200, precision = 0)
    public String getAccountRemark() {
        return accountRemark;
    }

    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CostAccount that = (CostAccount) o;

        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (accountMoney != null ? !accountMoney.equals(that.accountMoney) : that.accountMoney != null) return false;
        if (accountRemark != null ? !accountRemark.equals(that.accountRemark) : that.accountRemark != null)
            return false;
        if (accountStatus != null ? !accountStatus.equals(that.accountStatus) : that.accountStatus != null)
            return false;
        if (accountTime != null ? !accountTime.equals(that.accountTime) : that.accountTime != null) return false;
        if (accountType != null ? !accountType.equals(that.accountType) : that.accountType != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (modifyUser != null ? !modifyUser.equals(that.modifyUser) : that.modifyUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountId != null ? accountId.hashCode() : 0;
        result = 31 * result + (accountMoney != null ? accountMoney.hashCode() : 0);
        result = 31 * result + (accountTime != null ? accountTime.hashCode() : 0);
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (accountStatus != null ? accountStatus.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifyUser != null ? modifyUser.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (accountRemark != null ? accountRemark.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public CostUser getUser() {
        return user;
    }

    public void setUser(CostUser user) {
        this.user = user;
    }
}
