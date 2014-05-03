package org.fire.cost.action;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.enums.HttpStatusEnum;
import org.fire.cost.enums.ResultEnum;
import org.fire.cost.service.AccountService;
import org.fire.cost.util.DateUtil;
import org.fire.cost.util.MessageUtil;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.Message;
import org.fire.cost.vo.TypeVo;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * key：账单类型
     * value：用户账单金额
     */
    private Map<String, List<AccountVO>> accountVoListMap;

    /**
     * 账单类型VO
     */
    private List<TypeVo> accountStatusList;

    /**
     * 账单状态VO
     */
    private List<TypeVo> accountTypeList;
    /**
     * 结算方式VO
     */
    private List<TypeVo> clearTypeList;

    /**
     * 账单ID
     */
    private String accountIds;


    /**
     * 根据过滤条件查询账单信息
     *
     * @return
     */
    @Action(value = "getAccountByFilter", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String getAccountByFilter() {
        try {
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
    @Action(value = "approveAccount", results = {@Result(type = "json", params = {"root", "accountTypeList", "contentType", "text/html"})})
    public String approveAccount() {
        try {
            accountService.approveAccount(accountIds, accountVO.getAccountStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 查询本周
     *
     * @return
     */
    @Action(value = "getAccountByThisWeek", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String getAccountByThisWeek() {
        try {
            Date firstDay = DateUtil.getFirstDayOfWeek();
            Date endDay = DateUtil.getEndDayOfWeek();
            accountVO.setAccountStartTime(DateUtil.makeDate2Str(firstDay, false));
            accountVO.setAccountEndTime(DateUtil.makeDate2Str(endDay, false));
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
     * 查询本月
     *
     * @return
     */
    @Action(value = "getAccountByThisMonth", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String getAccountByThisMonth() {
        try {
            Date firstDay = DateUtil.getFirstDayOfMonth();
            Date endDay = DateUtil.getEndDayOfMonth();
            accountVO.setAccountStartTime(DateUtil.makeDate2Str(firstDay, false));
            accountVO.setAccountEndTime(DateUtil.makeDate2Str(endDay, false));
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
     * 结算账单
     *
     * @return
     */
    @Action(value = "clearAccount", results = {@Result(type = "json", params = {"root", "accountTypeList", "contentType", "text/html"})})
    public String clearAccount() {
        try {
            accountService.clearAccount(accountIds);
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
    @Action(value = "getAccountType", results = {@Result(type = "json", params = {"root", "accountTypeList", "contentType", "text/html"})})
    public String getAccountType() {
        try {
            accountTypeList = accountService.getAccountType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 得到账单状态
     *
     * @return
     */
    @Action(value = "getAccountStatus", results = {@Result(type = "json", params = {"root", "accountStatusList", "contentType", "text/html"})})
    public String getAccountStatus() {
        try {
            accountStatusList = accountService.getAccountStatus();
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
    @Action(value = "getClearType", results = {@Result(type = "json", params = {"root", "clearTypeList", "contentType", "text/html"})})
    public String getClearType() {
        try {
            clearTypeList = accountService.getClearType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 查找账单类型对应的账单数据
     *
     * @return
     */
    @Action(value = "getAccountGroupByAccountType", results = {@Result(type = "json", params = {"root", "accountVOList", "contentType", "text/html"})})
    public String getAccountGroupByAccountType() {
        try {
            String startTime = accountVO.getAccountStartTime();
            String endTime = accountVO.getAccountEndTime();
            accountVOList = accountService.getAccountGroupByAccountType(startTime, endTime);
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
            String startTime = accountVO.getAccountStartTime();
            String endTime = accountVO.getAccountEndTime();
            accountVOList = accountService.getAccountGroupByUser(startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 获取用户每种账单类型账单金额
     *
     * @return
     */
    @Action(value = "getAccountGroupByTypeAndUser", results = {@Result(type = "json", params = {"root", "accountVoListMap", "contentType", "text/html"})})
    public String getAccountGroupByTypeAndUser() {
        String startTime = accountVO.getAccountStartTime();
        String endTime = accountVO.getAccountEndTime();
        accountVoListMap = accountService.getAccountGroupByTypeAndUser(startTime, endTime);
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

    public Map<String, List<AccountVO>> getAccountVoListMap() {
        return accountVoListMap;
    }

    public void setAccountVoListMap(Map<String, List<AccountVO>> accountVoListMap) {
        this.accountVoListMap = accountVoListMap;
    }

    public List<TypeVo> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<TypeVo> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public List<TypeVo> getClearTypeList() {
        return clearTypeList;
    }

    public List<TypeVo> getAccountStatusList() {
        return accountStatusList;
    }

    public void setAccountStatusList(List<TypeVo> accountStatusList) {
        this.accountStatusList = accountStatusList;
    }

    public void setClearTypeList(List<TypeVo> clearTypeList) {
        this.clearTypeList = clearTypeList;
    }

    public String getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(String accountIds) {
        this.accountIds = accountIds;
    }
}
