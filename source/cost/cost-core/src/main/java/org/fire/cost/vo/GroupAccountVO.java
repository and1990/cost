package org.fire.cost.vo;

import java.io.Serializable;


/**
 * 注释：
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-14 下午2:07
 */
public class GroupAccountVO implements Serializable
{
    private long groupAccountId;

    private long groupId;

    private String groupName;

    private String userIds;

    private String userNames;

    private String accountMoney;

    private String startDate;

    private String endDate;

    private int isOver;

    private String isOverName;

    private String createUser;

    private String createTime;

    private String groupAccountRemark;

    public long getGroupAccountId()
    {
        return groupAccountId;
    }

    public void setGroupAccountId(long groupAccountId)
    {
        this.groupAccountId = groupAccountId;
    }

    public long getGroupId()
    {
        return groupId;
    }

    public void setGroupId(long groupId)
    {
        this.groupId = groupId;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getUserIds()
    {
        return userIds;
    }

    public void setUserIds(String userIds)
    {
        this.userIds = userIds;
    }

    public String getUserNames()
    {
        return userNames;
    }

    public void setUserNames(String userNames)
    {
        this.userNames = userNames;
    }

    public String getAccountMoney()
    {
        return accountMoney;
    }

    public void setAccountMoney(String accountMoney)
    {
        this.accountMoney = accountMoney;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public int getIsOver()
    {
        return isOver;
    }

    public void setIsOver(int isOver)
    {
        this.isOver = isOver;
    }

    public String getIsOverName()
    {
        return isOverName;
    }

    public void setIsOverName(String isOverName)
    {
        this.isOverName = isOverName;
    }

    public String getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getGroupAccountRemark()
    {
        return groupAccountRemark;
    }

    public void setGroupAccountRemark(String groupAccountRemark)
    {
        this.groupAccountRemark = groupAccountRemark;
    }
}
