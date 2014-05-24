package org.fire.cost.dao.custom;

import org.fire.cost.domain.Group;
import org.fire.cost.vo.GroupVO;
import org.fire.cost.vo.PageData;

import java.util.List;

/**
 * “组”dao扩展类
 *
 * @author liutengfei
 */
public interface GroupDaoCustom {
    /**
     * 根据过滤条件查询“组”数据
     *
     * @return
     */
    List<Group> getGroupByFilter(GroupVO groupVO, PageData<GroupVO> pageData);

    /**
     * 得到组数据总记录数
     *
     * @return
     */
    int getGroupTotal();

    /**
     * 根据用户获取所在组（个人组）
     *
     * @param userId
     * @return
     */
    Group getGroupByUser(Long userId);

    /**
     * 根据组类型获取数据
     *
     * @param groupType
     * @return
     */
    Group getGroupByType(int groupType);
}
