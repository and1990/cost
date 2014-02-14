package org.fire.cost.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 注释：
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-14 下午2:08
 */
@Entity
@Table(name = "cost_group_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupAccount implements Serializable
{
    @Id
    @Column(name = "group_account_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupAccountId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "account_money")
    private String accountMoney;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "is_over")
    private Integer isOver;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "group_account_remark")
    private String groupAccountRemark;

    public Long getGroupAccountId()
    {
        return groupAccountId;
    }

    public void setGroupAccountId(Long groupAccountId)
    {
        this.groupAccountId = groupAccountId;
    }

    public Long getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    public String getAccountMoney()
    {
        return accountMoney;
    }

    public void setAccountMoney(String accountMoney)
    {
        this.accountMoney = accountMoney;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Integer getIsOver()
    {
        return isOver;
    }

    public void setIsOver(Integer isOver)
    {
        this.isOver = isOver;
    }

    public String getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
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
