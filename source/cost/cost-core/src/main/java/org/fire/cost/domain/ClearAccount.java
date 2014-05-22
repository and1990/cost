package org.fire.cost.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 注释：结算实体
 * 时间：2014年05月20日 下午6:02
 * 作者：刘腾飞[liutengfei]
 */
@Entity
@Table(name = "clear_account")
public class ClearAccount implements Serializable {
    private Long clearAccountId;
    private BigDecimal accountMoney;
    private Date startDate;
    private Date endDate;
    private String createUser;
    private Date createTime;
    private String clearAccountRemark;
    private List<ClearAccountDetail> accountDetailList;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "clear_account_id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Long getClearAccountId() {
        return clearAccountId;
    }

    public void setClearAccountId(Long clearAccountId) {
        this.clearAccountId = clearAccountId;
    }

    @Basic
    @Column(name = "account_money", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    @Basic
    @Column(name = "start_date", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "clear_account_remark", nullable = true, insertable = true, updatable = true, length = 200, precision = 0)
    public String getClearAccountRemark() {
        return clearAccountRemark;
    }

    public void setClearAccountRemark(String clearAccountRemark) {
        this.clearAccountRemark = clearAccountRemark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClearAccount that = (ClearAccount) o;

        if (accountMoney != null ? !accountMoney.equals(that.accountMoney) : that.accountMoney != null) return false;
        if (clearAccountId != null ? !clearAccountId.equals(that.clearAccountId) : that.clearAccountId != null)
            return false;
        if (clearAccountRemark != null ? !clearAccountRemark.equals(that.clearAccountRemark) : that.clearAccountRemark != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clearAccountId != null ? clearAccountId.hashCode() : 0;
        result = 31 * result + (accountMoney != null ? accountMoney.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (clearAccountRemark != null ? clearAccountRemark.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "clearAccount")
    public List<ClearAccountDetail> getAccountDetailList() {
        return accountDetailList;
    }

    public void setAccountDetailList(List<ClearAccountDetail> accountDetailList) {
        this.accountDetailList = accountDetailList;
    }
}
