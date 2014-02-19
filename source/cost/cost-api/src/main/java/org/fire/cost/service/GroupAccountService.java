package org.fire.cost.service;

import org.fire.cost.vo.GroupAccountVO;

import java.util.List;

/**
 * 注释：组消费service
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-19 下午9:01
 */
public interface GroupAccountService
{
    /**
     * 根据查询条件查询组消费信息
     *
     * @param vo
     * @return
     */
    public List<GroupAccountVO> getGroupAccountByFilter(GroupAccountVO vo);

    /**
     * 添加组消费
     *
     * @param groupId 组ID
     * @return
     */
    public boolean addGroupAccount(Long groupId);
}
