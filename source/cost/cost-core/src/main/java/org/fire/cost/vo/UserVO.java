package org.fire.cost.vo;

import java.io.Serializable;

/**
 * uservo对象，用于向客户端展示数据
 *
 * @author liutengfei
 */
public class UserVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String userName;

    private String password;

    private String loginName;

    private Integer userAge;

    private String userAddress;

    private String userEmail;

    private String userImage;

    private String loginTime;

    private Integer userStatus;

    private String userStatusName;

    private Integer isAdmin;

    private String isAdminName;

    private Integer addType;

    private String createUser;

    private String createTime;

    private String modifyUser;

    private String modifyTime;

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
