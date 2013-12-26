package org.fire.cost.context;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author liutengfei
 * 
 */
public class ThreadMessageContext
{
	private static final ThreadLocal<UserContext> threadLocal = new ThreadLocal<UserContext>();

	public static final List<UserContext> userContextList = new ArrayList<UserContext>();

	/**
	 * 设置值
	 * 
	 * @param userContext
	 */
	public static void set(UserContext userContext)
	{
		if (userContext != null)
		{
			//			threadLocal.remove();
			//			threadLocal.set(userContext);
			userContextList.clear();
			userContextList.add(userContext);
		}

	}

	/**
	 * 取出值
	 * 
	 * @return
	 */
	public static UserContext getUserContext()
	{
		//		UserContext userContext = threadLocal.get();
		UserContext userContext = userContextList.get(0);
		return userContext;
	}

	/**
	 * 删除值
	 */
	public static void removePreviousMessageContext()
	{
		threadLocal.set(null);
	}
}
