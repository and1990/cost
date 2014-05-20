package org.fire.cost.vo;

import org.fire.cost.domain.ClearAccount;

import javax.persistence.*;
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
    private Integer overType;
    private String detailRemark;

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

    public Integer getOverType() {
        return overType;
    }

    public void setOverType(Integer overType) {
        this.overType = overType;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

}
