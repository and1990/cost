package org.fire.cost.service;

import net.rubyeye.xmemcached.exception.MemcachedException;
import org.fire.cost.context.UserContext;

import java.util.concurrent.TimeoutException;

/**
 * cost信息上下文
 * 
 * @author liutengfei
 * 
 */
public interface CostContextService
{

	/**
	 * 增加上下文配置项
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            值
	 */
	void add(String key, Object value);

	/**
	 * 根据key获取上下文配置信息项
	 * 
	 * @param key
	 * @return
	 * @throws InterruptedException
	 * @throws MemcachedException
	 * @throws TimeoutException
	 */
	Object get(String key) throws InterruptedException, MemcachedException, TimeoutException;

	/**
	 * 延迟配置项失效时间
	 * 
	 * @param key
	 * @param time
	 * @return
	 * @throws InterruptedException
	 * @throws MemcachedException
	 * @throws TimeoutException
	 */
	Object delay(String key, int time) throws InterruptedException, MemcachedException, TimeoutException;

	/**
	 * 延迟配置项失效时间
	 * 
	 * @param key
	 * @return
	 * @throws InterruptedException
	 * @throws MemcachedException
	 * @throws TimeoutException
	 */
	public Object delay(String key) throws InterruptedException, MemcachedException, TimeoutException;

	/**
	 * 移除会话
	 * 
	 * @param key
	 */
	public void remove(String key);

	/**
	 * 获取缓存用户上下文
	 * 
	 * @param sessionId
	 *            用户全局唯一id
	 * @return 获取缓存用户上下文
	 */
	public UserContext getUserContext(String sessionId);

}
