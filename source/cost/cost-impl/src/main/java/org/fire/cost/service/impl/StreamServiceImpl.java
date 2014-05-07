package org.fire.cost.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.fire.cost.dao.StreamDao;
import org.fire.cost.service.StreamService;
import org.fire.cost.vo.StreamVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.List;

/**
 * 注释：流水账service实现
 * 时间：2014年05月07日 上午11:00
 * 作者：刘腾飞[liutengfei]
 */
@Service
public class StreamServiceImpl implements StreamService {
    @Resource
    private StreamDao streamDao;

    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public List<StreamVO> getIncomeByYear(int year) {
        List<StreamVO> voList = new ArrayList<StreamVO>();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return voList;
    }

    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public List<StreamVO> getIncomeGroupByMonth() {
        List<StreamVO> voList = new ArrayList<StreamVO>();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return voList;
    }

    @Override
    public HSSFWorkbook getExcelData() {
        return null;
    }
}
