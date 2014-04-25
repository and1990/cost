package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.AccountDaoCustom;
import org.fire.cost.domain.Account;
import org.fire.cost.enums.AccountTypeEnum;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.PageData;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        String sql = "select a.* from cost_account a inner join cost_user u";
        sql += " on a.user_id=u.user_id where 1=1";
        String filterSQL = getFilterSQL(vo);
        if (filterSQL != null && filterSQL.trim().length() != 0) {
            sql += filterSQL;
        }
        sql += " order by create_time desc";

        Query query = entityManager.createNativeQuery(sql, Account.class);
        try {
            setAliasValue(vo, query);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
    public int getAccountTotal(AccountVO vo) {
        String sql = "select count(*) from cost_account a inner join cost_user u";
        sql += " on a.user_id=u.user_id where 1=1";
        String filterSQL = getFilterSQL(vo);
        if (filterSQL != null && filterSQL.trim().length() != 0) {
            sql += filterSQL;
        }

        Query query = entityManager.createNativeQuery(sql);
        try {
            setAliasValue(vo, query);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int total = Integer.valueOf(query.getSingleResult().toString());
        return total;
    }

    /**
     * 查找消费类型对应的账单数据
     *
     * @param accountStartTime 消费开始时间
     * @param accountEndTime   消费结束时间
     * @return
     */
    @Override
    public List<AccountVO> getAccountGroupByAccountType(String accountStartTime, String accountEndTime) {

        String sql = "select account_type,sum(account_money) as account_money " +
                "from cost_account WHERE 1=1 ";
        boolean accountStartTimeNotNull = accountStartTime != null && accountStartTime.trim().length() != 0;
        if (accountStartTimeNotNull) {
            sql += "and account_time>=:startTime ";
        }
        boolean accountEndTimeNotNull = accountEndTime != null && accountEndTime.trim().length() != 0;
        if (accountEndTimeNotNull) {
            sql += "and account_time<:endTime ";
        }
        sql += "group by account_type ";

        Query query = entityManager.createNativeQuery(sql);
        if (accountStartTimeNotNull) {
            query.setParameter("startTime", accountStartTime);
        }
        if (accountEndTimeNotNull) {
            query.setParameter("endTime", accountEndTime);
        }
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> resultList = query.getResultList();
        List<AccountVO> accountVOList = new ArrayList<AccountVO>();
        boolean resultDataNotNull = resultList != null && resultList.size() != 0;
        if (resultDataNotNull) {
            for (Map map : resultList) {
                AccountVO accountVO = new AccountVO();
                Integer accountType = Integer.valueOf(map.get("account_type").toString());
                accountVO.setAccountType(accountType);
                accountVO.setAccountTypeName(AccountTypeEnum.getName(accountType));
                accountVO.setAccountMoney(new BigDecimal(map.get("account_money").toString()));
                accountVOList.add(accountVO);
            }
        }
        return accountVOList;
    }


    /**
     * 查找用户对应的账单数据
     *
     * @param accountStartTime 消费开始时间
     * @param accountEndTime   消费结束时间
     * @return
     */
    @Override
    public List<AccountVO> getAccountGroupByUser(String accountStartTime, String accountEndTime) {
        String sql = "SELECT " +
                "  CONCAT(u.user_name, '_', u.user_id) AS userName, " +
                "  SUM(a.account_money) AS accountMoney " +
                "FROM " +
                "  cost_user u " +
                "INNER JOIN cost_account a ON u.user_id = a.user_id WHERE 1=1 ";
        boolean accountStartTimeNotNull = accountStartTime != null && accountStartTime.trim().length() != 0;
        if (accountStartTimeNotNull) {
            sql += "and a.account_time>=:startTime ";
        }
        boolean accountEndTimeNotNull = accountEndTime != null && accountEndTime.trim().length() != 0;
        if (accountEndTimeNotNull) {
            sql += "and a.account_time<:endTime ";
        }
        sql += "GROUP BY u.user_id, u.user_name";
        Query query = entityManager.createNativeQuery(sql);
        if (accountStartTimeNotNull) {
            query.setParameter("startTime", accountStartTime);
        }
        if (accountEndTimeNotNull) {
            query.setParameter("endTime", accountEndTime);
        }
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> resultList = query.getResultList();
        List<AccountVO> accountVOList = new ArrayList<AccountVO>();
        boolean resultDataNotNull = resultList != null && resultList.size() != 0;
        if (resultDataNotNull) {
            for (Map map : resultList) {
                AccountVO accountVO = new AccountVO();
                accountVO.setUserName(map.get("userName").toString());
                accountVO.setAccountMoney(new BigDecimal(map.get("accountMoney").toString()));
                accountVOList.add(accountVO);
            }
        }
        return accountVOList;
    }

    /**
     * 得到过滤条件
     *
     * @param vo
     * @return
     */
    private String getFilterSQL(AccountVO vo) {
        String filterSQL = "";
        String userName = vo.getUserName();
        boolean userNameNotNull = userName != null && userName.length() != 0;
        if (userNameNotNull) {
            filterSQL += " and u.user_name like :userName";
        }
        Integer accountType = vo.getAccountType();
        boolean accountTypeNotNull = accountType != null && accountType != 0;
        if (accountTypeNotNull) {
            filterSQL += " and a.account_type=:accountType";
        }
        Integer accountStatus = vo.getAccountStatus();
        boolean accountStatusNotNull = accountStatus != null && accountStatus != 0;
        if (accountStatusNotNull) {
            filterSQL += " and a.account_status=:accountStatus";
        }
        String accountStartTime = vo.getAccountStartTime();
        boolean accountStartTimeNotNull = accountStartTime != null && accountStartTime.trim().length() != 0;
        if (accountStartTimeNotNull) {
            filterSQL += " and a.account_time>=:accountStartTime";
        }
        String accountEndTime = vo.getAccountEndTime();
        boolean accountEndTimeNotNull = accountEndTime != null && accountEndTime.trim().length() != 0;
        if (accountEndTimeNotNull) {
            filterSQL += " and a.account_time<=:accountEndTime";
        }
        return filterSQL;
    }

    /**
     * 设置值
     *
     * @param vo
     * @param query
     */
    private void setAliasValue(AccountVO vo, Query query) throws ParseException {
        String userName = vo.getUserName();
        boolean userNameNotNull = userName != null && userName.length() != 0;
        if (userNameNotNull) {
            query.setParameter("userName", "%" + userName + "%");
        }
        Integer accountType = vo.getAccountType();
        boolean accountTypeNotNull = accountType != null && accountType != 0;
        if (accountTypeNotNull) {
            query.setParameter("accountType", accountType);
        }
        Integer accountStatus = vo.getAccountStatus();
        boolean accountStatusNotNull = accountStatus != null && accountStatus != 0;
        if (accountStatusNotNull) {
            query.setParameter("accountStatus", accountStatus);
        }
        String accountStartTime = vo.getAccountStartTime();
        boolean accountStartTimeNotNull = accountStartTime != null && accountStartTime.trim().length() != 0;
        if (accountStartTimeNotNull) {
            query.setParameter("accountStartTime", accountStartTime);
        }
        String accountEndTime = vo.getAccountEndTime();
        boolean accountEndTimeNotNull = accountEndTime != null && accountEndTime.trim().length() != 0;
        if (accountEndTimeNotNull) {
            Date date = DateUtil.makeStr2Date(accountEndTime, false);
            accountEndTime = DateUtil.makeDate2Str(DateUtil.addDays(date, 1), false);
            query.setParameter("accountEndTime", accountEndTime);
        }
    }

    /**
     * 得到账单VO
     *
     * @param resultList
     * @return
     */
    private List<AccountVO> getAccountVOList(List<Map> resultList) {
        List<AccountVO> accountVOList = new ArrayList<AccountVO>();
        boolean resultDataNotNull = resultList != null && resultList.size() != 0;
        if (resultDataNotNull) {
            for (Map map : resultList) {
                AccountVO accountVO = new AccountVO();
                accountVO.setUserName(map.get("userName").toString());
                accountVO.setAccountMoney(new BigDecimal(map.get("accountMoney").toString()));
                accountVOList.add(accountVO);
            }
        }
        return accountVOList;
    }

}
