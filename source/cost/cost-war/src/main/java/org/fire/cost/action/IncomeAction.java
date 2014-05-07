package org.fire.cost.action;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.service.IncomeService;
import org.fire.cost.vo.IncomeVO;
import org.fire.cost.vo.TypeVo;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * 注释：收入action
 * 时间：2014年05月06日 下午3:06
 * 作者：刘腾飞
 */
@Namespace("/")
@Controller
public class IncomeAction extends BaseAction<IncomeVO> {
    @Resource
    private IncomeService incomeService;

    /**
     * 收入信息VO
     */
    private IncomeVO incomeVO = new IncomeVO();
    /**
     * 收入VO
     */
    private List<IncomeVO> IncomeVOList;
    /**
     * 收入类型VO
     */
    private List<TypeVo> incomeTypeList;

    /**
     * 根据过滤条件查询收入信息
     *
     * @return
     */
    @Action(value = "getIncomeByYear", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String getIncomeByFilter() {
        try {
            pageData.setPage(page);
            pageData.setPageSize(rows);
            List<IncomeVO> voList = incomeService.getIncomeByFilter(incomeVO, pageData);
            int total = incomeService.getIncomeTotal(incomeVO);
            pageData.setRows(voList);
            pageData.setTotal(total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 增加收入
     *
     * @return
     */
    @Action(value = "addIncome", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String addIncome() {
        try {
            incomeService.addIncome(incomeVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 修改收入信息
     *
     * @return
     */
    @Action(value = "modifyIncome", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String modifyIncome() {
        try {
            incomeService.modifyIncome(incomeVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 删除收入信息
     *
     * @return
     */
    @Action(value = "deleteIncome", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String deleteIncome() {
        try {
            incomeService.deleteIncome(incomeVO.getIncomeId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 得到收入类型
     *
     * @return
     */
    @Action(value = "getIncomeType", results = {@Result(type = "json", params = {"root", "incomeTypeList", "contentType", "text/html"})})
    public String getIncomeType() {
        try {
            incomeTypeList = incomeService.getIncomeType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 导出excel
     *
     * @return
     */
    @Action(value = "exportIncomeToExcel", results = {@Result(name = SUCCESS, type = "stream")})
    public String exportIncomeToExcel() {
        try {
            HSSFWorkbook hwb = incomeService.getExcelData();
            if (hwb != null) {
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "No-cache");
                response.setDateHeader("Expires", 0);

                StringBuilder fileName = new StringBuilder();
                String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
                fileName.append("收入").append(date).append(".xls").toString();
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

    public IncomeVO getIncomeVO() {
        return incomeVO;
    }

    public void setIncomeVO(IncomeVO incomeVO) {
        this.incomeVO = incomeVO;
    }

    public List<IncomeVO> getIncomeVOList() {
        return IncomeVOList;
    }

    public void setIncomeVOList(List<IncomeVO> incomeVOList) {
        IncomeVOList = incomeVOList;
    }

    public List<TypeVo> getIncomeTypeList() {
        return incomeTypeList;
    }

    public void setIncomeTypeList(List<TypeVo> incomeTypeList) {
        this.incomeTypeList = incomeTypeList;
    }
}
