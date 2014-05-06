package org.fire.cost.vo;

/**
 * 注释：收入实体类
 * 时间：2014年05月06日 下午2:16
 * 作者：刘腾飞
 */
public class IncomeVO {
    private Long incomeId;
    private Long userId;
    private String userName;
    private Integer incomeMoney;
    private String incomeTime;
    private String createUser;
    private String createTime;
    private String modifyUser;
    private String modifyTime;
    private String incomeRemark;

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

    public Integer getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(Integer incomeMoney) {
        this.incomeMoney = incomeMoney;
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


}
