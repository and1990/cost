package org.fire.cost.service;

import org.fire.cost.context.UserContext;

/**
 * 认证服务接口
 * 
 * @author liutengfei
 * 
 */
public interface AuthenticationService
{

	/**
	 * 构建系统用户上下文
	 * 
	 * @param loginName
	 *            用户登录名称
	 * @return
	 */
	UserContext buildUserContext(String loginName);

}
