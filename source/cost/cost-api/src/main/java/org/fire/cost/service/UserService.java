package org.fire.cost.service;

import java.util.List;

import org.fire.cost.vo.UserVO;

public interface UserService
{

	/**
	 * 用户登录
	 * 
	 * @param name
	 *            用户名
	 * @param password
	 *            密码
	 */
	boolean userLogin(String name, String password);

	/**
	 * 更新用户登录时间
	 * 
	 * @param userId
	 * @return
	 */
	boolean changeUserLoginTime(Long userId);

	/**
	 * 得到当前登录用户名称
	 * 
	 * @return
	 */
	public String getLoginUserName();

	/**
	 * 根据过滤条件查询用户
	 * 
	 * @param vo
	 */
	List<UserVO> getUserByFilter(UserVO vo);

	/**
	 * 增加用户
	 * 
	 * @param vo
	 * @return
	 */
	boolean addUser(UserVO vo);

	/**
	 * 修改用户信息
	 * 
	 * @param vo
	 * @return
	 */
	boolean modifyUser(UserVO vo);

	/**
	 * 删除用户
	 * 
	 * @param userId
	 * @return
	 */
	boolean deleteUser(Long userId);

}
