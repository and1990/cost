package org.fire.cost.service.impl;

import org.fire.cost.dao.GroupDao;
import org.fire.cost.entity.Group;
import org.fire.cost.service.GroupService;
import org.fire.cost.util.DateUtil;
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
public class GroupServiceImpl implements GroupService {
    @Resource
    private GroupDao groupDao;

    /**
     * 根据过滤条件查询“组”数据
     *
     * @param vo 过滤条件数据
     * @return
     */
    @Override
    public List<GroupVO> getGroupByFilter(GroupVO vo) {
        try {
            List<Group> groupList = groupDao.getGroupByFilter(vo);
            if (groupList != null && groupList.size() != 0) {
                List<GroupVO> voList = new ArrayList<GroupVO>();
                for (Group group : groupList) {
                    voList.add(makeGroup2VO(group));
                }
                return voList;
            }
        } catch (Exception ex) {
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
    public boolean addGroup(GroupVO vo) {
        try {
            groupDao.save(makeVO2Group(vo, null));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * 更新“组”数据
     *
     * @param vo 组数据
     * @return
     */
    @Override
    public boolean updateGroup(GroupVO vo) {
        try {
            Long groupId = vo.getGroupId();
            groupDao.save(makeVO2Group(vo, groupDao.findOne(groupId)));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * 删除“组”数据
     *
     * @param groupId 组id
     * @return
     */
    @Override
    public boolean deleteGroup(Long groupId) {
        try {
            groupDao.delete(groupId);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * 将vo转换成po
     *
     * @param vo    vo对象
     * @param group po对象
     * @return
     */
    private Group makeVO2Group(GroupVO vo, Group group) throws Exception {
        if (group == null) {
            group = new Group();
        }
        group.setGroupId(vo.getGroupId());
        group.setGroupName(vo.getGroupName());
        group.setUserIds(null);//TODO
        group.setGroupStatus(vo.getGroupStatus());
        group.setCreateUser(vo.getCreateUser());
        group.setCreateTime(DateUtil.makeStr2Date(vo.getCreateTime()));
        group.setModifyUser(vo.getModifyUser());
        group.setModifyTime(DateUtil.makeStr2Date(vo.getModifyTime()));
        group.setGroupRemark(vo.getGroupRemark());
        return group;
    }

    /**
     * 将po对象转成vo
     *
     * @param group po对象
     * @return
     */
    private GroupVO makeGroup2VO(Group group) {
        GroupVO vo = new GroupVO();
        vo.setGroupId(group.getGroupId());
        vo.setGroupName(group.getGroupName());
        vo.setUserNames(null);//TODO
        vo.setGroupStatus(group.getGroupStatus());
        vo.setCreateUser(group.getCreateUser());
        vo.setCreateTime(DateUtil.makeDate2Str(group.getCreateTime()));
        vo.setModifyUser(group.getModifyUser());
        vo.setModifyTime(DateUtil.makeDate2Str(group.getModifyTime()));
        vo.setGroupRemark(group.getGroupRemark());
        return vo;
    }
}
