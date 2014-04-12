package org.fire.cost.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;


/**
 * 注释：跳转action
 * 时间：2014-04-12 上午9:01
 * 作者：liutengfei【刘腾飞】
 */
@Namespace("/")
@Controller
public class SkipAction extends BaseAction {

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @Action(value = "login", results = {@Result(name = SUCCESS, location = "/cost/login.jsp")})
    public String skipToLogin() {
        return SUCCESS;
    }

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @Action(value = "regist", results = {@Result(name = SUCCESS, location = "/cost/regist.jsp")})
    public String skipToRegist() {
        return SUCCESS;
    }

    /**
     * 跳转到主页面
     *
     * @return
     */
    @Action(value = "view", results = {@Result(name = SUCCESS, location = "/cost/main.jsp")})
    public String skipToView() {
        return SUCCESS;
    }


}
