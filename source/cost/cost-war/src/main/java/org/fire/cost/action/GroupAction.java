package org.fire.cost.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.enums.HttpStatusEnum;
import org.fire.cost.enums.ResultEnum;
import org.fire.cost.service.GroupService;
import org.fire.cost.util.MessageUtil;
import org.fire.cost.vo.GroupVO;
import org.fire.cost.vo.Message;
import org.fire.cost.vo.PageData;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * “组”Action
 *
 * @author liutengfei
 */
@Namespace("/")
@Controller
public class GroupAction extends BaseAction {
    //组service服务类
    @Resource
    private GroupService groupService;

    // 分页对象
    private PageData<GroupVO> pageData;

    //组VO信息
    private GroupVO groupVO;

    //当前页数
    private int page;
    //每页显示条数
    private int rows;

    /**
     * 根据过滤条件查询“组”数据
     *
     * @return
     */
    @Action(value = "getGroupByFilter", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String getGroupByFilter() {
        try {
            if (pageData == null) {
                pageData = new PageData<GroupVO>();
            }
            pageData.setPage(page);
            pageData.setPageSize(rows);
            List<GroupVO> voList = groupService.getGroupByFilter(groupVO, pageData);
            int total = groupService.getGroupTotal();
            pageData.setRows(voList);
            pageData.setTotal(total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 增加“组”数据
     *
     * @param vo 客户端传递的数据
     * @return
     */
    @Action(value = "addGroup", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public Message addGroup(GroupVO vo) {
        Message message = new Message();
        if (vo == null) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
            return message;
        }
        try {
            if (groupService.addGroup(vo)) {
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, "增加成功", null);
            } else {
                MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Success, "增加失败", null);
            }
        } catch (Exception e) {
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
    @Action(value = "updateGroup", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public Message updateGroup(GroupVO vo) {
        Message message = new Message();
        if (vo == null) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
            return message;
        }
        try {
            if (groupService.updateGroup(vo)) {
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, "更新成功", null);
            } else {
                MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Success, "更新失败", null);
            }
        } catch (Exception e) {
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
    @Action(value = "deleteGroup", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public Message deleteGroup(GroupVO vo) {
        Message message = new Message();
        Long groupId = vo.getGroupId();
        if (groupId == null || groupId == 0) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不正确", null);
            return message;
        }
        try {
            if (groupService.deleteGroup(groupId)) {
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, "删除成功", null);
            } else {
                MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Success, "删除失败", null);
            }
        } catch (Exception e) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
        }
        return message;
    }

    public PageData<GroupVO> getPageData() {
        return pageData;
    }

    public void setPageData(PageData<GroupVO> pageData) {
        this.pageData = pageData;
    }

    public GroupVO getGroupVO() {
        return groupVO;
    }

    public void setGroupVO(GroupVO groupVO) {
        this.groupVO = groupVO;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
