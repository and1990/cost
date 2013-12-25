package org.fire.cost.vo;

import java.io.Serializable;

import javax.ws.rs.FormParam;

/**
 * uservo对象，用于向客户端展示数据
 * 
 * @author liutengfei
 * 
 */
public class UserVO implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@FormParam("userId")
	private Long userId;

	//	@FormParam("userName")
	private String userName;

	@FormParam("password")
	private String password;

	@FormParam("loginName")
	private String loginName;

	@FormParam("userAge")
	private Integer userAge;

	@FormParam("userAddress")
	private String userAddress;

	@FormParam("userEmail")
	private String userEmail;

	@FormParam("userImage")
	private String userImage;

	@FormParam("loginTime")
	private String loginTime;

	@FormParam("userStatus")
	private Integer userStatus;

	@FormParam("userStatusName")
	private String userStatusName;

	@FormParam("isAdmin")
	private Integer isAdmin;

	@FormParam("isAdminName")
	private String isAdminName;

	private Integer addType;

	@FormParam("createUser")
	private String createUser;

	@FormParam("createTime")
	private String createTime;

	@FormParam("modifyUser")
	private String modifyUser;

	@FormParam("modifyTime")
	private String modifyTime;

	@FormParam("userRemark")
	private String userRemark;

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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public Integer getUserAge()
	{
		return userAge;
	}

	public void setUserAge(Integer userAge)
	{
		this.userAge = userAge;
	}

	public String getUserAddress()
	{
		return userAddress;
	}

	public void setUserAddress(String userAddress)
	{
		this.userAddress = userAddress;
	}

	public String getUserEmail()
	{
		return userEmail;
	}

	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}

	public String getUserImage()
	{
		return userImage;
	}

	public void setUserImage(String userImage)
	{
		this.userImage = userImage;
	}

	public String getLoginTime()
	{
		return loginTime;
	}

	public void setLoginTime(String loginTime)
	{
		this.loginTime = loginTime;
	}

	public Integer getUserStatus()
	{
		return userStatus;
	}

	public void setUserStatus(Integer userStatus)
	{
		this.userStatus = userStatus;
	}

	public String getUserStatusName()
	{
		return userStatusName;
	}

	public void setUserStatusName(String userStatusName)
	{
		this.userStatusName = userStatusName;
	}

	public Integer getIsAdmin()
	{
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin)
	{
		this.isAdmin = isAdmin;
	}

	public String getIsAdminName()
	{
		return isAdminName;
	}

	public void setIsAdminName(String isAdminName)
	{
		this.isAdminName = isAdminName;
	}

	public Integer getAddType()
	{
		return addType;
	}

	public void setAddType(Integer addType)
	{
		this.addType = addType;
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

	public String getUserRemark()
	{
		return userRemark;
	}

	public void setUserRemark(String userRemark)
	{
		this.userRemark = userRemark;
	}
}
