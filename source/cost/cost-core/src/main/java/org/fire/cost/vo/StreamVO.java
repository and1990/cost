package org.fire.cost.vo;

import java.math.BigDecimal;

/**
 * 注释：流水账VO
 * 时间：2014年05月07日 上午10:38
 * 作者：刘腾飞[liutengfei]
 */
public class StreamVO {
    private Long streamId;
    private Integer year;
    private Integer month;
    private String monthName;
    private BigDecimal accountMoney = BigDecimal.ZERO;
    private String createTime;

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

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
