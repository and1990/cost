package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.GroupDaoCustom;
import org.fire.cost.domain.Group;
import org.fire.cost.vo.GroupVO;
import org.fire.cost.vo.PageData;

import javax.persistence.Query;
import java.util.List;

/**
 * 注释：“组”dao扩展实现类
 * 作者：liutengfei 【刘腾飞】
 * 时间：13-12-28 下午9:42
 */
public class GroupDaoCustomImpl extends BaseJpaDaoSupport<Group, Long> implements GroupDaoCustom {

    /**
     * 获取所有的组数据
     *
     * @return
     */
    @Override
    public List<Group> getAllGroupData() {
        String sql = "select * from cost_group where group_status<>2 order by modify_time desc";
        Query query = entityManager.createNativeQuery(sql, Group.class);
        List<Group> resultList = query.getResultList();
        return resultList;
    }

    /**
     * 获取用户所在组（个人组）
     *
     * @param userId
     * @return
     */
    @Override
    public Group getGroupByUser(Long userId) {
        String sql = "select * from cost_group where group_id in";
        sql += "(select group_id from cost_group_user where group_id in ";
        sql += "(select group_id from cost_group_user ";
        if (userId != null || userId != 0) {
            sql += "where user_id=:user_id) ";
        }
        sql += "group by group_id having count(group_id)=1)";
        Query query = entityManager.createNativeQuery(sql, Group.class);
        if (userId != null || userId != 0) {
            query.setParameter("user_id", userId);
        }
        List<Group> resultList = query.getResultList();
        if (resultList != null && resultList.size() != 0) {
            return resultList.get(0);
        }
        return null;
    }

    /**
     * 根据过滤条件查询组信息
     *
     * @return
     */
    @Override
    public List<Group> getGroupByFilter(PageData<GroupVO> pageData) {
        String sql = "select * from cost_group order by modify_time desc";
        Query query = entityManager.createNativeQuery(sql, Group.class);
        int page = pageData.getPage();
        int pageSize = pageData.getPageSize();
        int start = (page - 1) * pageSize;
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        List<Group> resultList = query.getResultList();
        return resultList;
    }

    /**
     * 得到组数据总记录数
     *
     * @return
     */
    @Override
    public int getGroupTotal() {
        String sql = "select count(*) from cost_group";
        Query query = entityManager.createNativeQuery(sql);
        Integer total = Integer.valueOf(query.getSingleResult().toString());
        return total;
    }

    /**
     * 根据组类型获取数据
     *
     * @param groupType
     * @return
     */
    @Override
    public Group getGroupByType(int groupType) {
        String sql = "select * from cost_group where group_type=:groupType";
        Query query = entityManager.createNativeQuery(sql, Group.class);
        query.setParameter("groupType", groupType);
        List<Group> resultList = query.getResultList();
        if (resultList != null && resultList.size() != 0) {
            return resultList.get(0);
        }
        return null;
    }
}
