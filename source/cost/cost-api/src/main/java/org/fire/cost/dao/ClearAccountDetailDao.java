package org.fire.cost.dao;

import org.fire.cost.dao.custom.ClearAccountDetailDaoCustom;
import org.fire.cost.domain.ClearAccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 注释：结算明细Dao
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-19 下午8:58
 */
public interface ClearAccountDetailDao extends JpaRepository<ClearAccountDetail, Long>, ClearAccountDetailDaoCustom {
}
