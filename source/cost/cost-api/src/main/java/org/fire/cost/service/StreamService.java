package org.fire.cost.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.fire.cost.vo.StreamDetailVO;
import org.fire.cost.vo.StreamVO;
import org.fire.cost.vo.TypeVo;

import java.util.List;
import java.util.Map;

/**
 * 流水账service接口
 *
 * @author liutengfei
 */
public interface StreamService {

    /**
     * 根据过滤条件查找流水账
     *
     * @param year
     * @return
     */
    List<StreamVO> getStreamByYear(int year);

    /**
     * 获取年份列表
     *
     * @return
     */
    List<TypeVo> getYears();

    /**
     * 同步流水数据
     *
     * @param year
     * @return
     */
    boolean synStreamData(int year);

    /**
     * 查询流水明细
     *
     * @param month
     * @return
     */
    List<StreamDetailVO> getStreamDetail(int year, int month);

    /**
     * 查找月份对应的流水账数据
     *
     * @param year
     * @return
     */
    Map<String, List<String>> getStreamGroupByMonth(int year);

    /**
     * 获取excel数据
     *
     * @return
     */
    HSSFWorkbook getExcelData(int year);
}
