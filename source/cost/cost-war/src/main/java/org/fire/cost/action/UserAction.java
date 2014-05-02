package org.fire.cost.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.service.UserService;
import org.fire.cost.vo.Message;
import org.fire.cost.vo.PageData;
import org.fire.cost.vo.UserVO;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Namespace("/")
@Controller
public class UserAction extends BaseAction<UserVO> {
    @Resource
    private UserService userService;

    //用户vo对象
    private UserVO userVO = new UserVO();

    //用户ID
    private String userIds;

    private Message message = new Message();

    /**
     * 根据过滤条件查询用户
     *
     * @return
     */
    @Action(value = "getUserByFilter", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String getUserByFilter() {
        try {
            if (pageData == null) {
                pageData = new PageData<UserVO>();
            }
            if (userVO == null) {
                userVO = new UserVO();
            }
            List<UserVO> userVOList = userService.getUserByFilter(userVO, pageData);
            pageData.setRows(userVOList);
            pageData.setTotal(userService.getUserDataTotal(userVO));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 增加用户
     */
    @Action(value = "addUser", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String addUser() {
        try {
            if (userVO != null) {
                userService.addUser(userVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 修改用户
     *
     * @return
     */
    @Action(value = "modifyUser", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String modifyUser() {
        try {
            userService.modifyUser(userVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 删除用户信息
     *
     * @param
     * @return
     */
    @Action(value = "deleteUser", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String deleteUser() {
        try {
            if (userVO != null && userVO.getUserId() != null) {
                userService.deleteUser(userVO.getUserId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 禁用、启用用户
     *
     * @return
     */
    @Action(value = "modifyUserStatus", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String modifyUserStatus() {
        try {
            if (userIds == null || userIds.trim().length() == 0) {
                return SUCCESS;
            }
            userService.modifyUserStatus(userIds, userVO.getUserStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 验证密码是否正确
     *
     * @return
     */
    @Action(value = "validatePassword", results = {@Result(type = "json", params = {"root", "message", "contentType", "text/html"})})
    public String validatePassword() {
        try {
            boolean isValidate = userService.validatePassword(userVO.getPassword());
            message.setData(isValidate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 修改密码
     *
     * @return
     */
    @Action(value = "modifyPassword", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String modifyPassword() {
        try {
            userService.modifyPassword(userVO.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}