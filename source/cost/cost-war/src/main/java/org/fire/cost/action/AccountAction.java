package org.fire.cost.action;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.enums.HttpStatusEnum;
import org.fire.cost.enums.ResultEnum;
import org.fire.cost.service.AccountService;
import org.fire.cost.util.MessageUtil;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.Message;
import org.fire.cost.vo.PageData;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 账单信息表
 *
 * @author liutengfei
 */
@Namespace("/")
@Controller
public class AccountAction extends BaseAction {

    @Resource
    private AccountService accountService;

    //分页对象
    private PageData<AccountVO> pageData;

    //账单信息VO
    private AccountVO accountVO;

    //当前页数
    private int page;
    //每页显示条数
    private int rows;

    /**
     * 根据过滤条件查询账单信息
     *
     * @return
     */
    @Action(value = "getAccountByFilter", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String getAccountByFilter() {
        try {
            if (pageData == null) {
                pageData = new PageData<AccountVO>();
            }
            pageData.setPage(page);
            pageData.setPageSize(rows);
            List<AccountVO> voList = accountService.getAccountByFilter(accountVO, pageData);
            int total = accountService.getAccountTotal();
            pageData.setRows(voList);
            pageData.setTotal(total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 增加账单
     *
     * @param vo
     * @return
     */
    @Action(value = "addAccount", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public Message addAccount(AccountVO vo) {
        Message message = new Message();
        if (vo == null) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
            return message;
        }
        try {
            if (accountService.addAccount(vo)) {
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
        }
        return message;
    }

    /**
     * 修改账单信息
     *
     * @param vo
     * @return
     */
    @Action(value = "modifyAccount", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public Message modifyAccount(AccountVO vo) {
        Message message = new Message();
        if (vo == null) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
            return message;
        }
        try {
            if (accountService.modifyAccount(vo)) {
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
        }
        return message;
    }

    /**
     * 删除账单信息
     *
     * @param vo
     * @return
     */
    @Action(value = "deleteAccount", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public Message deleteAccount(AccountVO vo) {
        Message message = new Message();
        if (vo == null) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Warn, "请求数据不能为空", null);
            return message;
        }
        try {
            if (accountService.deleteAccount(vo.getAccountId())) {
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, e.getMessage(), null);
        }
        return message;
    }

    /**
     * 文件上传
     *
     * @return
     */
    @Action(value = "fileUpload", results = {@Result(type = "json", params = {"root", "returnData", "contentType", "text/html"})})
    public Message fileUpload() {
        Message message = new Message();
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            if (accountService.fileUpload(request, response)) {
                MessageUtil.setMessage(message, ResultEnum.Success, HttpStatusEnum.Success, "上传成功", null);
            } else {
                MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.Success, "上传失败", null);
            }
        } catch (Exception e) {
            MessageUtil.setMessage(message, ResultEnum.Fail, HttpStatusEnum.ServerError, "上传失败，服务器内部错误", null);
            e.printStackTrace();
        }
        return message;
    }

    public PageData<AccountVO> getPageData() {
        return pageData;
    }

    public void setPageData(PageData<AccountVO> pageData) {
        this.pageData = pageData;
    }

    public AccountVO getAccountVO() {
        return accountVO;
    }

    public void setAccountVO(AccountVO accountVO) {
        this.accountVO = accountVO;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
