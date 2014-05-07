package org.fire.cost.action;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.service.StreamService;
import org.fire.cost.vo.StreamVO;
import org.fire.cost.vo.TypeVo;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 注释：流水账action
 * 时间：2014年05月07日 下午3:06
 * 作者：刘腾飞
 */
@Namespace("/")
@Controller
public class StreamAction extends BaseAction<StreamVO> {
    @Resource
    private StreamService streamService;

    /**
     * 流水账VO
     */
    private List<StreamVO> streamVOList = new ArrayList<StreamVO>();
    /**
     * 流水账类型VO
     */
    private List<TypeVo> yearList;

    /**
     * 根据过滤条件查询流水账信息
     *
     * @return
     */
    @Action(value = "getIncomeByYear", results = {@Result(type = "json", params = {"root", "streamVOList", "contentType", "text/html"})})
    public String getIncomeByFilter() {
        try {
            streamVOList = streamService.getIncomeByYear(2014);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 得到流水账类型
     *
     * @return
     */
    @Action(value = "getYear", results = {@Result(type = "json", params = {"root", "yearList", "contentType", "text/html"})})
    public String getYear() {
        try {

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
            HSSFWorkbook hwb = streamService.getExcelData();
            if (hwb != null) {
                HttpServletResponse response = ServletActionContext.getResponse();
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "No-cache");
                response.setDateHeader("Expires", 0);

                StringBuilder fileName = new StringBuilder();
                String year = DateFormatUtils.format(new Date(), "yyyy");
                fileName.append("流水账").append(year).append(".xls").toString();
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

    public List<StreamVO> getStreamVOList() {
        return streamVOList;
    }

    public void setStreamVOList(List<StreamVO> streamVOList) {
        streamVOList = streamVOList;
    }

    public List<TypeVo> getYearList() {
        return yearList;
    }

    public void setYearList(List<TypeVo> yearList) {
        this.yearList = yearList;
    }
}
