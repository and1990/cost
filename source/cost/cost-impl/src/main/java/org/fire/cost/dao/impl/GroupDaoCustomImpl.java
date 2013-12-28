package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.GroupDaoCustom;
import org.fire.cost.entity.Group;
import org.fire.cost.vo.GroupVO;

import javax.persistence.Query;
import java.util.List;

/**
 * 注释：“组”dao扩展实现类
 * 作者：liutengfei 【刘腾飞】
 * 时间：13-12-28 下午9:42
 */
public class GroupDaoCustomImpl extends BaseJpaDaoSupport<Group, Long> implements GroupDaoCustom
{

    /**
     * 根据过滤条件查询组信息
     *
     * @param vo 组过滤条件
     * @return
     */
    @Override
    public List<Group> getGroupByFilter(GroupVO vo)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select * from cost_group ");
        Query query = entityManager.createNativeQuery(buffer.toString());
        List<Group> groupList = query.getResultList();
        return groupList;
    }
}