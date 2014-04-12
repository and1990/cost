package org.fire.cost.dao.custom;

import org.fire.cost.entity.User;
import org.fire.cost.vo.PageData;
import org.fire.cost.vo.UserVO;

import java.util.List;

/**
 * 用户dao扩展类
 *
 * @author liutengfei
 */
public interface UserDaoCustom {

    /**
     * 根据过滤条件查询用户
     *
     * @param vo
     * @return
     */
    List<User> getUserByFilter(UserVO vo, PageData<UserVO> pageData);

    /**
     * 根据用户id查询用户
     *
     * @param userIds 用户id，格式：id1,id2,..idn
     * @return
     */
    List<User> getUsersByUserIds(String userIds);

    /**
     * 获取用户总记录数
     *
     * @return
     */
    int getUserDataTotal();
}
