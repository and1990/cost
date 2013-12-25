package org.fire.cost.util;

import org.fire.cost.enums.HttpStatusEnum;
import org.fire.cost.enums.ResultEnum;
import org.fire.cost.vo.Message;

/**
 * 信息设置工具类
 * 
 * @author liutengfei
 * 
 */
public class MessageUtil
{
	/**
	 * 设置返回信息
	 * 
	 * @param statusEnum
	 */
	public static void setMessage(Message message, ResultEnum resultEnum, HttpStatusEnum statusEnum,
			String exceptionMsg, Object data)
	{
		message.setResult(resultEnum.getCode());
		message.setStatus(statusEnum.getCode());
		message.setExceptionMsg(exceptionMsg);
		message.setData(data);
	}
}
