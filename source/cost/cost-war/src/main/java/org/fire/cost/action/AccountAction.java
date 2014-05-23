package org.fire.cost.action;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.service.AccountService;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.TypeVo;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Calendar;
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
     * 每月每个用户对应的金额
     */
    private Map<String, List<AccountVO>> monthListMap;

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
     * 年
     */
    private int year;

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
     * 获取每月每个用户的账单金额
     *
     * @return
     */
    @Action(value = "getAccountGroupByMonthAndUser", results = {@Result(type = "json", params = {"root", "monthListMap", "contentType", "text/html"})})
    public String getAccountGroupByMonthAndUser() {
        if (year == 0) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }
        monthListMap = accountService.getAccountGroupByMonthAndUser(year);
        return SUCCESS;
    }

    /**
     * 导出excel
     *
     * @return
     */
    @Action(value = "exportAccountToExcel", results = {@Result(name = SUCCESS, type = "stream")})
    public String exportAccountToExcel() {
        try {
            HSSFWorkbook hwb = accountService.getExcelData();
            if (hwb != null) {
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "No-cache");
                response.setDateHeader("Expires", 0);

                StringBuilder fileName = new StringBuilder();
                String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
                fileName.append("账单").append(date).append(".xls").toString();
                String gb2312FileName = new String(fileName.toString().getBytes("GB2312"), "iso8859-1");
                response.setHeader("Content-Disposition", "attachment; filename=" + gb2312FileName);
                OutputStream output = response.getOutputStream();
                hwb.write(output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public Map<String, List<AccountVO>> getMonthListMap() {
        return monthListMap;
    }

    public void setMonthListMap(Map<String, List<AccountVO>> monthListMap) {
        this.monthListMap = monthListMap;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
