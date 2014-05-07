package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.StreamDaoCustom;
import org.fire.cost.domain.Stream;
import org.fire.cost.vo.StreamVO;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 注释：收入扩展实现类
 * 时间：2014年05月07日 下午3:11
 * 作者：刘腾飞
 */
public class StreamDaoCustomImpl extends BaseJpaDaoSupport<Stream, Long> implements StreamDaoCustom {
    @Override
    public List<StreamVO> getStreamGroupByMonth(int year) {
        String sql = "select month,sum(income_money) as income_money,sum(account_money) as account_money," +
                "sum(left_money) as left_money from cost_stream where year=:year group by month order by month";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("year", year);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list = query.getResultList();
        List<StreamVO> voList = new ArrayList<StreamVO>();
        for (Map map : list) {
            StreamVO vo = new StreamVO();
            vo.setMonth(Integer.valueOf(map.get("month").toString()));
            vo.setIncomeMoney(new BigDecimal(map.get("income_money").toString()));
            vo.setAccountMoney(new BigDecimal(map.get("account_money").toString()));
            vo.setLeftMoney(new BigDecimal(map.get("left_money").toString()));
            voList.add(vo);
        }
        return voList;
    }
}
