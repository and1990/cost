package org.fire.cost.vo;

import java.math.BigDecimal;

/**
 * 注释：结算明细VO
 * 时间：2014年05月20日 下午6:02
 * 作者：刘腾飞[liutengfei]
 */
public class ClearAccountDetailVO {
    private Long clearDetailId;
    private BigDecimal payMoney;
    private BigDecimal accountMoney;
    private BigDecimal clearMoney;
    private Integer clearResult;
    private String clearResultName;
    private Integer overStatus;
    private String overStatusName;
    private String detailRemark;
    private Long clearAccountId;
    private Long userId;
    private String userName;

    public Long getClearDetailId() {
        return clearDetailId;
    }

    public void setClearDetailId(Long clearDetailId) {
        this.clearDetailId = clearDetailId;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    public BigDecimal getClearMoney() {
        return clearMoney;
    }

    public void setClearMoney(BigDecimal clearMoney) {
        this.clearMoney = clearMoney;
    }

    public Integer getClearResult() {
        return clearResult;
    }

    public void setClearResult(Integer clearResult) {
        this.clearResult = clearResult;
    }

    public String getClearResultName() {
        return clearResultName;
    }

    public void setClearResultName(String clearResultName) {
        this.clearResultName = clearResultName;
    }

    public Integer getOverStatus() {
        return overStatus;
    }

    public void setOverStatus(Integer overStatus) {
        this.overStatus = overStatus;
    }

    public String getOverStatusName() {
        return overStatusName;
    }

    public void setOverStatusName(String overStatusName) {
        this.overStatusName = overStatusName;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    public Long getClearAccountId() {
        return clearAccountId;
    }

    public void setClearAccountId(Long clearAccountId) {
        this.clearAccountId = clearAccountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
