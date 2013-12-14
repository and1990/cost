package org.fire.cost.util;

import org.fire.cost.context.ThreadMessageContext;
import org.fire.cost.context.UserContext;

/**
 * 用户验证工具类
 * 
 * @author liutengfei
 * 
 */
public class AuthenticationUtil
{

	/**
	 * 得到登录用户id
	 * 
	 * @return
	 */
	public static Long getLoginUserId()
	{
		Long userid = null;
		UserContext userContext = ThreadMessageContext.getUserContext();
		if (userContext != null)
			userid = userContext.getUserId();
		return userid;
	}

	/**
	 * 得到uuid
	 * 
	 * @return
	 */
	public static String getUUId()
	{
		String uuid = null;
		UserContext userContext = ThreadMessageContext.getUserContext();
		if (userContext != null)
			uuid = userContext.getUuid();
		return uuid;
	}

	/**
	 * 得到sessionId
	 * 
	 * @return
	 */
	public static String getSessionId()
	{
		String sessionID = null;
		UserContext userContext = ThreadMessageContext.getUserContext();
		if (userContext != null)
			sessionID = userContext.getSessionId();
		return sessionID;
	}

	/**
	 * 得到用户类型
	 * 
	 * @return
	 */
	public static int getUserType()
	{
		Short userType = null;
		UserContext userContext = ThreadMessageContext.getUserContext();
		if (userContext != null)
			userType = userContext.getUserType();
		return userType;
	}
}
