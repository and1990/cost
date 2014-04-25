package org.fire.cost.dao;

import org.fire.cost.dao.custom.UserDaoCustom;
import org.fire.cost.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 用户dao提供类
 *
 * @author liutengfei
 */
public interface UserDao extends JpaRepository<User, Long>, UserDaoCustom {

    /**
     * 根据登录名查询用户
     *
     * @param name
     */
    User findByLoginName(String name);

    /**
     * 根据userId查询用户
     *
     * @param userId
     */
    User findByUserId(Long userId);

    /**
     * 更新用户登录时间
     *
     * @param userId
     */
    @Modifying
    @Query("update User set loginTime=now() where userId=?1")
    int changeUserLoginTime(Long userId);

}
