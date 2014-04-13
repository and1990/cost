package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.AccountDaoCustom;
import org.fire.cost.entity.Account;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.PageData;

import javax.persistence.Query;
import java.util.List;

/**
 * 账单扩展实现类
 *
 * @author liutengfei
 */
public class AccountDaoCustomImpl extends BaseJpaDaoSupport<Account, Long> implements AccountDaoCustom {

    /**
     * 根据过滤条件查询账单信息
     */
    public List<Account> getAccountByFilter(AccountVO vo, PageData<AccountVO> pageData) {
        String sql = "select * from cost_account order by create_time desc";
        Query query = entityManager.createNativeQuery(sql, Account.class);

        int page = pageData.getPage() - 1;
        int pageSize = pageData.getPageSize();
        int start = page * pageSize;
        query.setFirstResult(start);
        query.setMaxResults(pageSize);

        List<Account> resultList = query.getResultList();
        return resultList;
    }

    /**
     * 得到账单数据总数
     *
     * @return
     */
    @Override
    public int getAccountTotal() {
        String sql = "select count(*) from cost_account";
        Query query = entityManager.createNativeQuery(sql);
        int total = Integer.valueOf(query.getSingleResult().toString());
        return total;
    }

}
