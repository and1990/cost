package org.fire.cost.dao;

import org.fire.cost.dao.custom.AccountDaoCustom;
import org.fire.cost.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 账单dao
 * 
 * @author liutengfei
 * 
 */
public interface AccountDao extends JpaRepository<Account, Long>, AccountDaoCustom
{

}
