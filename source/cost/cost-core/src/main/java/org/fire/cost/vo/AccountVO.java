package org.fire.cost.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * costvo对象，用于向客户端展示数据
 *
 * @author liutengfei
 */
public class AccountVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long accountId;

    private Long userId;

    private String userName;

    private Long groupId;

    private String groupName;

    private BigDecimal accountMoney;

    private String accountTime;

    private Integer accountType;

    private String accountTypeName;

    private Integer accountStatus;

    private String accountStatusName;

    private Integer clearType;

    private String clearTypeName;

    private String accountFile;

    private String createUser;

    private String createTime;

    private String modifyUser;

    private String modifyTime;

    private String accountRemark;
    //消费时间【开始时间】
    private String accountStartTime;
    //消费时间【结束时间】
    private String accountEndTime;
    //是否分页
    private boolean isPage = true;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    public String getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(String accountTime) {
        this.accountTime = accountTime;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountStatusName() {
        return accountStatusName;
    }

    public void setAccountStatusName(String accountStatusName) {
        this.accountStatusName = accountStatusName;
    }

    public Integer getClearType() {
        return clearType;
    }

    public void setClearType(Integer clearType) {
        this.clearType = clearType;
    }

    public String getClearTypeName() {
        return clearTypeName;
    }

    public void setClearTypeName(String clearTypeName) {
        this.clearTypeName = clearTypeName;
    }

    public String getAccountFile() {
        return accountFile;
    }

    public void setAccountFile(String accountFile) {
        this.accountFile = accountFile;
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

    public String getAccountRemark() {
        return accountRemark;
    }

    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark;
    }

    public String getAccountStartTime() {
        return accountStartTime;
    }

    public void setAccountStartTime(String accountStartTime) {
        this.accountStartTime = accountStartTime;
    }

    public String getAccountEndTime() {
        return accountEndTime;
    }

    public void setAccountEndTime(String accountEndTime) {
        this.accountEndTime = accountEndTime;
    }

    public boolean isPage() {
        return isPage;
    }

    public void setPage(boolean isPage) {
        this.isPage = isPage;
    }
}
