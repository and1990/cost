package org.fire.cost.service;

import org.fire.cost.vo.GroupVO;
import org.fire.cost.vo.PageData;

import java.util.List;

/**
 * “组”业务处理类
 *
 * @author liutengfei
 */
public interface GroupService {
    List<GroupVO> getGroupByFilter(PageData<GroupVO> pageData);

    boolean addGroup(GroupVO vo);

    boolean modifyGroup(GroupVO vo);

    boolean deleteGroup(Long groupId);

    int getGroupTotal();

    List<GroupVO> getAllGroupData();

    GroupVO getGroupByUser();
}
