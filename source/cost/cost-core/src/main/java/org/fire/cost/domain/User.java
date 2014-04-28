package org.fire.cost.domain;

import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 注释：
 * 时间：2014年04月25日 上午11:37
 * 作者：刘腾飞
 */
@Entity
@Table(name = "cost_user", catalog = "cost")
public class User implements java.io.Serializable {

    private Long userId;
    private String userName;
    private String password;
    private String loginName;
    private Integer userAge;
    private String userAddress;
    private String userEmail;
    private Integer userStatus;
    private Integer userType;
    private Date loginTime;
    private String userImage;
    private String createUser;
    private Date createTime;
    private String modifyUser;
    private Date modifyTime;
    private String userRemark;
    private List<Account> accountList = new ArrayList<Account>();
    private List<GroupUser> groupUserList = new ArrayList<GroupUser>(0);


    public User() {
    }


    public User(String userName, String password, String loginName, Integer userAge, Integer userStatus, Integer userType, Date loginTime) {
        this.userName = userName;
        this.password = password;
        this.loginName = loginName;
        this.userAge = userAge;
        this.userStatus = userStatus;
        this.userType = userType;
        this.loginTime = loginTime;
    }

    public User(String userName, String password, String loginName, Integer userAge, String userAddress, String userEmail, Integer userStatus,
                Integer userType, Date loginTime, String userImage, String createUser, Date createTime, String modifyUser, Date modifyTime,
                String userRemark, List<Account> accountList, List<GroupUser> groupUserList) {
        this.userName = userName;
        this.password = password;
        this.loginName = loginName;
        this.userAge = userAge;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userStatus = userStatus;
        this.userType = userType;
        this.loginTime = loginTime;
        this.userImage = userImage;
        this.createUser = createUser;
        this.createTime = createTime;
        this.modifyUser = modifyUser;
        this.modifyTime = modifyTime;
        this.userRemark = userRemark;
        this.accountList = accountList;
        this.groupUserList = groupUserList;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "user_name", nullable = false, length = 20)
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "password", nullable = false, length = 20)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "login_name", nullable = false, length = 20)
    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name = "user_age", nullable = false)
    public Integer getUserAge() {
        return this.userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    @Column(name = "user_address", length = 150)
    public String getUserAddress() {
        return this.userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Column(name = "user_email", length = 50)
    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Column(name = "user_status", nullable = false)
    public Integer getUserStatus() {
        return this.userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Column(name = "user_type", nullable = false)
    public Integer getUserType() {
        return this.userType;
    }

    public void setUserType(Integer isAdmin) {
        this.userType = isAdmin;
    }

    @Column(name = "login_time", nullable = false, length = 19)
    public Date getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Column(name = "user_image", length = 100)
    public String getUserImage() {
        return this.userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
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

    @Column(name = "user_remark", length = 200)
    public String getUserRemark() {
        return this.userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public List<Account> getAccountList() {
        return this.accountList;
    }

    public void setAccountList(List<Account> Accounts) {
        this.accountList = Accounts;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public List<GroupUser> getGroupUserList() {
        return this.groupUserList;
    }

    public void setGroupUserList(List<GroupUser> groupUsers) {
        this.groupUserList = groupUsers;
    }

}