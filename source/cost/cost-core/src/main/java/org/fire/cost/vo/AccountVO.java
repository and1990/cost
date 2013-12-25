package org.fire.cost.vo;

import java.io.Serializable;

import javax.ws.rs.FormParam;

/**
 * costvo对象，用于向客户端展示数据
 * 
 * @author liutengfei
 * 
 */
public class AccountVO implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long accountId;

	private Long userId;

	@FormParam(value = "userName")
	private String userName;

	@FormParam(value = "accountMoney")
	private Double accountMoney;

	@FormParam(value = "accountTime")
	private String accountTime;

	@FormParam(value = "accountId")
	private Integer accountType;

	@FormParam(value = "accountTypeName")
	private String accountTypeName;

	@FormParam(value = "approveTime")
	private String approveTime;

	@FormParam(value = "isApprove")
	private Integer isApprove;

	@FormParam(value = "isApproveName")
	private String isApproveName;

	@FormParam(value = "accountPartner")
	private String accountPartner;

	@FormParam(value = "accountAccessory")
	private String accountAccessory;

	@FormParam(value = "createUser")
	private String createUser;

	@FormParam(value = "createTime")
	private String createTime;

	@FormParam(value = "modifyUser")
	private String modifyUser;

	@FormParam(value = "modifyTime")
	private String modifyTime;

	@FormParam(value = "accountRemark")
	private String accountRemark;

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

	public Integer getIsApprove()
	{
		return isApprove;
	}

	public void setIsApprove(Integer isApprove)
	{
		this.isApprove = isApprove;
	}

	public String getIsApproveName()
	{
		return isApproveName;
	}

	public void setIsApproveName(String isApproveName)
	{
		this.isApproveName = isApproveName;
	}

	public String getAccountPartner()
	{
		return accountPartner;
	}

	public void setAccountPartner(String accountPartner)
	{
		this.accountPartner = accountPartner;
	}

	public String getAccountAccessory()
	{
		return accountAccessory;
	}

	public void setAccountAccessory(String accountAccessory)
	{
		this.accountAccessory = accountAccessory;
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

}
