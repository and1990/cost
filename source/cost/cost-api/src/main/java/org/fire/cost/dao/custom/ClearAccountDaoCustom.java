package org.fire.cost.dao.custom;

import org.fire.cost.domain.ClearAccount;
import org.fire.cost.vo.PageData;

import java.util.List;

/**
 * 注释：结算Dao扩展类
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-19 下午8:59
 */
public interface ClearAccountDaoCustom {
    /**
     * 获取结算信息
     *
     * @return
     */
    List<ClearAccount> getClearData(PageData pageData);

    /**
     * 获取结算记录数
     *
     * @return
     */
    int getClearTotal();

    /**
     * 获取最后一次的结算记录
     *
     * @return
     */
    ClearAccount getLatestClearData();
}
