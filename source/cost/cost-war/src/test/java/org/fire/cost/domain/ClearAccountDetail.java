package org.fire.cost.domain;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 注释：
 * 时间：2014年05月20日 下午6:02
 * 作者：刘腾飞[liutengfei]
 */
@Entity
@Table(name = "clear_account_detail", schema = "", catalog = "cost")
public class ClearAccountDetail {
    private Long clearAccountDetailId;
    private BigDecimal accountMoney;
    private BigDecimal clearMoney;
    private Integer clearType;
    private Integer overStatus;
    private String detailRemark;
    private ClearAccount clearAccount;

    @Id
    @Column(name = "clear_account_detail_id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Long getClearAccountDetailId() {
        return clearAccountDetailId;
    }

    public void setClearAccountDetailId(Long clearAccountDetailId) {
        this.clearAccountDetailId = clearAccountDetailId;
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
    @Column(name = "clear_money", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    public BigDecimal getClearMoney() {
        return clearMoney;
    }

    public void setClearMoney(BigDecimal clearMoney) {
        this.clearMoney = clearMoney;
    }

    @Basic
    @Column(name = "clear_type", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getClearType() {
        return clearType;
    }

    public void setClearType(Integer clearType) {
        this.clearType = clearType;
    }

    @Basic
    @Column(name = "over_status", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getOverStatus() {
        return overStatus;
    }

    public void setOverStatus(Integer overStatus) {
        this.overStatus = overStatus;
    }

    @Basic
    @Column(name = "detail_remark", nullable = true, insertable = true, updatable = true, length = 200, precision = 0)
    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClearAccountDetail that = (ClearAccountDetail) o;

        if (accountMoney != null ? !accountMoney.equals(that.accountMoney) : that.accountMoney != null) return false;
        if (clearAccountDetailId != null ? !clearAccountDetailId.equals(that.clearAccountDetailId) : that.clearAccountDetailId != null)
            return false;
        if (clearMoney != null ? !clearMoney.equals(that.clearMoney) : that.clearMoney != null) return false;
        if (clearType != null ? !clearType.equals(that.clearType) : that.clearType != null) return false;
        if (detailRemark != null ? !detailRemark.equals(that.detailRemark) : that.detailRemark != null) return false;
        if (overStatus != null ? !overStatus.equals(that.overStatus) : that.overStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clearAccountDetailId != null ? clearAccountDetailId.hashCode() : 0;
        result = 31 * result + (accountMoney != null ? accountMoney.hashCode() : 0);
        result = 31 * result + (clearMoney != null ? clearMoney.hashCode() : 0);
        result = 31 * result + (clearType != null ? clearType.hashCode() : 0);
        result = 31 * result + (overStatus != null ? overStatus.hashCode() : 0);
        result = 31 * result + (detailRemark != null ? detailRemark.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "clear_account_id", referencedColumnName = "clear_account_id", nullable = false)
    public ClearAccount getClearAccount() {
        return clearAccount;
    }

    public void setClearAccount(ClearAccount clearAccount) {
        this.clearAccount = clearAccount;
    }
}
