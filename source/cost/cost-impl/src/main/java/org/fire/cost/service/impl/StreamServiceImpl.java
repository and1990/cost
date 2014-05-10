package org.fire.cost.service.impl;

import org.apache.poi.hssf.usermodel.*;
import org.fire.cost.dao.StreamDao;
import org.fire.cost.domain.Stream;
import org.fire.cost.enums.IncomeAndAccountEnum;
import org.fire.cost.service.AccountService;
import org.fire.cost.service.IncomeService;
import org.fire.cost.service.StreamService;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 注释：流水账service实现
 * 时间：2014年05月07日 上午11:00
 * 作者：刘腾飞[liutengfei]
 */
@Service
public class StreamServiceImpl implements StreamService {
    @Resource
    private AccountService accountService;

    @Resource
    private IncomeService incomeService;

    @Resource
    private StreamDao streamDao;

    /**
     * 查询月份对应的流水账
     *
     * @param year
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public List<StreamVO> getStreamByYear(int year) {
        List<StreamVO> voList = new ArrayList<StreamVO>();
        try {
            List<Stream> streamList = streamDao.findStreamByYear(year);
            if (streamList == null || streamList.size() == 0) {
                return voList;
            }
            for (Stream stream : streamList) {
                StreamVO streamVO = makeStreamToVo(stream);
                voList.add(streamVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voList;
    }

    /**
     * 获取年份列表
     *
     * @return
     */
    @Override
    public List<TypeVo> getYears() {
        List<TypeVo> typeVoList = new ArrayList<TypeVo>();
        int year = DateUtil.getCurrentYear();
        for (int i = year - 2; i <= year; i++) {
            TypeVo vo = new TypeVo();
            vo.setCode(i);
            vo.setName(i + "");
            typeVoList.add(vo);
        }
        return typeVoList;
    }

    /**
     * 同步该年份的流水账数据
     *
     * @param year
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public boolean synStreamData(int year) {
        try {
            List<Stream> entityList = streamDao.findStreamByYear(year);
            streamDao.delete(entityList);
            List<Stream> streamList = getSynData(year);
            streamDao.save(streamList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取同步数据
     *
     * @param year
     * @return
     */
    private List<Stream> getSynData(int year) {
        List<Stream> streamList = new ArrayList<Stream>();
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        for (int month = 0; month <= currentMonth; month++) {
            List<IncomeVO> incomeDataList = getIncomeDataByMonth(year, month);
            List<AccountVO> accountDataList = getAccountDataByMonth(year, month);
            BigDecimal totalIncomeMoney = getTotalIncomeMoney(incomeDataList);
            BigDecimal totalAccountMoney = getTotalAccountMoney(accountDataList);
            BigDecimal leftMoney = totalIncomeMoney.subtract(totalAccountMoney);
            Stream stream = new Stream();
            stream.setYear(year);
            stream.setMonth(month + 1);
            stream.setIncomeMoney(totalIncomeMoney);
            stream.setAccountMoney(totalAccountMoney);
            stream.setLeftMoney(leftMoney);
            stream.setCreateTime(new Date());
            streamList.add(stream);
        }
        return streamList;
    }

    /**
     * 获取流水明细
     *
     * @param month
     * @return
     */
    @Override
    public List<StreamDetailVO> getStreamDetail(int year, int month) {
        List<AccountVO> accountVOList = getAccountDataByMonth(year, month);
        List<IncomeVO> incomeVOList = getIncomeDataByMonth(year, month);
        List<StreamDetailVO> streamDetailVOList = getDetailData(accountVOList, incomeVOList);
        return streamDetailVOList;
    }


