package org.fire.cost.dao.custom;

import org.fire.cost.domain.Account;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.PageData;

import java.util.List;

/**
 * 账单dao扩展类
 *
 * @author liutengfei
 */
public interface AccountDaoCustom {
    List<Account> getAccountByFilter(AccountVO vo, PageData<AccountVO> pageData);

    int getAccountTotal(AccountVO vo);

    List<AccountVO> getAccountGroupByAccountType(String accountStartTime, String accountEndTime);

    List<AccountVO> getAccountGroupByUser(String accountStartTime, String accountEndTime);
}
