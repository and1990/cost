package org.fire.cost.vo;

import java.math.BigDecimal;

/**
 * 注释：结算明细VO
 * 时间：2014年05月20日 下午6:02
 * 作者：刘腾飞[liutengfei]
 */
public class ClearAccountDetailVO {
    private Long clearAccountDetailId;
    private BigDecimal payMoney;
    private BigDecimal accountMoney;
    private BigDecimal clearMoney;
    private Integer clearType;
    private String clearTypeName;
    private Integer overStatus;
    private String overStatusName;
    private String detailRemark;
    private Long clearAccountId;
    private Long userId;
    private String userName;

    public Long getClearAccountDetailId() {
        return clearAccountDetailId;
    }

    public void setClearAccountDetailId(Long clearAccountDetailId) {
        this.clearAccountDetailId = clearAccountDetailId;
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

    public Integer getClearType() {
        return clearType;
    }

    public void setClearType(Integer clearType) {
        this.clearType = clearType;
    }

    public String getClearTypeName() {
        return clearTypeName;
    }

    public void setClearTypeName(String clearTypeName) {
        this.clearTypeName = clearTypeName;
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
