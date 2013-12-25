package org.fire.cost.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.RollbackException;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.fire.cost.dao.UserDao;
import org.fire.cost.entity.User;
import org.fire.cost.enums.AddTypeEnum;
import org.fire.cost.enums.UserStatusEnum;
import org.fire.cost.enums.YesOrNoEnum;
import org.fire.cost.service.UserService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService
{
	private static org.apache.log4j.Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Resource
	private UserDao userDao;

	/**
	 * @param name
	 *            用户名
	 * @param password
	 *            密码
	 */
	public boolean userLogin(String name, String password)
	{
		try
		{
			User user = userDao.findByLoginName(name);
			if (user != null && user.getPassword().equals(password))
			{
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RollbackException();
		}
		return false;
	}

	/**
	 * 更新用户登录时间
	 */
	@Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
	public boolean changeUserLoginTime(Long userId)
	{
		try
		{
			userDao.changeUserLoginTime(userId);
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RollbackException();
		}
	}

	/**
	 * 根据过滤条件查询用户记录
	 */
	public List<UserVO> getUserByFilter(UserVO vo)
	{
		List<UserVO> userVOList = new ArrayList<UserVO>();
		try
		{
			List<User> userList = userDao.getUserByFilter(vo);
			for (User user : userList)
			{
				UserVO userVO = makeUser2VO(user);
				userVOList.add(userVO);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return userVOList;
	}

	/**
	 * 增加用户
	 */
	@Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
	public boolean addUser(UserVO vo)
	{
		logger.debug("liutengfei -- add start");
		boolean isTrue = true;
		try
		{
			Integer addType = vo.getAddType();
			if (addType != null && addType == AddTypeEnum.UserAdd.getCode())
			{
				vo.setUserStatus(UserStatusEnum.Enable.getCode());
				vo.setIsAdmin(YesOrNoEnum.No.getCode());
				vo.setCreateUser(vo.getUserName());
				vo.setModifyUser(vo.getUserName());
			} else
			{
				vo.setUserStatus(Integer.valueOf(vo.getUserStatusName()));//修改
				vo.setIsAdmin(Integer.valueOf(vo.getIsAdminName()));
				vo.setCreateUser(getLoginUserName());
				vo.setModifyUser(getLoginUserName());
				vo.setPassword("123");
			}
			User user = makeVO2User(vo, null);
			userDao.save(user);
			logger.debug("liutengfei -- add end");
		} catch (Exception e)
		{
			isTrue = false;
			logger.debug("liutengfei -- error : "+e.getMessage());
			e.printStackTrace();
			throw new RollbackException();
		}
		return isTrue;
	}

	/**
	 * 修改用户信息
	 */
	@Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
	public boolean modifyUser(UserVO vo)
	{
		boolean isTrue = true;
		try
		{
			Long userId = vo.getUserId();
			User user = userDao.findOne(userId);
			makeVO2User(vo, user);
			userDao.save(user);
		} catch (Exception e)
		{
			isTrue = false;
			e.printStackTrace();
			throw new RollbackException();
		}
		return isTrue;
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	@Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
	public boolean deleteUser(Long userId)
	{
		boolean isTrue = true;
		try
		{
			userDao.delete(userId);
		} catch (Exception e)
		{
			isTrue = false;
			e.printStackTrace();
			throw new RollbackException();
		}
		return isTrue;
	}

	/**
	 * 得到当前登录用户名字
	 * 
	 * @return
	 */
	public String getLoginUserName()
	{
		Long userId = AuthenticationUtil.getLoginUserId();
		if (userId != null && userId != 0)
		{
			User user = userDao.findByUserId(userId);
			if (user != null)
			{
				return user.getUserName();
			}
		}
		return null;
	}

	/**
	 * 将UserVO转换成User对象
	 * 
	 * @param vo
	 * @return
	 */
	private User makeVO2User(UserVO vo, User user)
	{
		if (user == null)
		{
			user = new User();
			user.setLoginTime(new Date());
			user.setCreateTime(new Date());
			user.setModifyTime(new Date());
		} else
		{
			user.setUserId(vo.getUserId());
		}
		user.setUserName(vo.getUserName());
		user.setPassword(vo.getPassword());
		user.setLoginName(vo.getLoginName());
		user.setUserAge(vo.getUserAge());
		user.setUserAddress(vo.getUserAddress());
		user.setUserEmail(vo.getUserEmail());
		user.setUserImage(vo.getUserImage());
		user.setUserStatus(vo.getUserStatus());
		user.setIsAdmin(vo.getIsAdmin());
		user.setCreateUser(vo.getCreateUser());
		user.setModifyUser(vo.getModifyUser());
		user.setUserRemark(vo.getUserRemark());
		return user;
	}

	/**
	 * 将实体对象user装换成vo对象
	 * 
	 * @param user
	 * @return
	 */
	private UserVO makeUser2VO(User user)
	{
		UserVO vo = new UserVO();
		vo.setUserId(user.getUserId());
		vo.setUserName(user.getUserName());
		vo.setPassword(user.getPassword());
		vo.setLoginName(user.getLoginName());
		vo.setUserAge(user.getUserAge());
		vo.setUserAddress(user.getUserAddress());
		vo.setUserEmail(user.getUserEmail());
		vo.setUserImage(user.getUserImage());
		vo.setLoginTime(DateUtil.makeDate2Str((user.getLoginTime())));
		vo.setUserStatus(user.getUserStatus());
		vo.setUserStatusName(UserStatusEnum.getName(user.getUserStatus()));
		vo.setIsAdmin(user.getIsAdmin());
		vo.setIsAdminName(YesOrNoEnum.getName(user.getIsAdmin()));
		vo.setCreateUser(user.getCreateUser());
		vo.setCreateTime(DateUtil.makeDate2Str(user.getCreateTime()));
		vo.setModifyUser(user.getModifyUser());
		vo.setModifyTime(DateUtil.makeDate2Str(user.getModifyTime()));
		vo.setUserRemark(user.getUserRemark());
		return vo;
	}
}
