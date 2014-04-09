package org.fire.cost.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.enums.HttpStatusEnum;
import org.fire.cost.enums.ResultEnum;
import org.fire.cost.service.UserService;
import org.fire.cost.util.MessageUtil;
import org.fire.cost.vo.Message;
import org.fire.cost.vo.UserVO;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Namespace("/")
@Controller
public class UserAction {
    @Resource
    private UserService userService;

    /**
     * 根据过滤条件查询用户
     *
     * @param vo
     * @return
     */
    @Action(value = "getUserByFilter", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public Message getUserByFilter(UserVO vo) {
        Message message = new Message();
        if (vo == null) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
            return message;
        }
        try {
            List<UserVO> voList = userService.getUserByFilter(vo);
            MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, voList);
        } catch (Exception e) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, null, null);
        }
        return message;
    }

    /**
     * 增加用户
     *
     * @param vo
     */
    @Action(value = "addUser", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public Message addUser(UserVO vo) {
        Message message = new Message();
        if (vo == null) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
            return message;
        }
        try {
            userService.addUser(vo);
            MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
        }
        return message;
    }

    /**
     * 修改用户
     *
     * @param vo
     * @return
     */
    @Action(value = "modifyUser", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public Message modifyUser(UserVO vo) {
        Message message = new Message();
        if (vo == null) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
            return message;
        }
        try {
            userService.modifyUser(vo);
            MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
        }
        return message;
    }

    /**
     * 删除用户信息
     *
     * @param
     * @return
     */
    @Action(value = "deleteUser", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public Message deleteUser(UserVO userVO) {
        Message message = new Message();
        if (userVO == null || userVO.getUserId() == null) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
            return message;
        }
        try {
            if (userService.deleteUser(userVO.getUserId()))
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
            else
                MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
        }
        return message;
    }

}