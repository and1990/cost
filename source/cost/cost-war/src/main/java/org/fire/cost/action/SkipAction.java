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
     * 跳转到菜单
     *
     * @return
     */
    @Action(value = "showMenu", results = {@Result(name = SUCCESS, location = "/cost/menu.jsp")})
    public String skipToMenu() {
        return SUCCESS;
    }

    /**
     * 跳转到公告
     *
     * @return
     */
    @Action(value = "showNotice", results = {@Result(name = SUCCESS, location = "/cost/notice.jsp")})
    public String skipToNotice() {
        return SUCCESS;
    }

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
    @Action(value = "showUser", results = {@Result(name = SUCCESS, location = "/cost/user.jsp")})
    public String skipToUserData() {
        return SUCCESS;
    }

    /**
     * 跳转到组员信息
     *
     * @return
     */
    @Action(value = "showGroup", results = {@Result(name = SUCCESS, location = "/cost/group.jsp")})
    public String skipToGroupData() {
        return SUCCESS;
    }

    /**
     * 跳转到消费
     *
     * @return
     */
    @Action(value = "showAccount", results = {@Result(name = SUCCESS, location = "/cost/account.jsp")})
    public String skipToAccountData() {
        return SUCCESS;
    }

    /**
     * 跳转到组消费
     *
     * @return
     */
    @Action(value = "showGroupAccount", results = {@Result(name = SUCCESS, location = "/cost/groupAccount.jsp")})
    public String skipToGroupAccountData() {
        return SUCCESS;
    }

    /**
     * 跳转到流水账
     *
     * @return
     */
    @Action(value = "showStream", results = {@Result(name = SUCCESS, location = "/cost/stream.jsp")})
    public String skipToStreamData() {
        return SUCCESS;
    }

    /**
     * 跳转到饼状图
     *
     * @return
     */
    @Action(value = "showPie", results = {@Result(name = SUCCESS, location = "/cost/pieChart.jsp")})
    public String skipToPieChart() {
        return SUCCESS;
    }

    /**
     * 跳转到柱状图
     *
     * @return
     */
    @Action(value = "showColumn", results = {@Result(name = SUCCESS, location = "/cost/columnChart.jsp")})
    public String skipToColumnChart() {
        return SUCCESS;
    }

    /**
     * 跳转到线形图
     *
     * @return
     */
    @Action(value = "showLine", results = {@Result(name = SUCCESS, location = "/cost/lineChart.jsp")})
    public String skipToLineChart() {
        return SUCCESS;
    }

    /**
     * 跳转到流水账柱状图
     *
     * @return
     */
    @Action(value = "showStreamColumn", results = {@Result(name = SUCCESS, location = "/cost/streamColumnChart.jsp")})
    public String skipToStreamColumnChart() {
        return SUCCESS;
    }
}
