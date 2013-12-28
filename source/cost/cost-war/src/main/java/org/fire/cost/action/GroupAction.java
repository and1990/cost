package org.fire.cost.action;

import org.fire.cost.vo.GroupVO;
import org.fire.cost.vo.Message;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Path;

/**
 * “组”Action
 *
 * @author liutengfei
 */
@Controller
@Path("group")
public class GroupAction
{
    /**
     * 根据过滤条件查询“组”数据
     *
     * @param vo 客户端传递的过滤数据
     * @return
     */
    public Message getGroupByFilter(GroupVO vo)
    {
        return null;
        //TODO
    }

    /**
     * 增加“组”数据
     *
     * @param vo 客户端传递的数据
     * @return
     */
    public Message addGroup(GroupVO vo)
    {
        return null;
        //TODO
    }

    /**
     * 更新“组”数据
     *
     * @param vo 客户端传递的数据
     * @return
     */
    public Message updateGroup(GroupVO vo)
    {
        return null;
        //TODO
    }

    /**
     * 根据组id删除“组数据”
     *
     * @param groupId 组id
     * @return
     */
    public Message deleteGroup(Long groupId)
    {
        return null;
        //TODO
    }

}
