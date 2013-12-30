package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.UserDaoCustom;
import org.fire.cost.entity.User;
import org.fire.cost.vo.UserVO;
import org.hibernate.ejb.QueryImpl;

import javax.persistence.Query;
import java.util.List;

/**
 * 用户dao扩展实现类
 *
 * @author liutengfei
 */
public class UserDaoCustomImpl extends BaseJpaDaoSupport<User, Long> implements UserDaoCustom
{
    /**
     * 根据过滤条件查询用户
     */
    public List<User> getUserByFilter(UserVO vo)
    {
        String sql = "select * from cost_user";
        Query query = entityManager.createNativeQuery(sql, User.class);
        if (query instanceof org.hibernate.ejb.QueryImpl)
        {
            ((QueryImpl<?>) query).getHibernateQuery().setCacheable(true);
        }
        List<User> resultList = query.getResultList();
        return resultList;
    }

    /**
     * 根据用户id查询用户
     *
     * @param userIds 用户id，格式：id1,id2,..idn
     * @return
     */
    @Override
    public List<User> getUsersByUserIds(String userIds)
    {
        String sql = "select * from cost_user where user_id in(" + userIds + ")";
        Query query = entityManager.createNativeQuery(sql, User.class);
        if (query instanceof org.hibernate.ejb.QueryImpl)
        {
            ((QueryImpl<?>) query).getHibernateQuery().setCacheable(true);
        }
        List<User> resultList = query.getResultList();
        return resultList;
    }

}