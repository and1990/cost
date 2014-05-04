package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.UserDaoCustom;
import org.fire.cost.domain.User;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.PageData;
import org.fire.cost.vo.UserVO;
import org.hibernate.ejb.QueryImpl;

import javax.persistence.Query;
import java.text.ParseException;
import java.util.Date;
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
        String filterSQL = getFilterSQL(vo);
        if (filterSQL != null && filterSQL.trim().length() != 0) {
            sql += filterSQL;
        }
        Query query = entityManager.createNativeQuery(sql, User.class);
        if (query instanceof org.hibernate.ejb.QueryImpl) {
            ((QueryImpl<?>) query).getHibernateQuery().setCacheable(true);
        }
        setAliasValue(vo, query);
        if (vo.isPage()) {
            query.setFirstResult(pageData.getPage() - 1);
            query.setMaxResults(pageData.getPageSize());
        }
        List<User> resultList = query.getResultList();
        return resultList;
    }

    /**
     * 获取用户总记录数
     *
     * @return
     */
    public int getUserDataTotal(UserVO vo) {
        String sql = "select count(*) from cost_user where 1=1";
        String filterSQL = getFilterSQL(vo);
        if (filterSQL != null && filterSQL.trim().length() != 0) {
            sql += filterSQL;
        }
        Query query = entityManager.createNativeQuery(sql);
        setAliasValue(vo, query);
        int total = Integer.valueOf(query.getSingleResult().toString());
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

    /**
     * 得到过滤条件
     *
     * @param vo
     * @return
     */
    private String getFilterSQL(UserVO vo) {
        String filterSQL = "";
        String userName = vo.getUserName();
        if (userName != null && userName.trim().length() != 0) {
            filterSQL += " and user_name like :userName";
        }
        Integer admin = vo.getUserType();
        if (admin != null && admin != 0) {
            filterSQL += " and user_type=:userType";
        }
        Integer userStatus = vo.getUserStatus();
        if (userStatus != null && userStatus != 0) {
            filterSQL += " and user_status=:userStatus";
        }
        String startTime = vo.getStartTime();
        if (startTime != null && startTime.trim().length() != 0) {
            filterSQL += " and create_time>=:startTime";
        }
        String endTime = vo.getEndTime();
        if (endTime != null && endTime.trim().length() != 0) {
            filterSQL += " and create_time<:endTime";
        }
        return filterSQL;
    }

    /**
     * 设置别名值
     */
    private void setAliasValue(UserVO vo, Query query) {
        String userName = vo.getUserName();
        if (userName != null && userName.trim().length() != 0) {
            query.setParameter("userName", "%" + userName + "%");
        }
        Integer userType = vo.getUserType();
        if (userType != null && userType != 0) {
            query.setParameter("userType", userType);
        }
        Integer userStatus = vo.getUserStatus();
        if (userStatus != null && userStatus != 0) {
            query.setParameter("userStatus", userStatus);
        }
        String startTime = vo.getStartTime();
        if (startTime != null && startTime.trim().length() != 0) {
            query.setParameter("startTime", startTime);
        }
        String endTime = vo.getEndTime();
        if (endTime != null && endTime.trim().length() != 0) {
            Date date = null;
            try {
                date = DateUtil.makeStr2Date(endTime, false);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            endTime = DateUtil.makeDate2Str(DateUtil.addDays(date, 1), false);
            query.setParameter("endTime", endTime);
        }
    }

}