package org.fire.cost.util;

import org.fire.cost.enums.HttpStatusEnum;
import org.fire.cost.enums.ResultEnum;
import org.fire.cost.vo.Message;

/**
 * 信息设置工具类
 *
 * @author liutengfei
 */
public class MessageUtil {
    /**
     * 设置返回信息
     *
     * @param message message信息
     * @param resultEnum 返回结果枚举类
     * @param statusEnum 返回状态枚举类
     * @param descMsg 描述信息
     * @param data 返回数据
     */
    public static void setMessage(Message message, ResultEnum resultEnum, HttpStatusEnum statusEnum,
                                  String descMsg, Object data) {
        message.setResult(resultEnum.getCode());
        message.setStatus(statusEnum.getCode());
        message.setDescMsg(descMsg);
        message.setData(data);
    }
}
