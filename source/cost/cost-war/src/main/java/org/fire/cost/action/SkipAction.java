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
    @Action(value = "welcome", results = {@Result(name = SUCCESS, location = "/cost/login.jsp")})
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

    /**
     * 跳转到用户
     *
     * @return
     */
    @Action(value = "user", results = {@Result(name = SUCCESS, location = "/cost/user.jsp")})
    public String skipToUserData() {
        return SUCCESS;
    }

    /**
     * 跳转到组员信息
     *
     * @return
     */
    @Action(value = "group", results = {@Result(name = SUCCESS, location = "/cost/group.jsp")})
    public String skipToGroupData() {
        return SUCCESS;
    }

    /**
     * 跳转到消费
     *
     * @return
     */
    @Action(value = "account", results = {@Result(name = SUCCESS, location = "/cost/account.jsp")})
    public String skipToAccountData() {
        return SUCCESS;
    }

    /**
     * 跳转到组消费
     *
     * @return
     */
    @Action(value = "groupAccount", results = {@Result(name = SUCCESS, location = "/cost/groupAccount.jsp")})
    public String skipToGroupAccountData() {
        return SUCCESS;
    }

    /**
     * 跳转到公告
     *
     * @return
     */
    @Action(value = "notice", results = {@Result(name = SUCCESS, location = "/cost/notice.jsp")})
    public String skipToNotice() {
        return SUCCESS;
    }
}
