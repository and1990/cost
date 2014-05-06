package org.fire.cost.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.fire.cost.vo.IncomeVO;
import org.fire.cost.vo.PageData;
import org.fire.cost.vo.TypeVo;

import java.util.List;

/**
 * 收入service接口
 *
 * @author liutengfei
 */
public interface IncomeService {

    /**
     * 根据过滤条件查找收入
     *
     * @param vo
     * @return
     */
    List<IncomeVO> getIncomeByFilter(IncomeVO vo, PageData<IncomeVO> pageData);

    /**
     * 查询收入总数据数
     *
     * @return
     */
    int getIncomeTotal(IncomeVO vo);

    /**
     * 增加收入
     *
     * @param vo
     * @return
     */
    boolean addIncome(IncomeVO vo);

    /**
     * 修改收入
     *
     * @param vo
     * @return
     */
    boolean modifyIncome(IncomeVO vo);

    /**
     * 删除收入
     *
     * @param IncomeId
     * @return
     */
    boolean deleteIncome(Long IncomeId);

    /**
     * 得到收入类型
     *
     * @return
     */
    List<TypeVo> getIncomeType();

    /**
     * 查找消费类型对应的收入数据
     *
     * @param IncomeStartTime 消费开始时间
     * @param IncomeEndTime   消费结束时间
     * @return
     */
    List<IncomeVO> getIncomeGroupByIncomeType(String IncomeStartTime, String IncomeEndTime);


    /**
     * 查找用户对应的收入数据
     *
     * @param IncomeStartTime 消费开始时间
     * @param IncomeEndTime   消费结束时间
     * @return
     */
    List<IncomeVO> getIncomeGroupByUser(String IncomeStartTime, String IncomeEndTime);

    /**
     * 获取excel数据
     *
     * @return
     */
    HSSFWorkbook getExcelData();
}
