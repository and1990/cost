package org.fire.cost.action;

import org.fire.cost.enums.HttpStatusEnum;
import org.fire.cost.enums.ResultEnum;
import org.fire.cost.service.GroupService;
import org.fire.cost.util.MessageUtil;
import org.fire.cost.vo.GroupVO;
import org.fire.cost.vo.Message;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * “组”Action
 *
 * @author liutengfei
 */
@Controller
@Path("group")
public class GroupAction
{
    @Resource
    private GroupService groupService;

    /**
     * 根据过滤条件查询“组”数据
     *
     * @param vo 客户端传递的过滤数据
     * @return
     */
    @POST
    @Path("getGroupByFilter")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Message getGroupByFilter(GroupVO vo)
    {
        Message message = new Message();
        if (vo == null)
        {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
            return message;
        }
        try
        {
            List<GroupVO> voList = groupService.getGroupByFilter(vo);
            MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, "查询成功", voList);
        } catch (Exception e)
        {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
        }
        return message;
    }

    /**
     * 增加“组”数据
     *
     * @param vo 客户端传递的数据
     * @return
     */
    @POST
    @Path("addGroup")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Message addGroup(GroupVO vo)
    {
        Message message = new Message();
        if (vo == null)
        {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
            return message;
        }
        try
        {
            if (groupService.addGroup(vo))
            {
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, "增加成功", null);
            } else
            {
                MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Success, "增加失败", null);
            }
        } catch (Exception e)
        {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
        }
        return message;
    }

    /**
     * 更新“组”数据
     *
     * @param vo 客户端传递的数据
     * @return
     */
    @POST
    @Path("updateGroup")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Message updateGroup(GroupVO vo)
    {
        Message message = new Message();
        if (vo == null)
        {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
            return message;
        }
        try
        {
            if (groupService.updateGroup(vo))
            {
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, "更新成功", null);
            } else
            {
                MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Success, "更新失败", null);
            }
        } catch (Exception e)
        {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
        }
        return message;
    }

    /**
     * 根据组id删除“组数据”
     *
     * @param vo 组VO
     * @return
     */
    @POST
    @Path("deleteGroup")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Message deleteGroup(GroupVO vo)
    {
        Message message = new Message();
        Long groupId = vo.getGroupId();
        if (groupId == null || groupId == 0)
        {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不正确", null);
            return message;
        }
        try
        {
            if (groupService.deleteGroup(groupId))
            {
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, "删除成功", null);
            } else
            {
                MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Success, "删除失败", null);
            }
        } catch (Exception e)
        {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
        }
        return message;
    }

}
