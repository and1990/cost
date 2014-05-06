package org.fire.cost.vo;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 注释：组消费VO
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-14 下午2:07
 */
public class GroupAccountVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long groupAccountId;

    private Long groupId;

    private BigDecimal accountMoney;

    private String startDate;

    private String endDate;

    private Integer isOver;

    private String isOverName;

    private String createUser;

    private String createTime;

    private String groupAccountRemark;

    public Long getGroupAccountId() {
        return groupAccountId;
    }

    public void setGroupAccountId(Long groupAccountId) {
        this.groupAccountId = groupAccountId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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

    public int getIsOver() {
        return isOver;
    }

    public void setIsOver(Integer isOver) {
        this.isOver = isOver;
    }

    public String getIsOverName() {
        return isOverName;
    }

    public void setIsOverName(String isOverName) {
        this.isOverName = isOverName;
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

    public String getGroupAccountRemark() {
        return groupAccountRemark;
    }

    public void setGroupAccountRemark(String groupAccountRemark) {
        this.groupAccountRemark = groupAccountRemark;
    }
}
