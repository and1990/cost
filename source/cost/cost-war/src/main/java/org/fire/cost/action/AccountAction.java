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
public class AccountAction extends BaseAction<AccountVO> {

    @Resource
    private AccountService accountService;

    //账单信息VO
    private AccountVO accountVO;


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
    @Action(value = "addAccount", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String addAccount(AccountVO vo) {
        try {
            accountService.addAccount(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 修改账单信息
     *
     * @param vo
     * @return
     */
    @Action(value = "modifyAccount", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String modifyAccount(AccountVO vo) {
        try {
            accountService.modifyAccount(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 删除账单信息
     *
     * @param vo
     * @return
     */
    @Action(value = "deleteAccount", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String deleteAccount(AccountVO vo) {
        try {
            accountService.deleteAccount(vo.getAccountId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 文件上传
     *
     * @return
     */
    @Action(value = "fileUpload", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
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

    public AccountVO getAccountVO() {
        return accountVO;
    }

    public void setAccountVO(AccountVO accountVO) {
        this.accountVO = accountVO;
    }
}
