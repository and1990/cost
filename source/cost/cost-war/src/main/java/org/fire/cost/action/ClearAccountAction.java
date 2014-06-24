package org.fire.cost.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.enums.OverStatusEnum;
import org.fire.cost.service.ClearAccountService;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.ClearAccountDetailVO;
import org.fire.cost.vo.ClearAccountVO;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 注释：结算action
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-14 下午2:09
 */
@Namespace("/")
@Controller
public class ClearAccountAction extends BaseAction {
    @Resource
    private ClearAccountService clearAccountService;

    //结算id
    private ClearAccountVO clearAccountVO = new ClearAccountVO();

    //结算明细vo
    private List<ClearAccountDetailVO> detailVOList;

    //结算明细id
    private Long clearAccountDetailId;


    /**
     * 获取结算信息
     *
     * @return
     */
    @Action(value = "getClearData", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String getClearData() {
        try {
            pageData.setPage(page);
            pageData.setPageSize(rows);
            List<ClearAccountVO> clearAccountVOList = clearAccountService.getClearData(pageData);
            int total = clearAccountService.getClearTotal();
            pageData.setRows(clearAccountVOList);
            pageData.setTotal(total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 获取结算明细信息
     *
     * @return
     */
    @Action(value = "getClearDetailData", results = {@Result(type = "json", params = {"root", "detailVOList", "contentType", "text/html"})})
    public String getClearDetailData() {
        try {
            Long clearAccountId = clearAccountVO.getClearAccountId();
            detailVOList = clearAccountService.getClearDetailData(clearAccountId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Action(value = "getLastestClearDate", results = {@Result(type = "json", params = {"root", "clearAccountVO", "contentType", "text/html"})})
    public String getLastestClearDate() {
        try {
            String clearDate = clearAccountService.getLatestClearDate();
            Date date = DateUtil.makeStr2Date(clearDate, false);
            clearDate = DateUtil.makeDate2Str(date, false);
            clearAccountVO.setStartDate(clearDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 结算
     *
     * @return
     */
    @Action(value = "clearData", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String clearData() {
        try {
            clearAccountService.clearData(clearAccountVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 明细结算
     *
     * @return
     */
    @Action(value = "clearDetail", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String clearDetail() {
        clearAccountService.updateClearDetail(OverStatusEnum.Clear.getCode(), clearAccountDetailId);
        return SUCCESS;
    }

    /**
     * 明细取消结算
     *
     * @return
     */
    @Action(value = "cancelDetail", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String cancelDetail() {
        clearAccountService.updateClearDetail(OverStatusEnum.Not_Clear.getCode(), clearAccountDetailId);
        return SUCCESS;
    }

    public ClearAccountVO getClearAccountVO() {
        return clearAccountVO;
    }

    public void setClearAccountVO(ClearAccountVO clearAccountVO) {
        this.clearAccountVO = clearAccountVO;
    }

    public List<ClearAccountDetailVO> getDetailVOList() {
        return detailVOList;
    }

    public void setDetailVOList(List<ClearAccountDetailVO> detailVOList) {
        this.detailVOList = detailVOList;
    }

    public Long getClearAccountDetailId() {
        return clearAccountDetailId;
    }

    public void setClearAccountDetailId(Long clearAccountDetailId) {
        this.clearAccountDetailId = clearAccountDetailId;
    }
}
