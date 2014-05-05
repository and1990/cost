package org.fire.cost.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.context.UserContext;
import org.fire.cost.enums.HttpStatusEnum;
import org.fire.cost.enums.ResultEnum;
import org.fire.cost.service.AuthenticationService;
import org.fire.cost.service.CostContextService;
import org.fire.cost.service.UserService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.MessageUtil;
import org.fire.cost.vo.Message;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Namespace("/")
@Controller
public class AuthenticationAction extends BaseAction {

    //用户服务类
    @Resource
    private UserService userService;

    //验证服务类
    @Resource
    private AuthenticationService authenticationService;

    //上下文信息
    @Resource
    private CostContextService costContextService;

    //用户名
    private String loginName;
    //密码
    private String password;
    //返回信息
    private Message message = new Message();

    /**
     * 用户登录
     */
    @Action(value = "login", results = {@Result(type = "json", params = {"root", "message", "contentType", "text/html"})})
    public String userLogin() {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken();
            token.setUsername(loginName);
            token.setPassword(password.toCharArray());
            token.setRememberMe(true);
            //自动调用AuthenticationService的doGetAuthenticationInfo方法验证
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
            //建立用户上下文
            UserContext userContext = authenticationService.buildUserContext(loginName);
            //创建cookie
            setCookie(userContext);
            //更新登录时间
            userService.changeUserLoginTime(userContext.getUserId());
            //设置用户名
            HttpSession session = ServletActionContext.getRequest().getSession();
            session.setAttribute("userName", userContext.getUserName());
            //设置返回信息
            MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
        } catch (AuthenticationException e) {
            String msg = e.getMessage();
            if (msg.indexOf("COST") >= 0) {
                msg = msg.substring("COST:".length(), msg.length());
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, msg, null);
            } else {
                msg = "内部错误，请重试";
                MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, msg, null);
            }
        }
        return SUCCESS;
    }

    /**
     * 用户退出
     *
     * @return
     */
    @Action(value = "loginOut", results = {@Result(name = SUCCESS, location = "/cost/login.jsp")})
    public String loginOut() {
        String sessionId = AuthenticationUtil.getSessionId();
        if (sessionId != null && sessionId.trim().length() > 0) {
            costContextService.remove(sessionId);
        }
        return SUCCESS;

    }

    /**
     * 设置cookie
     *
     * @param userContext
     */
    private void setCookie(UserContext userContext) {
        HttpServletResponse response = ServletActionContext.getResponse();
        Cookie sessionIDCookie = new Cookie("sessionId", userContext.getSessionId());
        sessionIDCookie.setPath("/");
        response.addCookie(sessionIDCookie);
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
