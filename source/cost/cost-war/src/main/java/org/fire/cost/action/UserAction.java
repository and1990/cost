package org.fire.cost.action;

import org.fire.cost.enums.HttpStatusEnum;
import org.fire.cost.enums.ResultEnum;
import org.fire.cost.service.UserService;
import org.fire.cost.util.MessageUtil;
import org.fire.cost.vo.Message;
import org.fire.cost.vo.UserVO;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Controller
@Path("user")
public class UserAction
{
	@Resource
	private UserService userService;

	/**
	 * 根据过滤条件查询用户
	 * 
	 * @param vo
	 * @return
	 */
	@POST
	@Path("getUserByFilter")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message getUserByFilter(UserVO vo)
	{
		Message message = new Message();
		if (vo == null)
		{
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
			return message;
		}
		try
		{
			List<UserVO> voList = userService.getUserByFilter(vo);
			MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, voList);
		} catch (Exception e)
		{
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, null, null);
		}
		return message;
	}

	/**
	 * 增加用户
	 * 
	 * @param vo
	 */
	@POST
	@Path("addUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message addUser(UserVO vo)
	{
		Message message = new Message();
		if (vo == null)
		{
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
			return message;
		}
		try
		{
			userService.addUser(vo);
			MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
		} catch (Exception e)
		{
			e.printStackTrace();
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
		}
		return message;
	}

	/**
	 * 修改用户
	 * 
	 * @param vo
	 * @return
	 */
	@POST
	@Path("modifyUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message modifyUser(UserVO vo)
	{
		Message message = new Message();
		if (vo == null)
		{
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
			return message;
		}
		try
		{
			userService.modifyUser(vo);
			MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
		} catch (Exception e)
		{
			e.printStackTrace();
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
		}
		return message;
	}

	/**
	 * 删除用户信息
	 * 
	 * @param
	 * @return
	 */
	@POST
	@Path("deleteUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message deleteUser(UserVO userVO)
	{
		Message message = new Message();
		if (userVO == null || userVO.getUserId() == null)
		{
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
			return message;
		}
		try
		{
			if (userService.deleteUser(userVO.getUserId()))
				MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
			else
				MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, null, null);
		} catch (Exception e)
		{
			e.printStackTrace();
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
		}
		return message;
	}

}