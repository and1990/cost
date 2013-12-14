package org.fire.cost.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 账单实体类
 * 
 * @author liutengfei
 * 
 */
@Entity
@Table(name = "cost_account")
public class Account implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "account_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int accountId;

	@Column(name = "account_money")
	private double accountMoney;

	@Column(name = "account_time")
	private Date accountTime;

	@Column(name = "account_type")
	private int accountType;

	@Column(name = "approve_time")
	private Date approveTime;

	@Column(name = "is_approve")
	private int isApprove;

	@Column(name = "account_partner")
	private String accountPartner;

	@Column(name = "account_accessory")
	private String accountAccessory;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "modify_User")
	private String modifyUser;

	@Column(name = "modify_time")
	private Date modifyTime;

	@Column(name = "account_remark")
	private String accountRemark;

	//与user关联
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public int getAccountId()
	{
		return accountId;
	}

	public void setAccountId(int accountId)
	{
		this.accountId = accountId;
	}

	public double getAccountMoney()
	{
		return accountMoney;
	}

	public void setAccountMoney(double accountMoney)
	{
		this.accountMoney = accountMoney;
	}

	public Date getAccountTime()
	{
		return accountTime;
	}

	public void setAccountTime(Date accountTime)
	{
		this.accountTime = accountTime;
	}

	public int getAccountType()
	{
		return accountType;
	}

	public void setAccountType(int accountType)
	{
		this.accountType = accountType;
	}

	public Date getApproveTime()
	{
		return approveTime;
	}

	public void setApproveTime(Date approveTime)
	{
		this.approveTime = approveTime;
	}

	public int getIsApprove()
	{
		return isApprove;
	}

	public void setIsApprove(int isApprove)
	{
		this.isApprove = isApprove;
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

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
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

	public Date getModifyTime()
	{
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

}
