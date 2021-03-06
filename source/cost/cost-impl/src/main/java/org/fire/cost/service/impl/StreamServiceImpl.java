package org.fire.cost.service.impl;

import org.apache.poi.hssf.usermodel.*;
import org.fire.cost.dao.StreamDao;
import org.fire.cost.domain.Stream;
import org.fire.cost.service.AccountService;
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
            setTotalData(voList);
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
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = 11;
        if (currentYear == year) {
            currentMonth = calendar.get(Calendar.MONTH);
        }
        for (int month = 1; month <= currentMonth + 1; month++) {
            List<AccountVO> accountDataList = getAccountDataByMonth(year, month);
            BigDecimal totalAccountMoney = getTotalAccountMoney(accountDataList);
            Stream stream = new Stream();
            stream.setYear(year);
            stream.setMonth(month);
            stream.setAccountMoney(totalAccountMoney);
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
        List<StreamDetailVO> streamDetailVOList = new ArrayList<StreamDetailVO>();
        boolean accountNotNull = accountVOList != null && accountVOList.size() != 0;
        if (accountNotNull) {
            for (AccountVO accountVO : accountVOList) {
                StreamDetailVO detailVO = new StreamDetailVO();
                detailVO.setDate(accountVO.getAccountTime());
                detailVO.setMoney(accountVO.getAccountMoney());
                detailVO.setRemark(accountVO.getAccountRemark());
                streamDetailVOList.add(detailVO);
            }
        }
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
            List<String> accountList = getMoneyList(streamList);
            streamVoListMap.put("1", accountList);
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
    public HSSFWorkbook getExcelData(int year) {
        HSSFWorkbook hwb = new HSSFWorkbook();
        try {
            HSSFCellStyle style = hwb.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);

            HSSFSheet sheet = hwb.createSheet("流水账");
            for (int i = 0; i < 6; i++) {
                sheet.setColumnWidth(i, (short) 5000);
            }
            HSSFRow row = null;
            HSSFCell cell = null;
            createExcelTitle(sheet, style, row, cell);
            createExcelBody(sheet, style, row, cell, year);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hwb;
    }

    /**
     * 设置总计行
     *
     * @param voList
     */
    private void setTotalData(List<StreamVO> voList) {
        BigDecimal incomeTotal = BigDecimal.ZERO;
        BigDecimal accountTotal = BigDecimal.ZERO;
        for (StreamVO vo : voList) {
            BigDecimal accountMoney = vo.getAccountMoney();
            accountTotal = accountTotal.add(accountMoney);
        }
        BigDecimal leftMoney = incomeTotal.subtract(accountTotal);
        StreamVO streamVO = new StreamVO();
        streamVO.setMonthName("总计");
        streamVO.setAccountMoney(accountTotal);
        voList.add(streamVO);
    }


    /**
     * 获取金额
     *
     * @param streamList
     * @return
     */
    private List<String> getMoneyList(List<StreamVO> streamList) {
        int[] monthArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        List<String> moneyList = new ArrayList<String>();
        for (int month : monthArr) {
            String moneyStr = "0";
            for (StreamVO vo : streamList) {
                int streamMonth = vo.getMonth();
                if (month == streamMonth) {
                    BigDecimal money = vo.getAccountMoney();
                    moneyStr = money.toString();
                }
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
        cell.setCellValue("支出金额");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);
    }

    /**
     * 创建表体数据
     */
    private void createExcelBody(HSSFSheet sheet, HSSFCellStyle style, HSSFRow row, HSSFCell cell, int year) {
        int rowIndex = 1;
        List<StreamVO> streamVOList = getStreamByYear(year);
        for (StreamVO vo : streamVOList) {
            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            Long streamId = vo.getStreamId();
            if (streamId == null || streamId == 0) {
                cell.setCellValue("");
            } else {
                cell.setCellValue(streamId);
            }
            cell.setCellStyle(style);
            cell = row.createCell(1);
            Integer voYear = vo.getYear();
            if (voYear == null || voYear == 0) {
                cell.setCellValue("");
            } else {
                cell.setCellValue(voYear);
            }
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(vo.getMonthName());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(vo.getAccountMoney().toString());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            String createTime = vo.getCreateTime();
            if (createTime == null || createTime.length() == 0) {
                cell.setCellValue("");
            } else {
                cell.setCellValue(createTime);
            }
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
        stream.setAccountMoney(vo.getAccountMoney());
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
        vo.setStreamId(stream.getStreamId());
        vo.setYear(stream.getYear());
        Integer month = stream.getMonth();
        vo.setMonth(month);
        vo.setMonthName(month + "");
        vo.setAccountMoney(stream.getAccountMoney());
        Date createTime = stream.getCreateTime();
        vo.setCreateTime(DateUtil.makeDate2Str(createTime, true));
        return vo;
    }
}
