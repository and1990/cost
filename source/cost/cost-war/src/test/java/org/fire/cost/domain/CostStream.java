package org.fire.cost.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 注释：
 * 时间：2014年05月07日 上午10:38
 * 作者：刘腾飞[liutengfei]
 */
@Entity
@Table(name = "cost_stream", schema = "", catalog = "cost_shasha")
public class CostStream {
    private Long streamId;
    private Integer year;
    private Integer month;
    private BigDecimal incomeMoney;
    private BigDecimal accountMoney;
    private BigDecimal leftMoney;
    private Timestamp createTime;

    @Id
    @Column(name = "stream_id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streamId) {
        this.streamId = streamId;
    }

    @Basic
    @Column(name = "year", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Basic
    @Column(name = "month", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Basic
    @Column(name = "income_money", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    public BigDecimal getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(BigDecimal incomeMoney) {
        this.incomeMoney = incomeMoney;
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
    @Column(name = "left_money", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    public BigDecimal getLeftMoney() {
        return leftMoney;
    }

    public void setLeftMoney(BigDecimal leftMoney) {
        this.leftMoney = leftMoney;
    }

    @Basic
    @Column(name = "create_time", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CostStream that = (CostStream) o;

        if (accountMoney != null ? !accountMoney.equals(that.accountMoney) : that.accountMoney != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (incomeMoney != null ? !incomeMoney.equals(that.incomeMoney) : that.incomeMoney != null) return false;
        if (leftMoney != null ? !leftMoney.equals(that.leftMoney) : that.leftMoney != null) return false;
        if (month != null ? !month.equals(that.month) : that.month != null) return false;
        if (streamId != null ? !streamId.equals(that.streamId) : that.streamId != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = streamId != null ? streamId.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (incomeMoney != null ? incomeMoney.hashCode() : 0);
        result = 31 * result + (accountMoney != null ? accountMoney.hashCode() : 0);
        result = 31 * result + (leftMoney != null ? leftMoney.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
