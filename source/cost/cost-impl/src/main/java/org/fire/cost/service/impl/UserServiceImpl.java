package org.fire.cost.service.impl;

import org.apache.log4j.Logger;
import org.fire.cost.dao.UserDao;
import org.fire.cost.entity.User;
import org.fire.cost.enums.UserStatusEnum;
import org.fire.cost.enums.YesOrNoEnum;
import org.fire.cost.service.UserService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.PageData;
import org.fire.cost.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Resource
    private UserDao userDao;

    /**
     * @param name     用户名
     * @param password 密码
     */
    public boolean userLogin(String name, String password) {
        try {
            User user = userDao.findByLoginName(name);
            if (user != null && user.getPassword().equals(password)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
        return false;
    }

    /**
     * 更新用户登录时间
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean changeUserLoginTime(Long userId) {
        try {
            userDao.changeUserLoginTime(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 根据过滤条件查询用户记录
     */
    public List<UserVO> getUserByFilter(UserVO vo, PageData<UserVO> pageData) {
        List<UserVO> userVOList = new ArrayList<UserVO>();
        try {
            List<User> userList = userDao.getUserByFilter(vo, pageData);
            for (User user : userList) {
                UserVO userVO = makeUser2VO(user);
                userVOList.add(userVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userVOList;
    }

    /**
     * 获取用户总记录数
     *
     * @return
     */
    @Override
    public int getUserDataTotal(UserVO vo) {
        int total = userDao.getUserDataTotal(vo);
        return total;
    }

    /**
     * 增加用户
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean addUser(UserVO vo) {
        try {
            vo.setUserStatus(UserStatusEnum.Enable.getCode());
            vo.setIsAdmin(YesOrNoEnum.No.getCode());
            User user = makeVO2User(vo, null);
            userDao.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 修改用户信息
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean modifyUser(UserVO vo) {
        try {
            Long userId = vo.getUserId();
            User user = userDao.findOne(userId);
            makeVO2User(vo, user);
            userDao.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 删除用户
     *
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean deleteUser(Long userId) {
        try {
            userDao.delete(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackException();
        }
    }

    /**
     * 得到当前登录用户名字
     *
     * @return
     */
    public String getLoginUserName() {
        Long userId = AuthenticationUtil.getLoginUserId();
        if (userId != null && userId != 0) {
            User user = userDao.findByUserId(userId);
            if (user != null) {
                return user.getUserName();
            }
        }
        return null;
    }

    /**
     * 根据用户id查询用户
     *
     * @param userIds 用户id，格式：id1,id2,..idn
     * @return
     */
    public List<UserVO> getUsersByUserIds(String userIds) {
        if (userIds == null || userIds.trim().length() == 0) {
            throw new RuntimeException("数据格式不正确");
        }
        try {
            List<User> userList = userDao.getUsersByUserIds(userIds);
            List<UserVO> userVOList = new ArrayList<UserVO>();
            for (User user : userList) {
                userVOList.add(makeUser2VO(user));
            }
            return userVOList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据用户id查询用户名称
     *
     * @param userIds 用户id
     * @return
     */
    public String getUserNamesByUserIds(String userIds) {
        String userNames = null;
        List<UserVO> userVOList = getUsersByUserIds(userIds);
        if (userVOList != null || userVOList.size() != 0) {
            for (UserVO userVO : userVOList) {
                String userName = userVO.getUserName();
                userNames = userNames == null ? userName : userNames + "," + userName;
            }
        }
        return userNames;
    }

    /**
     * 将UserVO转换成User对象
     *
     * @param vo
     * @return
     */
    private User makeVO2User(UserVO vo, User user) {
        if (user == null) {
            user = new User();
            user.setLoginTime(new Date());
            user.setCreateUser(getLoginUserName());
            user.setCreateTime(new Date());
        } else {
            user.setUserId(vo.getUserId());
        }
        user.setUserName(vo.getUserName());
        user.setLoginName(vo.getLoginName());
        user.setUserAge(vo.getUserAge());
        user.setUserAddress(vo.getUserAddress());
        user.setUserEmail(vo.getUserEmail());
        user.setUserImage(vo.getUserImage());
        user.setUserStatus(vo.getUserStatus());
        user.setIsAdmin(vo.getIsAdmin());
        user.setUserRemark(vo.getUserRemark());
        user.setModifyUser(vo.getModifyUser());
        user.setModifyTime(new Date());
        return user;
    }

    /**
     * 将实体对象user装换成vo对象
     *
     * @param user
     * @return
     */
    private UserVO makeUser2VO(User user) {
        UserVO vo = new UserVO();
        vo.setUserId(user.getUserId());
        vo.setUserName(user.getUserName());
        vo.setPassword(user.getPassword());
        vo.setLoginName(user.getLoginName());
        vo.setUserAge(user.getUserAge());
        vo.setUserAddress(user.getUserAddress());
        vo.setUserEmail(user.getUserEmail());
        vo.setUserImage(user.getUserImage());
        vo.setLoginTime(DateUtil.makeDate2Str((user.getLoginTime())));
        vo.setUserStatus(user.getUserStatus());
        vo.setUserStatusName(UserStatusEnum.getName(user.getUserStatus()));
        vo.setIsAdmin(user.getIsAdmin());
        vo.setIsAdminName(YesOrNoEnum.getName(user.getIsAdmin()));
        vo.setCreateUser(user.getCreateUser());
        vo.setCreateTime(DateUtil.makeDate2Str(user.getCreateTime()));
        vo.setModifyUser(user.getModifyUser());
        vo.setModifyTime(DateUtil.makeDate2Str(user.getModifyTime()));
        vo.setUserRemark(user.getUserRemark());
        return vo;
    }
}
