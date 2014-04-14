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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @Action(value = "userLogin", results = {@Result(type = "json", params = {"root", "message", "contentType", "text/html"})})
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
            userService.changeUserLoginTime(userContext.getUserId());
            MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
        } catch (AuthenticationException e) {
            String msg = e.getMessage();
            if (msg.indexOf("COST") >= 0) {
                msg = msg.substring("COST:".length(), msg.length());
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, msg, null);
            }
        }
        return SUCCESS;
    }

    /**
     * 用户退出
     *
     * @return
     */
    @Action(value = "loginOut", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
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
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Cookie[] cookieArr = request.getCookies();
        if (cookieArr == null || cookieArr.length <= 2) {
            Cookie userIdCookie = new Cookie("userId", String.valueOf(userContext.getUserId()));
            userIdCookie.setPath("/");
            Cookie sessionIdCookie = new Cookie("sessionId", userContext.getSessionId());
            sessionIdCookie.setPath("/");
            Cookie uuidCookie = new Cookie("uuid", userContext.getUuid());
            uuidCookie.setPath("/");
            response.addCookie(userIdCookie);
            response.addCookie(sessionIdCookie);
            response.addCookie(uuidCookie);
        } else {
            for (Cookie cookie : cookieArr) {
                String name = cookie.getName();
                if ("userId".equals(name) || "sessionId".equals(name) || "uuid".equals(name)) {
                    if ("userId".equals(name))
                        cookie.setValue(String.valueOf(userContext.getUserId()));
                    else if ("sessionId".equals(name))
                        cookie.setValue(userContext.getSessionId());
                    else if ("uuid".equals(name))
                        cookie.setValue(userContext.getUuid());
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
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
