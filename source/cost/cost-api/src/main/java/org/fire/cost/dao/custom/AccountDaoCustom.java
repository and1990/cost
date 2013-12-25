package org.fire.cost.dao.custom;

import java.util.List;

import org.fire.cost.entity.Account;
import org.fire.cost.vo.AccountVO;

/**
 * 账单dao扩展类
 * 
 * @author liutengfei
 * 
 */
public interface AccountDaoCustom
{
	List<Account> getAccountByFilter(AccountVO vo);
}
