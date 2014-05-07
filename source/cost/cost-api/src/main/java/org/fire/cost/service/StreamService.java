package org.fire.cost.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.fire.cost.vo.StreamVO;

import java.util.List;

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
     * 查找月份对应的流水账数据
     *
     * @return
     */
    List<StreamVO> getStreamGroupByMonth();

    /**
     * 获取excel数据
     *
     * @return
     */
    HSSFWorkbook getExcelData();
}
