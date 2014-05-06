package org.fire.cost.dao;

import org.fire.cost.dao.custom.IncomeDaoCustom;
import org.fire.cost.domain.Income;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 收入dao
 *
 * @author liutengfei
 */
public interface IncomeDao extends JpaRepository<Income, Long>, IncomeDaoCustom {

}
