package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.IncomeDaoCustom;
import org.fire.cost.domain.Income;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.IncomeVO;
import org.fire.cost.vo.PageData;

import javax.persistence.Query;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 注释：收入扩展实现类
 * 时间：2014年05月06日 下午3:11
 * 作者：刘腾飞
 */
public class IncomeDaoCustomImpl extends BaseJpaDaoSupport<Income, Long> implements IncomeDaoCustom {

    @Override
    public List<Income> getIncomeByFilter(IncomeVO vo, PageData<IncomeVO> pageData) {
        String sql = "select i.* from cost_income i inner join cost_user u";
        sql += " on i.user_id=u.user_id where 1=1";
        String filterSQL = getFilterSQL(vo);
        if (filterSQL != null && filterSQL.trim().length() != 0) {
            sql += filterSQL;
        }
        sql += " order by create_time desc";

        Query query = entityManager.createNativeQuery(sql, Income.class);
        setAliasValue(vo, query);
        if (vo.isPage()) {
            int page = pageData.getPage() - 1;
            int pageSize = pageData.getPageSize();
            int start = page * pageSize;
            query.setFirstResult(start);
            query.setMaxResults(pageSize);
        }
        List<Income> incomeList = query.getResultList();
        return incomeList;

    }

    @Override
    public int getIncomeTotal(IncomeVO vo) {
        String sql = "select count(*) from cost_income i inner join cost_user u";
        sql += " on i.user_id=u.user_id where 1=1";
        String filterSQL = getFilterSQL(vo);
        if (filterSQL != null && filterSQL.trim().length() != 0) {
            sql += filterSQL;
        }

        Query query = entityManager.createNativeQuery(sql);
        setAliasValue(vo, query);
        int total = Integer.valueOf(query.getSingleResult().toString());
        return total;
    }

    @Override
    public List<IncomeVO> getIncomeGroupByIncomeType(String IncomeStartTime, String IncomeEndTime) {
        return null;
    }

    @Override
    public List<IncomeVO> getIncomeGroupByUser(String IncomeStartTime, String IncomeEndTime) {
        return null;
    }

    /**
     * 得到过滤条件
     *
     * @param vo
     * @return
     */
    private String getFilterSQL(IncomeVO vo) {
        String filterSQL = "";
        String userName = vo.getUserName();
        boolean userNameNotNull = userName != null && userName.length() != 0;
        if (userNameNotNull) {
            filterSQL += " and u.user_name like :userName";
        }
        Integer incomeType = vo.getIncomeType();
        boolean incomeTypeNotNull = incomeType != null && incomeType != 0;
        if (incomeTypeNotNull) {
            filterSQL += " and i.income_type=:incomeType";
        }
        String incomeStartTime = vo.getIncomeStartTime();
        boolean incomeStartTimeNotNull = incomeStartTime != null && incomeStartTime.trim().length() != 0;
        if (incomeStartTimeNotNull) {
            filterSQL += " and i.income_time>=:incomeStartTime";
        }
        String incomeEndTime = vo.getIncomeEndTime();
        boolean incomeEndTimeNotNull = incomeEndTime != null && incomeEndTime.trim().length() != 0;
        if (incomeEndTimeNotNull) {
            filterSQL += " and i.income_time<:incomeEndTime";
        }
        return filterSQL;
    }

    /**
     * 设置值
     *
     * @param vo
     * @param query
     */
    private void setAliasValue(IncomeVO vo, Query query) {
        String userName = vo.getUserName();
        boolean userNameNotNull = userName != null && userName.length() != 0;
        if (userNameNotNull) {
            query.setParameter("userName", "%" + userName + "%");
        }
        Integer incomeType = vo.getIncomeType();
        boolean incomeTypeNotNull = incomeType != null && incomeType != 0;
        if (incomeTypeNotNull) {
            query.setParameter("incomeType", incomeType);
        }
        String incomeStartTime = vo.getIncomeStartTime();
        boolean incomeStartTimeNotNull = incomeStartTime != null && incomeStartTime.trim().length() != 0;
        if (incomeStartTimeNotNull) {
            query.setParameter("incomeStartTime", incomeStartTime);
        }
        String incomeEndTime = vo.getIncomeEndTime();
        boolean incomeEndTimeNotNull = incomeEndTime != null && incomeEndTime.trim().length() != 0;
        if (incomeEndTimeNotNull) {
            Date date = null;
            try {
                date = DateUtil.makeStr2Date(incomeEndTime, false);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            incomeEndTime = DateUtil.makeDate2Str(DateUtil.addDays(date, 1), false);
            query.setParameter("incomeEndTime", incomeEndTime);
        }
    }
}
