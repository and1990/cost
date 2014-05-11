package org.fire.cost.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.PageData;
import org.fire.cost.vo.TypeVo;

import java.util.List;
import java.util.Map;

/**
 * 账单service接口
 *
 * @author liutengfei
 */
public interface AccountService {

    /**
     * 根据过滤条件查找账单
     *
     * @param vo
     * @return
     */
    List<AccountVO> getAccountByFilter(AccountVO vo, PageData<AccountVO> pageData);

    /**
     * 查询账单总数据数
     *
     * @return
     */
    int getAccountTotal(AccountVO vo);

    /**
     * 增加账单
     *
     * @param vo
     * @return
     */
    boolean addAccount(AccountVO vo);

    /**
     * 修改账单
     *
     * @param vo
     * @return
     */
    boolean modifyAccount(AccountVO vo);

    /**
     * 删除账单
     *
     * @param accountId
     * @return
     */
    boolean deleteAccount(Long accountId);

    /**
     * 结算账单
     *
     * @param accountIds
     * @param accountStatus
     * @return
     */
    void approveAccount(String accountIds, Integer accountStatus);

    /**
     * 得到账单类型
     *
     * @param accountIds
     * @return
     */
    void clearAccount(String accountIds);

    /**
     * 获取消费种类
     *
     * @return
     */
    List<TypeVo> getAccountClass();

    /**
     * 得到账单类型
     *
     * @return
     * @param accountClass
     */
    List<TypeVo> getAccountType(int accountClass);

    /**
     * 得到结算方式
     *
     * @return
     */
    List<TypeVo> getClearType();


    /**
     * 查找消费类型对应的账单数据
     *
     *
     * @param accountStartTime 消费开始时间
     * @param accountEndTime   消费结束时间
     * @param accountClass
     * @return
     */
    List<AccountVO> getAccountGroupByAccountType(String accountStartTime, String accountEndTime, int accountClass);

    /**
     * 查找用户对应的账单数据
     *
     * @param accountStartTime 消费开始时间
     * @param accountEndTime   消费结束时间
     * @return
     */
    List<AccountVO> getAccountGroupByUser(String accountStartTime, String accountEndTime);

    /**
     * 获取用户每种消费类型消费金额
     *
     * @return
     */
    Map<String, List<AccountVO>> getAccountGroupByTypeAndUser(String startTime, String endTime);

    /**
     * 获取账单状态
     *
     * @return
     */
    List<TypeVo> getAccountStatus();

    /**
     * 获取excel数据
     *
     * @return
     */
    HSSFWorkbook getExcelData();

    /**
     * 获取每月投资数据
     *
     * @return
     */
    Map<String,List<AccountVO>> getInvestGroupByMonth(int year);
}
