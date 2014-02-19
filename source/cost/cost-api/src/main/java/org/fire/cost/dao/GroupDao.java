package org.fire.cost.dao;

import org.fire.cost.dao.custom.GroupDaoCustom;
import org.fire.cost.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * 注释：“组”dao
 * 作者：liutengfei 【刘腾飞】
 * 时间：13-12-28 下午9:42
 */
public interface GroupDao extends JpaRepository<Group, Long>, GroupDaoCustom
{
    /**
     * 查询状态是“可用”状态的组信息
     *
     * @return
     */
    @Query(value = "From Group where groupStatus=2")
    public Group findGroupByStatus();
}
