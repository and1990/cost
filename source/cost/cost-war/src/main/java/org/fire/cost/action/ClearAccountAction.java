package org.fire.cost.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.fire.cost.service.ClearAccountService;
import org.fire.cost.vo.ClearAccountDetailVO;
import org.fire.cost.vo.ClearAccountVO;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
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
    private Long clearAccountId;

    //结算明细vo
    private List<ClearAccountDetailVO> detailVOList;

    /**
     * 结算
     *
     * @return
     */
    @Action(value = "clearData", results = {@Result(type = "json", params = {"root", "pageData", "contentType", "text/html"})})
    public String clearData() {
        try {
            clearAccountService.clearData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

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
            detailVOList = clearAccountService.getClearDetailData(clearAccountId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public Long getClearAccountId() {
        return clearAccountId;
    }

    public void setClearAccountId(Long clearAccountId) {
        this.clearAccountId = clearAccountId;
    }

    public List<ClearAccountDetailVO> getDetailVOList() {
        return detailVOList;
    }

    public void setDetailVOList(List<ClearAccountDetailVO> detailVOList) {
        this.detailVOList = detailVOList;
    }
}
