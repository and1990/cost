package org.fire.cost.util;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户验证工具类
 *
 * @author liutengfei
 */
public class UserUtil {

    /**
     * 得到登录用户id
     *
     * @return
     */
    public static Long getLoginUserId() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Object userId = request.getSession().getAttribute("userId");
        if (userId != null) {
            return Long.valueOf(userId.toString());
        }
        return 0L;
    }

    /**
     * 得到当前用户名称
     *
     * @return
     */
    public static String getUserName() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Object loginName = request.getSession().getAttribute("loginName");
        if (loginName != null) {
            return loginName.toString();
        }
        return null;
    }

}
