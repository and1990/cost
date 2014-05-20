package org.fire.cost.dao;

import org.fire.cost.dao.custom.ClearAccountDaoCustom;
import org.fire.cost.domain.ClearAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 注释：结算Dao
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-19 下午8:58
 */
public interface ClearAccountDao extends JpaRepository<ClearAccount, Long>, ClearAccountDaoCustom {
}
