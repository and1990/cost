package org.fire.cost.service;

import org.fire.cost.vo.UserVO;

import java.util.List;

public interface UserService
{

    /**
     * 用户登录
     *
     * @param name     用户名
     * @param password 密码
     */
    boolean userLogin(String name, String password);

    /**
     * 更新用户登录时间
     *
     * @param userId
     * @return
     */
    boolean changeUserLoginTime(Long userId);

    /**
     * 得到当前登录用户名称
     *
     * @return
     */
    public String getLoginUserName();

    /**
     * 根据过滤条件查询用户
     *
     * @param vo
     */
    List<UserVO> getUserByFilter(UserVO vo);

    /**
     * 增加用户
     *
     * @param vo
     * @return
     */
    boolean addUser(UserVO vo);

    /**
     * 修改用户信息
     *
     * @param vo
     * @return
     */
    boolean modifyUser(UserVO vo);

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    boolean deleteUser(Long userId);

    /**
     * 根据用户id查询用户
     *
     * @param userIds 用户id，格式：id1,id2,..idn
     * @return
     */
    List<UserVO> getUsersByUserIds(String userIds);

    /**
     * 根据用户id查询用户名称
     *
     * @param userIds 用户id
     * @return
     */
    String getUserNamesByUserIds(String userIds);
}