    /**
     * 获取月份对应的流水账记录
     *
     * @param year
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public Map<String, List<String>> getStreamGroupByMonth(int year) {
        Map<String, List<String>> streamVoListMap = new HashMap<String, List<String>>();
        try {
            List<StreamVO> streamList = streamDao.getStreamGroupByMonth(year);
            List<String> incomeList = getMoneyList(streamList, 1);
            streamVoListMap.put("1", incomeList);
            List<String> accountList = getMoneyList(streamList, 2);
            streamVoListMap.put("2", accountList);
            List<String> leftList = getMoneyList(streamList, 3);
            streamVoListMap.put("3", leftList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return streamVoListMap;
    }


    /**
     * 获取excel数据
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
     * 获取金额
     *
     * @param streamList
     * @param type       类型，1：收入，2：支出，3：剩余
     * @return
     */
    private List<String> getMoneyList(List<StreamVO> streamList, int type) {
        int[] monthArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        List<String> moneyList = new ArrayList<String>();
        for (int month : monthArr) {
            String moneyStr = "0";
            for (StreamVO vo : streamList) {
                int streamMonth = vo.getMonth();
                if (month != streamMonth) {
                    continue;
                }
                BigDecimal money = BigDecimal.ZERO;
                switch (type) {
                    case 1:
                        money = vo.getIncomeMoney();
                        break;
                    case 2:
                        money = vo.getAccountMoney();
                        break;
                    case 3:
                        money = vo.getLeftMoney();
                        break;
                    default:
                        money = BigDecimal.ZERO;
                }
                moneyStr = money.toString();
            }
            moneyList.add(moneyStr);
        }
        return moneyList;
    }

    /**
     * 根据月份获取账单数据
     *
     * @param year
     * @param month
     * @return
     */
    private List<AccountVO> getAccountDataByMonth(int year, int month) {
        PageData<AccountVO> pageData = new PageData<AccountVO>();
        pageData.setPage(1);
        pageData.setPageSize(Integer.MAX_VALUE);
        AccountVO vo = new AccountVO();
        vo.setAccountStartTime(year + "-" + month + "-1");
        vo.setAccountEndTime(year + "-" + month + "-31");
        List<AccountVO> accountVOList = accountService.getAccountByFilter(vo, pageData);
        return accountVOList;
    }

    /**
     * 根据月份获取收入数据
     *
     * @param year
     * @param month
     * @return
     */
    private List<IncomeVO> getIncomeDataByMonth(int year, int month) {
        PageData<IncomeVO> pageData = new PageData<IncomeVO>();
        pageData.setPage(1);
        pageData.setPageSize(Integer.MAX_VALUE);
        IncomeVO vo = new IncomeVO();
        vo.setIncomeStartTime(year + "-" + month + "-1");
        vo.setIncomeEndTime(year + "-" + month + "-31");
        List<IncomeVO> incomeVOList = incomeService.getIncomeByFilter(vo, pageData);
        return incomeVOList;
    }

