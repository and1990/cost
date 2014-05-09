package org.fire.cost.action;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.service.StreamService;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.StreamDetailVO;
import org.fire.cost.vo.StreamVO;
import org.fire.cost.vo.TypeVo;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

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
     * 流水账明细VO
     */
    private List<StreamDetailVO> streamDetailVOList = new ArrayList<StreamDetailVO>();
    /**
     * 月份对应的流水账信息，key：收入、支出、剩余，value：对应的金额
     */
    private Map<String, List<String>> moneyListMap = new HashMap<String, List<String>>();
    /**
     * 流水账类型VO
     */
    private List<TypeVo> yearList;

    /**
     * 查询某年数据
     */
    private int year;
    /**
     * 查询某月流水明细
     */
    private int month;

    /**
     * 根据过滤条件查询流水账信息
     *
     * @return
     */
    @Action(value = "getStreamByYear", results = {@Result(type = "json", params = {"root", "streamVOList", "contentType", "text/html"})})
    public String getIncomeByFilter() {
        try {
            if (year == 0) {
                year = DateUtil.getCurrentYear();
            }
            streamVOList = streamService.getStreamByYear(year);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 获取年份列表
     *
     * @return
     */
    @Action(value = "getYears", results = {@Result(type = "json", params = {"root", "yearList", "contentType", "text/html"})})
    public String getYears() {
        try {
            yearList = streamService.getYears();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 获取流水明细
     *
     * @return
     */
    @Action(value = "getStreamDetail", results = {@Result(type = "json", params = {"root", "streamDetailVOList", "contentType", "text/html"})})
    public String getStreamDetail() {
        try {
            streamDetailVOList = streamService.getStreamDetail(month);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 查询每月流水账信息
     *
     * @return
     */
    @Action(value = "getStreamGroupByMonth", results = {@Result(type = "json", params = {"root", "moneyListMap", "contentType", "text/html"})})
    public String getStreamGroupByMonth() {
        try {
            if (year == 0) {
                year = DateUtil.getCurrentYear();
            }
            moneyListMap = streamService.getStreamGroupByMonth(year);
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
    @Action(value = "exportStreamToExcel", results = {@Result(name = SUCCESS, type = "stream")})
    public String exportStreamToExcel() {
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

    public List<StreamDetailVO> getStreamDetailVOList() {
        return streamDetailVOList;
    }

    public void setStreamDetailVOList(List<StreamDetailVO> streamDetailVOList) {
        this.streamDetailVOList = streamDetailVOList;
    }

    public Map<String, List<String>> getMoneyListMap() {
        return moneyListMap;
    }

    public void setMoneyListMap(Map<String, List<String>> moneyListMap) {
        this.moneyListMap = moneyListMap;
    }

    public List<TypeVo> getYearList() {
        return yearList;
    }

    public void setYearList(List<TypeVo> yearList) {
        this.yearList = yearList;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
