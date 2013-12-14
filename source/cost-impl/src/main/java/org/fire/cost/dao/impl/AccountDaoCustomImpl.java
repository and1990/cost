package org.fire.cost.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.fire.cost.dao.custom.AccountDaoCustom;
import org.fire.cost.entity.Account;
import org.fire.cost.vo.AccountVO;

/**
 * 账单扩展实现类
 * 
 * @author liutengfei
 * 
 */
public class AccountDaoCustomImpl extends BaseJpaDaoSupport<Account, Long> implements AccountDaoCustom
{

	/**
	 * 根据过滤条件查询账单信息
	 */
	public List<Account> getAccountByFilter(AccountVO vo)
	{
		String sql = "select * from cost_account";
		Query query = entityManager.createNativeQuery(sql, Account.class);
		List<Account> resultList = query.getResultList();
		return resultList;
	}

}
