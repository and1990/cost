package org.fire.cost.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 注释：
 * 时间：2014年04月25日 上午11:37
 * 作者：刘腾飞
 */
@Entity
@Table(name = "cost_group_account", catalog = "cost")
public class GroupAccount implements java.io.Serializable {

    private Long groupAccountId;
    private Group group;
    private Long accountMoney;
    private Date startDate;
    private Date endDate;
    private Integer isOver;
    private String createUser;
    private Timestamp createTime;
    private String groupAccountRemark;

    public GroupAccount() {
    }

    public GroupAccount(Group group, Long accountMoney, Date startDate, Date endDate, Integer isOver) {
        this.group = group;
        this.accountMoney = accountMoney;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOver = isOver;
    }

    public GroupAccount(Group group, Long accountMoney, Date startDate, Date endDate, Integer isOver, String createUser,
                        Timestamp createTime, String groupAccountRemark) {
        this.group = group;
        this.accountMoney = accountMoney;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOver = isOver;
        this.createUser = createUser;
        this.createTime = createTime;
        this.groupAccountRemark = groupAccountRemark;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "group_account_id", unique = true, nullable = false)
    public Long getGroupAccountId() {
        return this.groupAccountId;
    }

    public void setGroupAccountId(Long groupAccountId) {
        this.groupAccountId = groupAccountId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group costGroup) {
        this.group = costGroup;
    }

    @Column(name = "account_money", nullable = false, precision = 10, scale = 0)
    public Long getAccountMoney() {
        return this.accountMoney;
    }

    public void setAccountMoney(Long accountMoney) {
        this.accountMoney = accountMoney;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false, length = 10)
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false, length = 10)
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "is_over", nullable = false)
    public Integer getIsOver() {
        return this.isOver;
    }

    public void setIsOver(Integer isOver) {
        this.isOver = isOver;
    }

    @Column(name = "create_user", length = 20)
    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(name = "create_time", length = 19)
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "group_account_remark", length = 200)
    public String getGroupAccountRemark() {
        return this.groupAccountRemark;
    }

    public void setGroupAccountRemark(String groupAccountRemark) {
        this.groupAccountRemark = groupAccountRemark;
    }

}