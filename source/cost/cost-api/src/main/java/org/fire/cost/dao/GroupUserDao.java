package org.fire.cost.dao;

import org.fire.cost.domain.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 注释：组、用户关系DAO
 * 时间：2014-04-28 下午9:52
 * 作者：liutengfei【刘腾飞】
 */
public interface GroupUserDao extends JpaRepository<GroupUser, Long> {
    /**
     * 根据组id查找
     *
     * @param groupId
     * @return
     */
    List<GroupUser> findByGroupId(Long groupId);
}
