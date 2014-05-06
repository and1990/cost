package org.fire.cost.vo;

import java.math.BigDecimal;

/**
 * 注释：收入实体类
 * 时间：2014年05月06日 下午2:16
 * 作者：刘腾飞
 */
public class IncomeVO {
    private Long incomeId;
    private Long userId;
    private String userName;
    private BigDecimal incomeMoney;
    private Integer incomeType;
    private String incomeTypeName;
    private String incomeTime;
    private String createUser;
    private String createTime;
    private String modifyUser;
    private String modifyTime;
    private String incomeRemark;
    //自定义
    private String incomeStartTime;
    private String incomeEndTime;
    //是否分页
    private boolean isPage;

    public Long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
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

    public BigDecimal getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(BigDecimal incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public Integer getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(Integer incomeType) {
        this.incomeType = incomeType;
    }

    public String getIncomeTypeName() {
        return incomeTypeName;
    }

    public void setIncomeTypeName(String incomeTypeName) {
        this.incomeTypeName = incomeTypeName;
    }

    public String getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(String incomeTime) {
        this.incomeTime = incomeTime;
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

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getIncomeRemark() {
        return incomeRemark;
    }

    public void setIncomeRemark(String incomeRemark) {
        this.incomeRemark = incomeRemark;
    }

    public String getIncomeStartTime() {
        return incomeStartTime;
    }

    public void setIncomeStartTime(String incomeStartTime) {
        this.incomeStartTime = incomeStartTime;
    }

    public String getIncomeEndTime() {
        return incomeEndTime;
    }

    public void setIncomeEndTime(String incomeEndTime) {
        this.incomeEndTime = incomeEndTime;
    }

    public boolean isPage() {
        return isPage;
    }

    public void setPage(boolean isPage) {
        this.isPage = isPage;
    }
}
