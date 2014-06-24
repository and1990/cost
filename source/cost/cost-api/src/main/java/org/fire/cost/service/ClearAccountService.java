package org.fire.cost.service;

import org.fire.cost.vo.ClearAccountDetailVO;
import org.fire.cost.vo.ClearAccountVO;
import org.fire.cost.vo.PageData;

import java.util.List;

/**
 * 注释：结算service
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-19 下午9:01
 */
public interface ClearAccountService {
    /**
     * 获取结算信息
     *
     * @return
     */
    List<ClearAccountVO> getClearData(PageData pageData);

    /**
     * 获取结算记录数
     *
     * @return
     */
    int getClearTotal();

    /**
     * 获取结算明细信息
     *
     * @param clearAccountId
     * @return
     */
    List<ClearAccountDetailVO> getClearDetailData(Long clearAccountId);

    /**
     * 获取最后一次的结算日期
     *
     * @return
     */
    String getLatestClearDate();

    /**
     * 结算
     *
     * @param clearAccountVO
     */
    void clearData(ClearAccountVO clearAccountVO);

    /**
     * 更新结算明细结算状态
     *
     * @param code
     * @param clearAccountDetailId
     */
    void updateClearDetail(int code, Long clearAccountDetailId);
}
