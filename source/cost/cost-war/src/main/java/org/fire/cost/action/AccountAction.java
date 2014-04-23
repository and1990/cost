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
import org.fire.cost.vo.TypeVo;
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

    /**
     * 账单信息VO
     */
    private AccountVO accountVO = new AccountVO();
    /**
     * 账单VO
     */
    private List<AccountVO> accountVOList;

    /**
     * 枚举类型VO
     */
    private List<TypeVo> typeList;


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
            int total = accountService.getAccountTotal(accountVO);
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
     * @return
     */
    @Action(value = "addAccount", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String addAccount() {
        try {
            accountService.addAccount(accountVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 修改账单信息
     *
     * @return
     */
    @Action(value = "modifyAccount", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String modifyAccount() {
        try {
            accountService.modifyAccount(accountVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 删除账单信息
     *
     * @return
     */
    @Action(value = "deleteAccount", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String deleteAccount() {
        try {
            accountService.deleteAccount(accountVO.getAccountId());
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

    /**
     * 审批账单
     *
     * @return
     */
    @Action(value = "approveAccount", results = {@Result(type = "json", params = {"root", "typeList", "contentType", "text/html"})})
    public String approveAccount() {
        try {
            accountService.approveAccount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 结算账单
     *
     * @return
     */
    @Action(value = "clearAccount", results = {@Result(type = "json", params = {"root", "typeList", "contentType", "text/html"})})
    public String clearAccount() {
        try {
            accountService.clearAccount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 得到账单类型
     *
     * @return
     */
    @Action(value = "getAccountType", results = {@Result(type = "json", params = {"root", "typeList", "contentType", "text/html"})})
    public String getAccountType() {
        try {
            typeList = accountService.getAccountType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }


    /**
     * 查找用户对应的账单数据
     *
     * @return
     */
    @Action(value = "getAccountGroupByUser", results = {@Result(type = "json", params = {"root", "accountVOList", "contentType", "text/html"})})
    public String getAccountGroupByUser() {
        try {
            accountVOList = accountService.getAccountGroupByUser(accountVO.getAccountStartTime(), accountVO.getAccountEndTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }


    public AccountVO getAccountVO() {
        return accountVO;
    }

    public void setAccountVO(AccountVO accountVO) {
        this.accountVO = accountVO;
    }

    public List<AccountVO> getAccountVOList() {
        return accountVOList;
    }

    public void setAccountVOList(List<AccountVO> accountVOList) {
        this.accountVOList = accountVOList;
    }

    public List<TypeVo> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<TypeVo> typeList) {
        this.typeList = typeList;
    }
}
