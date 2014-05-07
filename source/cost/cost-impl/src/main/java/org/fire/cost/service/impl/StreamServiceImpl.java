package org.fire.cost.service.impl;

import org.apache.poi.hssf.usermodel.*;
import org.fire.cost.dao.StreamDao;
import org.fire.cost.domain.Stream;
import org.fire.cost.service.StreamService;
import org.fire.cost.vo.StreamVO;
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
        vo.setCreateTime(new Date());
        return vo;
    }
}
