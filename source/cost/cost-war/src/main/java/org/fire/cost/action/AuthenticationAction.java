package org.fire.cost.action;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.fire.cost.context.CostContextService;
import org.fire.cost.context.ThreadMessageContext;
import org.fire.cost.context.UserContext;
import org.fire.cost.enums.HttpStatusEnum;
import org.fire.cost.enums.ResultEnum;
import org.fire.cost.service.AuthenticationService;
import org.fire.cost.service.UserService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.MessageUtil;
import org.fire.cost.vo.Message;
import org.jboss.resteasy.annotations.GZIP;
import org.springframework.stereotype.Controller;

@Controller
@Path("auth")
public class AuthenticationAction
{

	//用户服务 
	@Resource
	private UserService userService;

	//验证服务类
	@Resource
	private AuthenticationService authenticationService;

	//项目上下文
	@Resource
	private CostContextService costContextService;

	/**
	 * 用户登录
	 * 
	 * @param vo
	 */
	@POST
	@Path("userLogin")
	@Produces(MediaType.APPLICATION_JSON)
	public Message userLogin(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@QueryParam(value = "loginName") String loginName, @QueryParam(value = "password") String password)
	{
		Message message = new Message();
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken();
		token.setUsername(loginName);
		token.setPassword(password.toCharArray());
		token.setRememberMe(true);
		try
		{
			currentUser.login(token);//自动调用AuthenticationService的doGetAuthenticationInfo方法验证
			UserContext userContext = authenticationService.buildUserContext(loginName);
			//创建cookie
			setCookie(request, response, userContext);
			userService.changeUserLoginTime(userContext.getUserId());
			ThreadMessageContext.set(userContext);
			MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, userContext);
		} catch (Exception e)
		{
			e.printStackTrace();
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, null, null);
		}
		return message;
	}

	/**
	 * 心跳检查
	 * 
	 * @return
	 */
	@POST
	@Path("/palpitation")
	@Produces("application/json")
	@GZIP
	public Message palpitation()
	{
		String sessionId = AuthenticationUtil.getSessionId();
		String uuid = AuthenticationUtil.getUUId();

		UserContext userContext = costContextService.getUserContext(sessionId);
		Message message = new Message();
		if (userContext != null)
		{
			if (!userContext.getUuid().equals(uuid))
			{
				userContext = null;
			}
		}
		message.setData(userContext);
		return message;
	}

	@POST
	@Path("/loginOut")
	@Produces("application/json")
	@GZIP
	public Message loginPostOut()
	{
		clear();

		return new Message();
	}

	@GET
	@Path("/loginOut")
	@Produces("application/json")
	@GZIP
	public Message loginOut()
	{
		clear();
		return new Message();

	}

	/**
	 * 设置cookie
	 * 
	 * @param request
	 * @param response
	 * @param userContext
	 */
	private void setCookie(HttpServletRequest request, HttpServletResponse response, UserContext userContext)
	{
		Cookie[] cookieArr = request.getCookies();
		if (cookieArr == null || cookieArr.length <= 2)
		{
			Cookie userIdCookie = new Cookie("userId", String.valueOf(userContext.getUserId()));
			userIdCookie.setPath("/");
			Cookie sessionIdCookie = new Cookie("sessionId", userContext.getSessionId());
			sessionIdCookie.setPath("/");
			Cookie uuidCookie = new Cookie("uuid", userContext.getUuid());
			uuidCookie.setPath("/");
			response.addCookie(userIdCookie);
			response.addCookie(sessionIdCookie);
			response.addCookie(uuidCookie);
		} else
		{
			for (Cookie cookie : cookieArr)
			{
				String name = cookie.getName();
				if ("userId".equals(name) || "sessionId".equals(name) || "uuid".equals(name))
				{
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

	/**
	 * 清空缓存
	 */
	private void clear()
	{
		String sessionId = AuthenticationUtil.getSessionId();

		if (sessionId != null && sessionId.trim().length() > 0)
		{
			costContextService.remove(sessionId);
		}

	}

}
