package org.fire.cost.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.RollbackException;

import org.fire.cost.dao.AccountDao;
import org.fire.cost.dao.UserDao;
import org.fire.cost.entity.Account;
import org.fire.cost.entity.User;
import org.fire.cost.enums.AccountTypeEnum;
import org.fire.cost.enums.ApproveEnum;
import org.fire.cost.service.AccountService;
import org.fire.cost.service.UserService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.AccountVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账单service实现类
 * 
 * @author liutengfei
 * 
 */
@Service
public class AccountServiceImpl implements AccountService
{
	@Resource
	private UserService userService;

	@Resource
	private AccountDao accountDao;

	@Resource
	private UserDao userDao;

	/**
	 * 根据过滤条件查询账单信息
	 */
	public List<AccountVO> getAccountByFilter(AccountVO vo)
	{
		List<AccountVO> voList = new ArrayList<AccountVO>();
		try
		{
			List<Account> accountList = accountDao.getAccountByFilter(vo);
			for (Account account : accountList)
			{
				voList.add(makeAccount2VO(account));
			}
			return voList;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RollbackException();
		}
	}

	/**
	 * 增加账单
	 */
	@Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
	public boolean addAccount(AccountVO vo)
	{
		boolean isTrue = true;
		try
		{
			vo.setIsApprove(Integer.valueOf(vo.getIsApproveName()));
			vo.setAccountType(Integer.valueOf(vo.getAccountTypeName()));
			Account account = makeVO2Account(vo, null);
			accountDao.save(account);
		} catch (Exception e)
		{
			isTrue = false;
			e.printStackTrace();
			throw new RollbackException();
		}
		return isTrue;
	}

	/**
	 * 修改账单
	 */
	@Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
	public boolean modifyAccount(AccountVO vo)
	{
		boolean isTrue = true;
		try
		{
			Long accountId = vo.getAccountId();
			Account account = accountDao.findOne(accountId);
			makeVO2Account(vo, account);
			accountDao.save(account);
		} catch (Exception e)
		{
			isTrue = false;
			e.printStackTrace();
			throw new RollbackException();
		}
		return isTrue;
	}

	/**
	 * 删除账单
	 */
	@Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
	public boolean deleteAccount(Long accountId)
	{
		boolean isTrue = true;
		try
		{
			accountDao.delete(accountId);
		} catch (Exception e)
		{
			isTrue = false;
			e.printStackTrace();
			throw new RollbackException();
		}
		return isTrue;
	}

	/**
	 * 将VO对象转换成实体对象
	 * 
	 * @param account
	 * @param account
	 * @return
	 * @throws ParseException
	 */
	private Account makeVO2Account(AccountVO vo, Account account) throws ParseException
	{
		if (account == null)
		{
			account = new Account();
			account.setCreateUser(userService.getLoginUserName());
			account.setCreateTime(new Date());
		}
		User user = userDao.findOne(AuthenticationUtil.getLoginUserId());
		account.setUser(user);
		account.setAccountMoney(vo.getAccountMoney());
		account.setAccountTime(DateUtil.makeStr2Date(vo.getAccountTime()));
		account.setIsApprove(vo.getIsApprove());
		account.setAccountType(vo.getAccountType());
		account.setAccountAccessory(vo.getAccountAccessory());
		account.setModifyUser(userService.getLoginUserName());
		account.setModifyTime(new Date());
		account.setAccountRemark(vo.getAccountRemark());
		return account;
	}

	/**
	 * 将实体对象转换成vo
	 * 
	 * @param account
	 * @return
	 */
	private AccountVO makeAccount2VO(Account account)
	{
		AccountVO vo = new AccountVO();
		vo.setUserId(account.getUser().getUserId());
		vo.setUserName(account.getUser().getUserName());
		vo.setAccountMoney(account.getAccountMoney());
		vo.setAccountTime(DateUtil.makeDate2Str(account.getAccountTime(), false));
		vo.setApproveTime(DateUtil.makeDate2Str(account.getApproveTime()));
		vo.setIsApprove(account.getIsApprove());
		vo.setIsApproveName(ApproveEnum.getName(account.getIsApprove()));
		vo.setAccountType(account.getAccountType());
		vo.setAccountTypeName(AccountTypeEnum.getName(account.getAccountType()));
		vo.setAccountAccessory(account.getAccountAccessory());
		vo.setCreateUser(account.getCreateUser());
		vo.setCreateTime(DateUtil.makeDate2Str(account.getCreateTime()));
		vo.setModifyUser(account.getModifyUser());
		vo.setModifyTime(DateUtil.makeDate2Str(account.getCreateTime()));
		vo.setAccountRemark(account.getAccountRemark());
		return vo;
	}
}
