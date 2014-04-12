package org.fire.cost.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.service.UserService;
import org.fire.cost.vo.PageData;
import org.fire.cost.vo.UserVO;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Namespace("/")
@Controller
public class UserAction extends BaseAction {
    @Resource
    private UserService userService;

    //用户vo对象
    private UserVO userVO;

    //分页对象
    private PageData<UserVO> pageData;

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
            List<UserVO> userVOList = userService.getUserByFilter(userVO, pageData);
            pageData.setDataList(userVOList);
            pageData.setDataTotal(80);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 增加用户
     *
     * @param vo
     */
    @Action(value = "addUser", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public String addUser(UserVO vo) {
        try {
            userService.addUser(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 修改用户
     *
     * @param vo
     * @return
     */
    @Action(value = "modifyUser", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public String modifyUser(UserVO vo) {
        try {
            userService.modifyUser(vo);
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
    @Action(value = "deleteUser", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public String deleteUser(UserVO userVO) {
        try {
            userService.deleteUser(userVO.getUserId());
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

    public PageData<UserVO> getPageData() {
        return pageData;
    }

    public void setPageData(PageData<UserVO> pageData) {
        this.pageData = pageData;
    }
}