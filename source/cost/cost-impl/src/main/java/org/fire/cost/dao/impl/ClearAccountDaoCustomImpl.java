package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.ClearAccountDaoCustom;
import org.fire.cost.domain.ClearAccount;
import org.fire.cost.vo.PageData;

import javax.persistence.Query;
import java.util.List;

/**
 * 注释：结算Dao扩展实现类
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-19 下午9:00
 */
public class ClearAccountDaoCustomImpl extends BaseJpaDaoSupport<ClearAccount, Long> implements ClearAccountDaoCustom {
    /**
     * 获取结算信息
     *
     * @return
     */
    @Override
    public List<ClearAccount> getClearData(PageData pageData) {
        String sql = "select * from clear_account";
        Query query = entityManager.createNativeQuery(sql, ClearAccount.class);
        int page = pageData.getPage() - 1;
        int pageSize = pageData.getPageSize();
        int start = page * pageSize;
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        List<ClearAccount> clearAccountList = query.getResultList();
        return clearAccountList;
    }

    /**
     * 获取结算记录数
     *
     * @return
     */
    @Override
    public int getClearTotal() {
        String sql = "select count(*) from clear_account";
        Query query = entityManager.createNativeQuery(sql);
        int total = Integer.valueOf(query.getSingleResult().toString());
        return total;
    }

    /**
     * 获取最后一次的结算记录
     *
     * @return
     */
    @Override
    public ClearAccount getLatestClearData() {
        String sql = "select * from clear_account order by clear_account_id desc limit 0,1";
        Query query = entityManager.createNativeQuery(sql, ClearAccount.class);
        List<ClearAccount> clearAccountList = query.getResultList();
        if (clearAccountList != null && clearAccountList.size() != 0) {
            return clearAccountList.get(0);
        }
        return null;
    }
}