    /**
     * 整合收支明细数据
     *
     * @param accountVOList
     * @param incomeVOList
     * @return
     */
    private List<StreamDetailVO> getDetailData(List<AccountVO> accountVOList, List<IncomeVO> incomeVOList) {
        List<StreamDetailVO> detailVOList = new ArrayList<StreamDetailVO>();
        boolean accountNotNull = accountVOList != null && accountVOList.size() != 0;
        if (accountNotNull) {
            for (AccountVO accountVO : accountVOList) {
                StreamDetailVO detailVO = new StreamDetailVO();
                detailVO.setDate(accountVO.getAccountTime());
                detailVO.setMoney(accountVO.getAccountMoney());
                int code = IncomeAndAccountEnum.Account.getCode();
                detailVO.setType(code);
                String name = IncomeAndAccountEnum.Account.getName();
                detailVO.setTypeName(name);
                detailVO.setRemark(accountVO.getAccountRemark());
                detailVOList.add(detailVO);
            }
        }
        boolean incomeNotNull = incomeVOList != null && incomeVOList.size() != 0;
        if (incomeNotNull) {
            for (IncomeVO incomeVO : incomeVOList) {
                StreamDetailVO detailVO = new StreamDetailVO();
                detailVO.setDate(incomeVO.getIncomeTime());
                detailVO.setMoney(incomeVO.getIncomeMoney());
                int code = IncomeAndAccountEnum.Income.getCode();
                detailVO.setType(code);
                String name = IncomeAndAccountEnum.Income.getName();
                detailVO.setTypeName(name);
                detailVO.setRemark(incomeVO.getIncomeRemark());
                detailVOList.add(detailVO);
            }
        }
        Collections.sort(detailVOList, new Comparator<StreamDetailVO>() {
            @Override
            public int compare(StreamDetailVO o1, StreamDetailVO o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return detailVOList;
    }

    /**
     * 获取收入总金额
     *
     * @param incomeVOList
     * @return
     */
    private BigDecimal getTotalIncomeMoney(List<IncomeVO> incomeVOList) {
        BigDecimal totalMoney = BigDecimal.ZERO;
        boolean incomeNotNull = incomeVOList != null && incomeVOList.size() != 0;
        if (incomeNotNull) {
            for (IncomeVO incomeVO : incomeVOList) {
                BigDecimal incomeMoney = incomeVO.getIncomeMoney();
                totalMoney = totalMoney.add(incomeMoney);
            }
        }
        return totalMoney;
    }

    /**
     * 获取支出总金额
     *
     * @param accountVOList
     * @return
     */
    private BigDecimal getTotalAccountMoney(List<AccountVO> accountVOList) {
        BigDecimal totalMoney = BigDecimal.ZERO;
        boolean incomeNotNull = accountVOList != null && accountVOList.size() != 0;
        if (incomeNotNull) {
            for (AccountVO accountVO : accountVOList) {
                BigDecimal incomeMoney = accountVO.getAccountMoney();
                totalMoney = totalMoney.add(incomeMoney);
            }
        }
        return totalMoney;
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
        cell.setCellValue("ID");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("年份");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("月份");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("收入金额");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("支出金额");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("剩余金额");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);
    }

    /**
     * 创建表体数据
     */
    private void createExcelBody(HSSFSheet sheet, HSSFCellStyle style, HSSFRow row, HSSFCell cell) {
        int rowIndex = 1;
        List<StreamVO> streamVOList = getStreamByYear(2014);
        for (StreamVO vo : streamVOList) {
            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(vo.getStreamId());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(vo.getYear());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(vo.getMonth());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(vo.getIncomeMoney().toString());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(vo.getAccountMoney().toString());
            cell = row.createCell(5);
            cell.setCellValue(vo.getLeftMoney().toString());
            cell = row.createCell(6);
            cell.setCellValue(vo.getCreateTime());
            cell.setCellStyle(style);
            rowIndex++;
        }
    }

    /**
     * 将VO转换成PO
     *
     * @param stream
     * @param vo
     * @return
     */
    private Stream makeVoToStream(Stream stream, StreamVO vo) {
        if (stream == null) {
            stream = new Stream();
            stream.setCreateTime(new Date());
        }
        stream.setYear(vo.getYear());
        stream.setMonth(vo.getMonth());
        stream.setIncomeMoney(vo.getIncomeMoney());
        stream.setAccountMoney(vo.getAccountMoney());
        stream.setLeftMoney(vo.getLeftMoney());
        return stream;
    }

    /**
     * 将PO转换成VO
     *
     * @param stream
     * @return
     */
    private StreamVO makeStreamToVo(Stream stream) {
        StreamVO vo = new StreamVO();
        vo.setYear(stream.getYear());
        vo.setMonth(stream.getMonth());
        vo.setIncomeMoney(stream.getIncomeMoney());
        vo.setAccountMoney(stream.getAccountMoney());
        vo.setLeftMoney(stream.getLeftMoney());
        Date createTime = stream.getCreateTime();
        vo.setCreateTime(DateUtil.makeDate2Str(createTime, true));
        return vo;
    }
}
