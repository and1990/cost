package org.fire.cost.service.impl;

import org.fire.cost.dao.GroupDao;
import org.fire.cost.domain.Group;
import org.fire.cost.enums.UserStatusEnum;
import org.fire.cost.service.GroupService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.GroupVO;
import org.fire.cost.vo.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

    //private
    /**
     * 根据过滤条件查询“组”数据
     *
     * @param vo       过滤条件数据
     * @param pageData
     * @return
     */
    @Override
    public List<GroupVO> getGroupByFilter(GroupVO vo, PageData<GroupVO> pageData) {
        List<GroupVO> voList = new ArrayList<GroupVO>();
        try {
            List<Group> groupList = groupDao.getGroupByFilter(vo, pageData);
            if (groupList != null && groupList.size() != 0) {
                for (Group group : groupList) {
                    voList.add(makeGroup2VO(group));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return voList;
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
            vo.setGroupStatus(UserStatusEnum.Enable.getCode());
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
    public boolean modifyGroup(GroupVO vo) {
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
     * 得到组数据总记录数
     *
     * @return
     */
    @Override
    public int getGroupTotal() {
        try {
            int total = groupDao.getGroupTotal();
            return total;
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
            group.setCreateUser(AuthenticationUtil.getUserName());
            group.setCreateTime(new Date());
        } else {
            group.setGroupId(vo.getGroupId());
        }
        group.setGroupName(vo.getGroupName());
        group.setGroupStatus(vo.getGroupStatus());
        group.setModifyUser(AuthenticationUtil.getUserName());
        group.setModifyTime(new Date());
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
        vo.setGroupStatus(group.getGroupStatus());
        vo.setGroupStatusName(UserStatusEnum.getName(group.getGroupStatus()));
        vo.setCreateUser(group.getCreateUser());
        vo.setCreateTime(DateUtil.makeDate2Str(group.getCreateTime(), true));
        vo.setModifyUser(group.getModifyUser());
        vo.setModifyTime(DateUtil.makeDate2Str(group.getModifyTime(), true));
        vo.setGroupRemark(group.getGroupRemark());
        return vo;
    }

}
