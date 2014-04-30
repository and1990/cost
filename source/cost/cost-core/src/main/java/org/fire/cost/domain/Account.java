package org.fire.cost.domain;

import javax.persistence.*;
import java.math.BigDecimal;
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
@Table(name = "cost_account", catalog = "cost")
public class Account implements java.io.Serializable {

    private Long accountId;
    private User user;
    private Group group;
    private BigDecimal accountMoney;
    private Date accountTime;
    private Integer accountType;
    private Integer accountStatus;
    private Integer clearType;
    private String accountFile;
    private String createUser;
    private Date createTime;
    private String modifyUser;
    private Date modifyTime;
    private String accountRemark;


    public Account() {
    }


    public Account(User user, Group group, BigDecimal accountMoney, Date accountTime, Integer accountType, Integer accountStatus, Integer clearType) {
        this.user = user;
        this.group = group;
        this.accountMoney = accountMoney;
        this.accountTime = accountTime;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.clearType = clearType;
    }

    public Account(User user, Group group, BigDecimal accountMoney, Date accountTime, Integer accountType, Integer accountStatus, Integer clearType, String accountFile, String createUser, Date createTime, String modifyUser, Date modifyTime, String accountRemark) {
        this.user = user;
        this.group = group;
        this.accountMoney = accountMoney;
        this.accountTime = accountTime;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.clearType = clearType;
        this.accountFile = accountFile;
        this.createUser = createUser;
        this.createTime = createTime;
        this.modifyUser = modifyUser;
        this.modifyTime = modifyTime;
        this.accountRemark = accountRemark;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "account_id", unique = true, nullable = false)
    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User costUser) {
        this.user = costUser;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Column(name = "account_money", nullable = false, precision = 10, scale = 2)
    public BigDecimal getAccountMoney() {
        return this.accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "account_time", nullable = false, length = 10)
    public Date getAccountTime() {
        return this.accountTime;
    }

    public void setAccountTime(Date accountTime) {
        this.accountTime = accountTime;
    }

    @Column(name = "account_type", nullable = false)
    public Integer getAccountType() {
        return this.accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    @Column(name = "account_status", nullable = false)
    public Integer getAccountStatus() {
        return this.accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Column(name = "clear_type", nullable = false)
    public Integer getClearType() {
        return this.clearType;
    }

    public void setClearType(Integer clearType) {
        this.clearType = clearType;
    }

    @Column(name = "account_file", length = 100)
    public String getAccountFile() {
        return this.accountFile;
    }

    public void setAccountFile(String accountFile) {
        this.accountFile = accountFile;
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

    @Column(name = "account_remark", length = 200)
    public String getAccountRemark() {
        return this.accountRemark;
    }

    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark;
    }


}