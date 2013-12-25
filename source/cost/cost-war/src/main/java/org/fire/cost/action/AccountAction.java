package org.fire.cost.action;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.fire.cost.enums.HttpStatusEnum;
import org.fire.cost.enums.ResultEnum;
import org.fire.cost.service.AccountService;
import org.fire.cost.util.MessageUtil;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.Message;
import org.springframework.stereotype.Controller;

/**
 * 账单信息表
 * 
 * @author liutengfei
 * 
 */
@Controller
@Path("account")
public class AccountAction
{

	@Resource
	private AccountService accountService;

	/**
	 * 根据过滤条件查询账单信息
	 * 
	 * @param vo
	 * @return
	 */
	@POST
	@Path("getAccountByFilter")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message getAccountByFilter(AccountVO vo)
	{
		Message message = new Message();
		if (vo == null)
		{
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
			return message;
		}
		try
		{
			List<AccountVO> voList = accountService.getAccountByFilter(vo);
			MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, voList);
		} catch (Exception e)
		{
			e.printStackTrace();
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
		}
		return message;
	}

	/**
	 * 增加账单
	 * 
	 * @param vo
	 * @return
	 */
	@POST
	@Path("addAccount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message addAccount(AccountVO vo)
	{
		Message message = new Message();
		if (vo == null)
		{
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
			return message;
		}
		try
		{
			if (accountService.addAccount(vo))
			{
				MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
		}
		return message;
	}

	/**
	 * 修改账单信息
	 * 
	 * @param vo
	 * @return
	 */
	@POST
	@Path("modifyAccount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message modifyAccount(AccountVO vo)
	{
		Message message = new Message();
		if (vo == null)
		{
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
			return message;
		}
		try
		{
			if (accountService.modifyAccount(vo))
			{
				MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
		}
		return message;
	}

	/**
	 * 删除账单信息
	 * 
	 * @param vo
	 * @return
	 */
	@POST
	@Path("deleteAccount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message deleteAccount(@QueryParam(value = "accountId") Long accountId)
	{
		Message message = new Message();
		if (accountId == null || accountId == 0)
		{
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
			return message;
		}
		try
		{
			if (accountService.deleteAccount(accountId))
			{
				MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
		}
		return message;
	}

}
