package org.fire.cost.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 注释：流水账实体
 * 时间：2014年05月07日 上午10:38
 * 作者：刘腾飞[liutengfei]
 */
@Entity
@Table(name = "cost_stream")
public class Stream implements Serializable {
    private Long streamId;
    private Integer year;
    private Integer month;
    private BigDecimal accountMoney;
    private Date createTime;

    @Id
    @GeneratedValue(strategy = IDENTITY)
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
    @Column(name = "account_money", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    @Basic
    @Column(name = "create_time", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
