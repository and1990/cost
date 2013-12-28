package org.fire.cost.service;

import org.fire.cost.vo.GroupVO;

import java.util.List;

/**
 * “组”业务处理类
 *
 * @author liutengfei
 */
public interface GroupService
{
    List<GroupVO> getGroupByFilter(GroupVO vo);

    boolean addGroup(GroupVO vo);

    boolean updateGroup(GroupVO vo);

    boolean deleteGroup(Long groupId);
}
