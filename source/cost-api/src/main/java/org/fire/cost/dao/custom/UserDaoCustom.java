package org.fire.cost.dao.custom;

import java.util.List;

import org.fire.cost.entity.User;
import org.fire.cost.vo.UserVO;

/**
 * 用户dao扩展类
 * 
 * @author liutengfei
 * 
 */
public interface UserDaoCustom
{

	/**
	 * 根据过滤条件查询用户
	 * 
	 * @param vo
	 * @return
	 */
	public List<User> getUserByFilter(UserVO vo);

}
