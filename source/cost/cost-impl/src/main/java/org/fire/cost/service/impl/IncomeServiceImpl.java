package org.fire.cost.service.impl;

import org.apache.poi.hssf.usermodel.*;
import org.fire.cost.dao.IncomeDao;
import org.fire.cost.dao.UserDao;
import org.fire.cost.domain.Income;
import org.fire.cost.domain.User;
import org.fire.cost.enums.IncomeTypeEnum;
import org.fire.cost.service.IncomeService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.IncomeVO;
import org.fire.cost.vo.PageData;
import org.fire.cost.vo.TypeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 注释：收入service
 * 时间：2014年05月06日 下午3:09
 * 作者：刘腾飞
 */
@Service
public class IncomeServiceImpl implements IncomeService {
    @Resource
    private IncomeDao incomeDao;

    @Resource
    private UserDao userDao;

    /**
     * 查询收入
     *
     * @param vo
     * @param pageData
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public List<IncomeVO> getIncomeByFilter(IncomeVO vo, PageData<IncomeVO> pageData) {
        List<IncomeVO> incomeVOList = new ArrayList<IncomeVO>();
        try {
            List<Income> incomeList = incomeDao.getIncomeByFilter(vo, pageData);
            if (incomeList != null && incomeList.size() != 0) {
                for (Income income : incomeList) {
                    IncomeVO incomeVO = makeIncomeToVo(income);
                    incomeVOList.add(incomeVO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return incomeVOList;
    }

    /**
     * 获取收入记录数
     *
     * @param vo
     * @return
     */
    @Override
    public int getIncomeTotal(IncomeVO vo) {
        int total = 0;
        try {
            total = incomeDao.getIncomeTotal(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 增加收入
     *
     * @param vo
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean addIncome(IncomeVO vo) {
        try {
            Income income = makeVoToIncome(null, vo);
            Long userId = AuthenticationUtil.getLoginUserId();
            User user = userDao.findOne(userId);
            income.setUser(user);
            incomeDao.save(income);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 修改收入
     *
     * @param vo
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean modifyIncome(IncomeVO vo) {
        try {
            Long incomeId = vo.getIncomeId();
            Income income = incomeDao.findOne(incomeId);
            income = makeVoToIncome(income, vo);
            incomeDao.save(income);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除收入
     *
     * @param incomeId
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean deleteIncome(Long incomeId) {
        try {
            incomeDao.delete(incomeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取收入类型
     *
     * @return
     */
    @Override
    public List<TypeVo> getIncomeType() {
        IncomeTypeEnum[] typeEnums = IncomeTypeEnum.values();
        List<TypeVo> typeList = new ArrayList<TypeVo>();
        for (IncomeTypeEnum typeEnum : typeEnums) {
            TypeVo vo = new TypeVo();
            vo.setCode(typeEnum.getCode());
            vo.setName(typeEnum.getName());
            typeList.add(vo);
        }
        return typeList;
    }

    /**
     * 获取收入类型对应的收入
     *
     * @param IncomeStartTime 消费开始时间
     * @param IncomeEndTime   消费结束时间
     * @return
     */
    @Override
    public List<IncomeVO> getIncomeGroupByIncomeType(String IncomeStartTime, String IncomeEndTime) {
        try {

        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 获取每个人对应的收入
     *
     * @param IncomeStartTime 消费开始时间
     * @param IncomeEndTime   消费结束时间
     * @return
     */
    @Override
    public List<IncomeVO> getIncomeGroupByUser(String IncomeStartTime, String IncomeEndTime) {
        try {

        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 获取导出的excel数据
     *
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public HSSFWorkbook getExcelData() {
        HSSFWorkbook hwb = new HSSFWorkbook();
        try {
            // 加边框
            HSSFCellStyle style = hwb.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            // 列宽
            HSSFSheet sheet = hwb.createSheet("收入");
            for (int i = 0; i < 6; i++) {
                sheet.setColumnWidth(i, (short) 5000);
            }
            HSSFRow row = null;
            HSSFCell cell = null;
            createExcelTitle(sheet, style, row, cell);
            createExcelBody(sheet, style, row, cell);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hwb;
    }

    /**
     * 创建表头
     *
     * @param sheet
     * @param row
     * @param cell
     */
    private void createExcelTitle(HSSFSheet sheet, HSSFCellStyle style, HSSFRow row, HSSFCell cell) {
        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("用户名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("金额");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("收入方式");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("收入时间");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("创建人");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("修改时间");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("修改人");
        cell.setCellStyle(style);
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("备注");
        cell.setCellStyle(style);
    }

    /**
     * 创建表体数据
     */
    private void createExcelBody(HSSFSheet sheet, HSSFCellStyle style, HSSFRow row, HSSFCell cell) {
        int rowIndex = 1;
        IncomeVO incomeVO = new IncomeVO();
        incomeVO.setPage(false);
        List<IncomeVO> incomeVOList = getIncomeByFilter(incomeVO, new PageData<IncomeVO>());
        for (IncomeVO vo : incomeVOList) {
            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(vo.getUserName());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(vo.getIncomeMoney().toString());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            Integer incomeType = vo.getIncomeType();
            String incomeTypeName = IncomeTypeEnum.getName(incomeType);
            cell.setCellValue(incomeTypeName);
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(vo.getIncomeTime());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(vo.getCreateTime());
            cell = row.createCell(5);
            cell.setCellValue(vo.getCreateUser());
            cell = row.createCell(6);
            cell.setCellValue(vo.getModifyUser());
            cell = row.createCell(7);
            cell.setCellValue(vo.getModifyTime());
            cell = row.createCell(8);
            cell.setCellValue(vo.getIncomeRemark());
            cell.setCellStyle(style);
            rowIndex++;
        }
    }

    /**
     * 将VO转换成实体
     *
     * @param income
     * @param incomeVO
     * @return
     */
    private Income makeVoToIncome(Income income, IncomeVO incomeVO) throws ParseException {
        if (income == null) {
            income = new Income();
            income.setCreateUser(AuthenticationUtil.getUserName());
            income.setCreateTime(new Date());
        }
        income.setIncomeMoney(incomeVO.getIncomeMoney());
        String incomeTime = incomeVO.getIncomeTime();
        income.setIncomeTime(DateUtil.makeStr2Date(incomeTime, false));
        income.setIncomeType(incomeVO.getIncomeType());
        income.setIncomeRemark(incomeVO.getIncomeRemark());
        income.setModifyTime(new Date());
        income.setModifyUser(AuthenticationUtil.getUserName());
        return income;
    }

    /**
     * 将实体转换成VO
     *
     * @return
     */
    private IncomeVO makeIncomeToVo(Income income) {
        IncomeVO incomeVO = new IncomeVO();
        if (income == null) {
            return incomeVO;
        }
        User user = income.getUser();
        incomeVO.setIncomeId(income.getIncomeId());
        incomeVO.setUserId(user.getUserId());
        incomeVO.setUserName(user.getUserName());
        incomeVO.setIncomeMoney(income.getIncomeMoney());
        Date incomeTime = income.getIncomeTime();
        incomeVO.setIncomeTime(DateUtil.makeDate2Str(incomeTime, false));
        Integer incomeType = income.getIncomeType();
        incomeVO.setIncomeType(incomeType);
        String incomeTypeName = IncomeTypeEnum.getName(incomeType);
        incomeVO.setIncomeTypeName(incomeTypeName);
        incomeVO.setCreateUser(income.getCreateUser());
        incomeVO.setCreateTime(DateUtil.makeDate2Str(income.getCreateTime(), true));
        incomeVO.setModifyUser(income.getModifyUser());
        incomeVO.setModifyTime(DateUtil.makeDate2Str(income.getCreateTime(), true));
        incomeVO.setIncomeRemark(income.getIncomeRemark());
        return incomeVO;
    }
}
