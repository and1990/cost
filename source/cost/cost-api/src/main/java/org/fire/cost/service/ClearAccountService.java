package org.fire.cost.service;

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
     * 结算
     */
    void clearData();
}
