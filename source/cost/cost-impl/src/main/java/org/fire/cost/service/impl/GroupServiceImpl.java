package org.fire.cost.service.impl;

import org.fire.cost.dao.GroupDao;
import org.fire.cost.dao.GroupUserDao;
import org.fire.cost.dao.UserDao;
import org.fire.cost.domain.Group;
import org.fire.cost.domain.GroupUser;
import org.fire.cost.domain.User;
import org.fire.cost.enums.StatusEnum;
import org.fire.cost.service.GroupService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.GroupVO;
import org.fire.cost.vo.PageData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private GroupUserDao groupUserDao;

    @Resource
    private UserDao userDao;

    /**
     * 得到当前用户所在的组（个人组）
     *
     * @return
     */
    @Override
    @Transactional(value = "transactionManager")
    public GroupVO getGroupByUser() {
        //Long userId = AuthenticationUtil.getLoginUserId();
        Long userId = 1L;
        Group group = groupDao.getGroupByUser(userId);
        GroupVO groupVO = makeGroup2VO(group);
        return groupVO;
    }

    /**
     * 根据过滤条件查询“组”数据
     *
     * @param groupVO
     * @param pageData
     * @return
     */
    @Override
    @Transactional(value = "transactionManager")
    public List<GroupVO> getGroupByFilter(GroupVO groupVO, PageData<GroupVO> pageData) {
        List<GroupVO> voList = new ArrayList<GroupVO>();
        try {
            List<Group> groupList = groupDao.getGroupByFilter(groupVO, pageData);
            if (groupList == null || groupList.size() == 0) {
                return voList;
            }
            for (Group group : groupList) {
                String userIds = null;
                String userNames = null;
                List<GroupUser> groupUserList = group.getGroupUserList();
                for (GroupUser groupUser : groupUserList) {
                    User user = groupUser.getUser();
                    String userId = user.getUserId().toString();
                    userIds = userIds == null ? userId : userIds + "," + userId;
                    String userName = user.getUserName();
                    userNames = userNames == null ? userName : userNames + "," + userName;
                }
                GroupVO vo = makeGroup2VO(group);
                vo.setUserIds(userIds);
                vo.setUserNames(userNames);
                voList.add(vo);
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
    @Transactional(value = "transactionManager")
    public boolean addGroup(GroupVO vo) {
        try {
            vo.setGroupStatus(StatusEnum.Enable.getCode());
            Group group = groupDao.save(makeVO2Group(vo, null));
            String[] userIdArr = vo.getUserIds().split(",");
            List<GroupUser> groupUserList = new ArrayList<GroupUser>();
            for (String userId : userIdArr) {
                User user = userDao.findOne(Long.valueOf(userId));
                GroupUser groupUser = new GroupUser();
                groupUser.setUser(user);
                groupUser.setGroup(group);
                groupUserList.add(groupUser);
            }
            groupUserDao.save(groupUserList);
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
    @Transactional(value = "transactionManager")
    public boolean modifyGroup(GroupVO vo) {
        try {
            Long groupId = vo.getGroupId();
            Group group = groupDao.findOne(groupId);
            List<GroupUser> groupUserList = group.getGroupUserList();
            groupUserDao.deleteInBatch(groupUserList);

            Group entity = groupDao.save(makeVO2Group(vo, group));
            String[] userIdArr = vo.getUserIds().split(",");
            for (String userId : userIdArr) {
                User user = userDao.findOne(Long.valueOf(userId));
                GroupUser groupUser = new GroupUser();
                groupUser.setUser(user);
                groupUser.setGroup(entity);
                groupUserDao.save(groupUser);
            }
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
    @Transactional(value = "transactionManager")
    public boolean deleteGroup(Long groupId) {
        try {
            Group group = groupDao.findOne(groupId);
            List<GroupUser> groupUserList = group.getGroupUserList();
            groupUserDao.deleteInBatch(groupUserList);
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
        vo.setGroupStatusName(StatusEnum.getName(group.getGroupStatus()));
        vo.setCreateUser(group.getCreateUser());
        vo.setCreateTime(DateUtil.makeDate2Str(group.getCreateTime(), true));
        vo.setModifyUser(group.getModifyUser());
        vo.setModifyTime(DateUtil.makeDate2Str(group.getModifyTime(), true));
        vo.setGroupRemark(group.getGroupRemark());
        return vo;
    }

}
