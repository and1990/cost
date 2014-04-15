package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.UserDaoCustom;
import org.fire.cost.entity.User;
import org.fire.cost.vo.PageData;
import org.fire.cost.vo.UserVO;
import org.hibernate.ejb.QueryImpl;

import javax.persistence.Query;
import java.util.List;

/**
 * 用户dao扩展实现类
 *
 * @author liutengfei
 */
public class UserDaoCustomImpl extends BaseJpaDaoSupport<User, Long> implements UserDaoCustom {
    /**
     * 根据过滤条件查询用户
     */
    public List<User> getUserByFilter(UserVO vo, PageData<UserVO> pageData) {
        String sql = "select * from cost_user where 1=1";
        String userName = vo.getUserName();
        if (userName != null && userName.trim().length() != 0) {
            sql += " and user_name like :userName";
        }
        Integer admin = vo.getIsAdmin();
        if (admin != null && admin != 0) {
            sql += " and is_admin=:isAdmin";
        }
        Integer userStatus = vo.getUserStatus();
        if (userStatus != null && userStatus != 0) {
            sql += " and user_status=:userStatus";
        }
        String startTime = vo.getStartTime();
        if (startTime != null && startTime.trim().length() != 0) {
            sql += " and create_time>=:startTime";
        }
        String endTime = vo.getEndTime();
        if (endTime != null && endTime.trim().length() != 0) {
            sql += " and create_time<=:endTime";
        }
        Query query = entityManager.createNativeQuery(sql, User.class);
        if (query instanceof org.hibernate.ejb.QueryImpl) {
            ((QueryImpl<?>) query).getHibernateQuery().setCacheable(true);
        }
        if (userName != null && userName.trim().length() != 0) {
            query.setParameter("userName", "%" + userName + "%");
        }
        if (admin != null && admin != 0) {
            query.setParameter("isAdmin", admin);
        }
        if (userStatus != null && userStatus != 0) {
            query.setParameter("userStatus", userStatus);
        }
        if (startTime != null && startTime.trim().length() != 0) {
            query.setParameter("startTime", startTime);
        }
        if (endTime != null && endTime.trim().length() != 0) {
            query.setParameter("endTime", endTime);
        }
        query.setFirstResult(pageData.getPage() - 1);
        query.setMaxResults(pageData.getPageSize());
        List<User> resultList = query.getResultList();
        return resultList;
    }

    /**
     * 获取用户总记录数
     *
     * @return
     */
    public int getUserDataTotal() {
        String sql = "select count(*) from cost_user";
        Query query = entityManager.createNativeQuery(sql);
        int total = query.getFirstResult();
        return total;
    }

    /**
     * 根据用户id查询用户
     *
     * @param userIds 用户id，格式：id1,id2,..idn
     * @return
     */
    @Override
    public List<User> getUsersByUserIds(String userIds) {
        String sql = "select * from cost_user where user_id in(" + userIds + ")";
        Query query = entityManager.createNativeQuery(sql, User.class);
        if (query instanceof org.hibernate.ejb.QueryImpl) {
            ((QueryImpl<?>) query).getHibernateQuery().setCacheable(true);
        }
        List<User> resultList = query.getResultList();
        return resultList;
    }

}