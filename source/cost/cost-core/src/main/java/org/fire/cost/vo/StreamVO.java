package org.fire.cost.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 注释：流水账VO
 * 时间：2014年05月07日 上午10:38
 * 作者：刘腾飞[liutengfei]
 */
public class StreamVO {
    private Long streamId;
    private Integer year;
    private Integer month;
    private BigDecimal incomeMoney = BigDecimal.ZERO;
    private BigDecimal accountMoney = BigDecimal.ZERO;
    private BigDecimal leftMoney = BigDecimal.ZERO;
    private Date createTime;

    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streamId) {
        this.streamId = streamId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public BigDecimal getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(BigDecimal incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    public BigDecimal getLeftMoney() {
        return leftMoney;
    }

    public void setLeftMoney(BigDecimal leftMoney) {
        this.leftMoney = leftMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
