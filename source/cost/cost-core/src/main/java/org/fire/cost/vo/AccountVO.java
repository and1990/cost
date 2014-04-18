package org.fire.cost.vo;

import java.io.Serializable;

/**
 * costvo对象，用于向客户端展示数据
 *
 * @author liutengfei
 */
public class AccountVO implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long accountId;

    private Long userId;

    private String userName;

    private Double accountMoney;

    private String accountTime;

    private Integer accountType;

    private String accountTypeName;

    private String approveTime;

    private Integer accountStatus;

    private String accountStatusName;

    private String accountPartner;

    private String accountFile;

    private String createUser;

    private String createTime;

    private String modifyUser;

    private String modifyTime;

    private String accountRemark;

    private String startTime;

    private String endTime;


    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public Double getAccountMoney()
    {
        return accountMoney;
    }

    public void setAccountMoney(Double accountMoney)
    {
        this.accountMoney = accountMoney;
    }

    public String getAccountTime()
    {
        return accountTime;
    }

    public void setAccountTime(String accountTime)
    {
        this.accountTime = accountTime;
    }

    public int getAccountType()
    {
        return accountType;
    }

    public void setAccountType(Integer accountType)
    {
        this.accountType = accountType;
    }

    public String getAccountTypeName()
    {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName)
    {
        this.accountTypeName = accountTypeName;
    }

    public String getApproveTime()
    {
        return approveTime;
    }

    public void setApproveTime(String approveTime)
    {
        this.approveTime = approveTime;
    }

    public Integer getAccountStatus()
    {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus)
    {
        this.accountStatus = accountStatus;
    }

    public String getAccountStatusName()
    {
        return accountStatusName;
    }

    public void setAccountStatusName(String accountStatusName)
    {
        this.accountStatusName = accountStatusName;
    }

    public String getAccountPartner()
    {
        return accountPartner;
    }

    public void setAccountPartner(String accountPartner)
    {
        this.accountPartner = accountPartner;
    }

    public String getAccountFile()
    {
        return accountFile;
    }

    public void setAccountFile(String accountFile)
    {
        this.accountFile = accountFile;
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

    public String getModifyUser()
    {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser)
    {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime()
    {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime)
    {
        this.modifyTime = modifyTime;
    }

    public String getAccountRemark()
    {
        return accountRemark;
    }

    public void setAccountRemark(String accountRemark)
    {
        this.accountRemark = accountRemark;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
}
