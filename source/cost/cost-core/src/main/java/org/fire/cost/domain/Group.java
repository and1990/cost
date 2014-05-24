package org.fire.cost.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 注释：
 * 时间：2014年04月25日 上午11:37
 * 作者：刘腾飞
 */
@Entity
@Table(name = "cost_group")
public class Group implements java.io.Serializable {

    private Long groupId;
    private String groupName;
    private Integer groupType;
    private Integer groupStatus;
    private String createUser;
    private Date createTime;
    private String modifyUser;
    private Date modifyTime;
    private String groupRemark;
    private List<Account> accountList = new ArrayList<Account>();
    private List<GroupUser> groupUserList = new ArrayList<GroupUser>();


    public Group() {
    }

    public Group(String groupName, Integer groupStatus) {
        this.groupName = groupName;
        this.groupStatus = groupStatus;
    }

    public Group(String groupName, Integer groupStatus, String createUser, Date createTime, String modifyUser, Date modifyTime, String groupRemark, List<Account> accountList) {
        this.groupName = groupName;
        this.groupStatus = groupStatus;
        this.createUser = createUser;
        this.createTime = createTime;
        this.modifyUser = modifyUser;
        this.modifyTime = modifyTime;
        this.groupRemark = groupRemark;
        this.accountList = accountList;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "group_id", unique = true, nullable = false)
    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Column(name = "group_name", nullable = false, length = 20)
    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Column(name = "group_type", nullable = false)
    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    @Column(name = "group_status", nullable = false)
    public Integer getGroupStatus() {
        return this.groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }

    @Column(name = "create_user", length = 20)
    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(name = "create_time", length = 19)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "modify_user", length = 20)
    public String getModifyUser() {
        return this.modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Column(name = "modify_time", length = 19)
    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Column(name = "group_remark", length = 200)
    public String getGroupRemark() {
        return this.groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
    public List<GroupUser> getGroupUserList() {
        return this.groupUserList;
    }

    public void setGroupUserList(List<GroupUser> costGroupUsers) {
        this.groupUserList = costGroupUsers;
    }
}