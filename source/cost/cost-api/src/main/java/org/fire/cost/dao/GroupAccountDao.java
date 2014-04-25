package org.fire.cost.dao;

import org.fire.cost.dao.custom.GroupAccountDaoCustom;
import org.fire.cost.domain.GroupAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 注释：组消费Dao
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-19 下午8:58
 */
public interface GroupAccountDao extends JpaRepository<GroupAccount, Long>, GroupAccountDaoCustom {
}
