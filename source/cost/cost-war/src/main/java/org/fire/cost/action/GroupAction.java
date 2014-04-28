package org.fire.cost.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.service.GroupService;
import org.fire.cost.vo.GroupVO;
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
public class GroupAction extends BaseAction<GroupVO> {
    //组service服务类
    @Resource
    private GroupService groupService;

    private GroupVO groupVO;

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
            List<GroupVO> voList = groupService.getGroupByFilter(null, pageData);
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
     * @return
     */
    @Action(value = "addGroup", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public String addGroup() {
        try {
            groupService.addGroup(groupVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 更新“组”数据
     *
     * @return
     */
    @Action(value = "modifyGroup", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public String modifyGroup() {
        try {
            groupService.modifyGroup(groupVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 根据组id删除“组数据”
     *
     * @return
     */
    @Action(value = "deleteGroup", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public String deleteGroup() {
        try {
            groupService.deleteGroup(groupVO.getGroupId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public GroupVO getGroupVO() {
        return groupVO;
    }

    public void setGroupVO(GroupVO groupVO) {
        this.groupVO = groupVO;
    }

}
