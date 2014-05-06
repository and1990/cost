package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.IncomeDaoCustom;
import org.fire.cost.domain.Income;
import org.fire.cost.vo.IncomeVO;
import org.fire.cost.vo.PageData;

import java.util.List;

/**
 * 注释：收入扩展实现类
 * 时间：2014年05月06日 下午3:11
 * 作者：刘腾飞
 */
public class IncomeDaoCustomImpl implements IncomeDaoCustom {
    @Override
    public List<Income> getIncomeByFilter(IncomeVO vo, PageData<IncomeVO> pageData) {
        return null;
    }

    @Override
    public int getIncomeTotal(IncomeVO vo) {
        return 0;
    }

    @Override
    public List<IncomeVO> getIncomeGroupByIncomeType(String IncomeStartTime, String IncomeEndTime) {
        return null;
    }

    @Override
    public List<IncomeVO> getIncomeGroupByUser(String IncomeStartTime, String IncomeEndTime) {
        return null;
    }
}
