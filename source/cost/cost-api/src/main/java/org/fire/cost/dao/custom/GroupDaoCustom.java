package org.fire.cost.dao.custom;

import org.fire.cost.entity.Group;
import org.fire.cost.vo.GroupVO;

import java.util.List;

/**
 * “组”dao扩展类
 *
 * @author liutengfei
 */
public interface GroupDaoCustom
{
    /**
     * 根据过滤条件查询“组”数据
     * @param vo 组过滤条件
     * @return
     */
    List<Group> getGroupByFilter(GroupVO vo);
}
