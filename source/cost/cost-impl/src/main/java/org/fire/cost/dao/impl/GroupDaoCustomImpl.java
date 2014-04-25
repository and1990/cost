package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.GroupDaoCustom;
import org.fire.cost.domain.Group;
import org.fire.cost.vo.GroupVO;
import org.fire.cost.vo.PageData;
import org.hibernate.ejb.QueryImpl;

import javax.persistence.Query;
import java.util.List;

/**
 * 注释：“组”dao扩展实现类
 * 作者：liutengfei 【刘腾飞】
 * 时间：13-12-28 下午9:42
 */
public class GroupDaoCustomImpl extends BaseJpaDaoSupport<Group, Long> implements GroupDaoCustom {

    /**
     * 根据过滤条件查询组信息
     *
     * @param vo 组过滤条件
     * @return
     */
    @Override
    public List<Group> getGroupByFilter(GroupVO vo, PageData<GroupVO> pageData) {
        String sql = "select * from cost_group order by modify_time desc";
        Query query = entityManager.createNativeQuery(sql, Group.class);
        if (query instanceof org.hibernate.ejb.QueryImpl) {
            ((QueryImpl<?>) query).getHibernateQuery().setCacheable(true);
        }
        int page = pageData.getPage();
        int pageSize = pageData.getPageSize();
        int start = (page - 1) * pageSize;
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        List<Group> resultList = query.getResultList();
        return resultList;
    }

    /**
     * 得打组数据总记录数
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
}
