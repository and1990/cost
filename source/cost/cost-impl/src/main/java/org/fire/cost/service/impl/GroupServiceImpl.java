package org.fire.cost.service.impl;

import org.fire.cost.dao.GroupDao;
import org.fire.cost.entity.Group;
import org.fire.cost.service.GroupService;
import org.fire.cost.vo.GroupVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 注释：“组”业务处理实现类
 * 作者：liutengfei 【刘腾飞】
 * 时间：13-12-28 下午9:41
 */
@Service
public class GroupServiceImpl implements GroupService
{
    @Resource
    private GroupDao groupDao;

    /**
     * 根据过滤条件查询“组”数据
     *
     * @param vo 过滤条件数据
     * @return
     */
    @Override
    public List<GroupVO> getGroupByFilter(GroupVO vo)
    {
        try
        {
            List<Group> groupList = groupDao.getGroupByFilter(vo);
            if (groupList != null && groupList.size() != 0)
            {
                List<GroupVO> voList = new ArrayList<GroupVO>();
                for (Group group : groupList)
                {
                    voList.add(makeGroup2VO(group));
                }
                return voList;
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return null;
    }

    /**
     * 增加“组”数据
     *
     * @param vo 组数据
     * @return
     */
    @Override
    public boolean addGroup(GroupVO vo)
    {
        return false;
    }

    /**
     * 更新“组”数据
     *
     * @param vo 组数据
     * @return
     */
    @Override
    public boolean updateGroup(GroupVO vo)
    {
        return false;
    }

    /**
     * 删除“组”数据
     *
     * @param groupId 组id
     * @return
     */
    @Override
    public boolean deleteGroup(Long groupId)
    {
        return false;
    }

    /**
     * 将vo转换成po
     *
     * @param vo    vo对象
     * @param group po对象
     * @return
     */
    private Group makeVO2Group(GroupVO vo, Group group)
    {
        return group;
    }

    /**
     * 将po对象转成vo
     *
     * @param group
     * @return
     */
    private GroupVO makeGroup2VO(Group group)
    {
        GroupVO vo = new GroupVO();
        return vo;
    }
}
