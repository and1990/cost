package org.fire.cost.dao.custom;

import org.fire.cost.domain.Income;
import org.fire.cost.vo.IncomeVO;
import org.fire.cost.vo.PageData;

import java.util.List;

/**
 * 账单dao扩展类
 *
 * @author liutengfei
 */
public interface IncomeDaoCustom {
    List<Income> getIncomeByFilter(IncomeVO vo, PageData<IncomeVO> pageData);

    int getIncomeTotal(IncomeVO vo);

    List<IncomeVO> getIncomeGroupByIncomeType(String IncomeStartTime, String IncomeEndTime);

    List<IncomeVO> getIncomeGroupByUser(String IncomeStartTime, String IncomeEndTime);

}
