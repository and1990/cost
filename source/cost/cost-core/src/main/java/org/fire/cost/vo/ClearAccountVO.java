package org.fire.cost.vo;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 注释：结算VO
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-14 下午2:07
 */
public class ClearAccountVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long clearAccountId;

    private BigDecimal accountMoney;

    private String startDate;

    private String endDate;

    private String createUser;

    private String createTime;

    private String clearAccountRemark;

    //是否全部结算，1：未全部结算；2：全部结算
    private int allClear = 2;
    private String allClearName;

    public Long getClearAccountId() {
        return clearAccountId;
    }

    public void setClearAccountId(Long clearAccountId) {
        this.clearAccountId = clearAccountId;
    }

    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getClearAccountRemark() {
        return clearAccountRemark;
    }

    public void setClearAccountRemark(String clearAccountRemark) {
        this.clearAccountRemark = clearAccountRemark;
    }

    public int getAllClear() {
        return allClear;
    }

    public void setAllClear(int allClear) {
        this.allClear = allClear;
    }

    public String getAllClearName() {
        return allClearName;
    }

    public void setAllClearName(String allClearName) {
        this.allClearName = allClearName;
    }
}
