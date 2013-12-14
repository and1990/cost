package org.fire.cost.action;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.fire.cost.context.CostContextService;
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
	public Message userLogin(@QueryParam(value = "loginName") String loginName,
			@QueryParam(value = "password") String password)
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
			userService.changeUserLoginTime(userContext.getUserId());
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

	private void clear()
	{
		String sessionId = AuthenticationUtil.getSessionId();

		if (sessionId != null && sessionId.trim().length() > 0)
		{
			costContextService.remove(sessionId);
		}

	}
}
