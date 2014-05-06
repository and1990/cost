package org.fire.cost.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.fire.cost.service.IncomeService;
import org.fire.cost.vo.IncomeVO;
import org.fire.cost.vo.PageData;
import org.fire.cost.vo.TypeVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 注释：收入service
 * 时间：2014年05月06日 下午3:09
 * 作者：刘腾飞
 */
@Service
public class IncomeServiceImpl implements IncomeService {
    @Override
    public List<IncomeVO> getIncomeByFilter(IncomeVO vo, PageData<IncomeVO> pageData) {
        return null;
    }

    @Override
    public int getIncomeTotal(IncomeVO vo) {
        return 0;
    }

    @Override
    public boolean addIncome(IncomeVO vo) {
        return false;
    }

    @Override
    public boolean modifyIncome(IncomeVO vo) {
        return false;
    }

    @Override
    public boolean deleteIncome(Long IncomeId) {
        return false;
    }

    @Override
    public List<TypeVo> getIncomeType() {
        return null;
    }

    @Override
    public List<IncomeVO> getIncomeGroupByIncomeType(String IncomeStartTime, String IncomeEndTime) {
        return null;
    }

    @Override
    public List<IncomeVO> getIncomeGroupByUser(String IncomeStartTime, String IncomeEndTime) {
        return null;
    }

    @Override
    public HSSFWorkbook getExcelData() {
        return null;
    }
}
