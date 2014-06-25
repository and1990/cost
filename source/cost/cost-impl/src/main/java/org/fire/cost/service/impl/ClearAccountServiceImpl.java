package org.fire.cost.service.impl;

import org.fire.cost.dao.*;
import org.fire.cost.domain.*;
import org.fire.cost.enums.ClearResultEnum;
import org.fire.cost.enums.OverStatusEnum;
import org.fire.cost.enums.StatusEnum;
import org.fire.cost.service.ClearAccountService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 注释：结算service实现类
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-19 下午9:01
 */
@Service
public class ClearAccountServiceImpl implements ClearAccountService {
    @Resource
    private AccountDao accountDao;

    @Resource
    private GroupDao groupDao;

    @Resource
    private ClearAccountDao clearAccountDao;

    @Resource
    private ClearAccountDetailDao detailDao;

    @Resource
    private UserDao userDao;

    /**
     * 获取结算信息
     *
     * @return
     */
    @Override
    public List<ClearAccountVO> getClearData(PageData pageData) {
        List<ClearAccount> clearAccountList = clearAccountDao.getClearData(pageData);
        Map<Long, List<ClearAccountDetailVO>> clearDetailDataMap = getClearDetailDataMap(clearAccountList);
        List<ClearAccountVO> clearAccountVOList = new ArrayList<ClearAccountVO>();
        for (ClearAccount clearAccount : clearAccountList) {
            ClearAccountVO clearAccountVO = makePoToVo(clearAccount);
            setAllClear(clearAccountVO, clearDetailDataMap);
            clearAccountVOList.add(clearAccountVO);
        }
        return clearAccountVOList;
    }

    /**
     * 获取结算记录数
     *
     * @return
     */
    @Override
    public int getClearTotal() {
        int total = clearAccountDao.getClearTotal();
        return total;
    }

    /**
     * 获取结算明细信息
     *
     * @param clearAccountId
     * @return
     */
    @Override
    public List<ClearAccountDetailVO> getClearDetailData(Long clearAccountId) {
        List<ClearAccountDetailVO> detailVOList = new ArrayList<ClearAccountDetailVO>();
        ClearAccount clearAccount = clearAccountDao.findOne(clearAccountId);
        List<ClearAccountDetail> detailList = detailDao.findByClearAccount(clearAccount);
        for (ClearAccountDetail detail : detailList) {
            ClearAccountDetailVO detailVO = makeDetailPoToVo(detail);
            detailVOList.add(detailVO);
        }
        return detailVOList;
    }

    /**
     * 获取最后一次的结算日期
     *
     * @return
     */
    @Override
    public String getLatestClearDate() {
        String date = "";
        ClearAccount clearAccount = clearAccountDao.getLatestClearData();
        if (clearAccount == null) {
            date = "2013-12-1 00:00:00";
        } else {
            Date endDate = clearAccount.getEndDate();
            date = DateUtil.makeDate2Str(endDate, true);
        }
        return date;
    }

    /**
     * 结算
     *
     * @param clearAccountVO
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public void clearData(ClearAccountVO clearAccountVO) {
        //获取用户对应的结算明细
        List<ClearAccountDetailVO> detailVOList = getDetailList();
        //设置结算明细数据
        setClearAccountDetail(detailVOList, clearAccountVO);
        //获取结算数据
        ClearAccount clearAccount = getClearAccount(detailVOList, clearAccountVO);
        clearAccount = clearAccountDao.save(clearAccount);

        //保存明细数据
        List<ClearAccountDetail> detailList = new ArrayList<ClearAccountDetail>();
        for (ClearAccountDetailVO detailVO : detailVOList) {
            ClearAccountDetail detail = makeDetailVoToPo(null, detailVO);
            detailList.add(detail);
        }
        for (ClearAccountDetail detail : detailList) {
            detail.setClearAccount(clearAccount);
        }
        detailDao.save(detailList);
    }

    /**
     * 更新结算明细结算状态
     *
     * @param overStatus
     * @param clearAccountDetailId
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public void updateClearDetail(int overStatus, Long clearAccountDetailId) {
        detailDao.updateClearDetail(overStatus, clearAccountDetailId);
    }

    /**
     * 根据结算id批量获取结算明细记录
     *
     * @param clearAccountList
     * @return
     */
    private Map<Long, List<ClearAccountDetailVO>> getClearDetailDataMap(List<ClearAccount> clearAccountList) {
        Map<Long, List<ClearAccountDetailVO>> clearDataMap = new HashMap<Long, List<ClearAccountDetailVO>>();
        for (ClearAccount clearAccount : clearAccountList) {
            Long clearAccountId = clearAccount.getClearAccountId();
            List<ClearAccountDetailVO> detailVOList = getClearDetailData(clearAccountId);
            clearDataMap.put(clearAccountId, detailVOList);
        }
        return clearDataMap;
    }

    /**
     * 设置是否全部结算
     *
     * @param clearAccountVO
     * @param clearDetailDataMap
     */
    private void setAllClear(ClearAccountVO clearAccountVO, Map<Long, List<ClearAccountDetailVO>> clearDetailDataMap) {
        clearAccountVO.setAllClear(OverStatusEnum.Clear.getCode());
        clearAccountVO.setAllClearName(OverStatusEnum.Clear.getName());
        Long clearAccountId = clearAccountVO.getClearAccountId();
        List<ClearAccountDetailVO> detailVOList = clearDetailDataMap.get(clearAccountId);
        for (ClearAccountDetailVO detailVO : detailVOList) {
            Integer overStatus = detailVO.getOverStatus();
            if (overStatus == OverStatusEnum.Not_Clear.getCode()) {
                clearAccountVO.setAllClear(OverStatusEnum.Not_Clear.getCode());
                clearAccountVO.setAllClearName(OverStatusEnum.Not_Clear.getName());
            }
        }
    }

    /**
     * 获取用户对应的结算明细
     *
     * @return
     */
    private List<ClearAccountDetailVO> getDetailList() {
        UserVO userVO = new UserVO();
        userVO.setUserStatus(StatusEnum.Enable.getCode());
        userVO.setPage(false);
        List<User> allUserList = userDao.getUserByFilter(userVO, null);
        List<ClearAccountDetailVO> detailVOList = new ArrayList<ClearAccountDetailVO>();
        for (User user : allUserList) {
            ClearAccountDetailVO detailVO = new ClearAccountDetailVO();
            detailVO.setUserId(user.getUserId());
            detailVO.setPayMoney(BigDecimal.ZERO);
            detailVO.setAccountMoney(BigDecimal.ZERO);
            detailVO.setClearMoney(BigDecimal.ZERO);
            detailVO.setOverStatus(OverStatusEnum.Not_Clear.getCode());
            detailVOList.add(detailVO);
        }
        return detailVOList;
    }

    /**
     * 设置结算明细
     *
     * @param detailVoList
     * @param clearAccountVO
     */
    private void setClearAccountDetail(List<ClearAccountDetailVO> detailVoList, ClearAccountVO clearAccountVO) {
        String startDate = getStartDate(clearAccountVO);
        String endDate = getEndDate(clearAccountVO);
        Map<Long, List<AccountVO>> accountDataMap = accountDao.getAccountGroupByGroupAndUser(startDate, endDate);
        if (accountDataMap == null || accountDataMap.size() == 0) {
            return;
        }
        Set<Long> groupIdSet = accountDataMap.keySet();
        Iterable<Group> groups = groupDao.findAll(groupIdSet);
        Iterator<Group> groupIterator = groups.iterator();
        while (groupIterator.hasNext()) {
            Group group = groupIterator.next();
            List<AccountVO> accountVOList = accountDataMap.get(group.getGroupId());
            List<User> groupUserList = getGroupUserList(group);
            BigDecimal payMoney = getPayMoney(accountVOList, groupUserList);
            setPayMoney(detailVoList, groupUserList, payMoney);
            setAccountMoney(detailVoList, accountVOList);
        }
        setClearMoney(detailVoList);
    }


    /**
     * 获取组对应的用户
     *
     * @param group
     * @return
     */
    private List<User> getGroupUserList(Group group) {
        List<User> userList = new ArrayList<User>();
        List<GroupUser> groupUserList = group.getGroupUserList();
        boolean groupUserNotNull = groupUserList != null && groupUserList.size() != 0;
        if (groupUserNotNull) {
            for (GroupUser groupUser : groupUserList) {
                userList.add(groupUser.getUser());
            }
        }
        return userList;
    }

    /**
     * 获取需支付金额
     *
     * @return
     */
    private BigDecimal getPayMoney(List<AccountVO> accountVOList, List<User> groupUserList) {
        BigDecimal totalMoney = BigDecimal.ZERO;
        for (AccountVO accountVO : accountVOList) {
            BigDecimal accountMoney = accountVO.getAccountMoney();
            totalMoney = totalMoney.add(accountMoney);
        }
        BigDecimal userCount = new BigDecimal(groupUserList.size());
        BigDecimal payMoney = totalMoney.divide(userCount, BigDecimal.ROUND_HALF_UP);
        return payMoney;
    }

    /**
     * 设置应付金额
     *
     * @param detailList
     * @param groupUserList
     * @param payMoney
     */
    private void setPayMoney(List<ClearAccountDetailVO> detailList, List<User> groupUserList, BigDecimal payMoney) {
        for (ClearAccountDetailVO detailVo : detailList) {
            Long userId = detailVo.getUserId();
            for (User user : groupUserList) {
                Long id = user.getUserId();
                if (userId.toString().equals(id.toString())) {
                    BigDecimal money = detailVo.getPayMoney();
                    money = money.add(payMoney);
                    detailVo.setPayMoney(money);
                }
            }
        }
    }

    /**
     * 设置已付金额
     *
     * @param detailList
     * @param accountVOList
     */
    private void setAccountMoney(List<ClearAccountDetailVO> detailList, List<AccountVO> accountVOList) {
        for (ClearAccountDetailVO detailVo : detailList) {
            Long userId = detailVo.getUserId();
            for (AccountVO accountVO : accountVOList) {
                Long id = accountVO.getUserId();
                if (userId.toString().equals(id.toString())) {
                    BigDecimal accountMoney = detailVo.getAccountMoney();
                    accountMoney = accountMoney.add(accountVO.getAccountMoney());
                    detailVo.setAccountMoney(accountMoney);
                }
            }
        }
    }

    /**
     * 设置还需支付金额
     *
     * @param detailVoList
     */
    private void setClearMoney(List<ClearAccountDetailVO> detailVoList) {
        if (detailVoList == null || detailVoList.size() == 0) {
            return;
        }
        for (ClearAccountDetailVO detailVo : detailVoList) {
            BigDecimal payMoney = detailVo.getPayMoney();
            BigDecimal accountMoney = detailVo.getAccountMoney();
            BigDecimal clearMoney = payMoney.subtract(accountMoney);
            detailVo.setClearMoney(clearMoney);
        }
    }

    /**
     * 获取结算数据
     *
     * @param detailList
     * @param clearAccountVO
     */
    private ClearAccount getClearAccount(List<ClearAccountDetailVO> detailList, ClearAccountVO clearAccountVO) {
        BigDecimal totalMoney = BigDecimal.ZERO;
        for (ClearAccountDetailVO detailVo : detailList) {
            totalMoney = totalMoney.add(detailVo.getAccountMoney());
        }
        ClearAccount clearAccount = new ClearAccount();
        clearAccount.setAccountMoney(totalMoney);
        String startDate = getStartDate(clearAccountVO);
        String endDate = getEndDate(clearAccountVO);
        clearAccount.setStartDate(DateUtil.makeStr2Date(startDate, false));
        clearAccount.setEndDate(DateUtil.makeStr2Date(endDate, false));
        clearAccount.setCreateUser(AuthenticationUtil.getUserName());
        clearAccount.setCreateTime(new Date());
        return clearAccount;
    }

    /**
     * 获取结算开始日期
     *
     * @param clearAccountVO
     * @return
     */
    private String getStartDate(ClearAccountVO clearAccountVO) {
        String startDate = clearAccountVO.getStartDate();
        if (startDate == null || startDate.length() == 0) {
            startDate = getLatestClearDate();
        }
        return startDate;
    }

    /**
     * 获取结算截止日期
     *
     * @param clearAccountVO
     * @return
     */
    private String getEndDate(ClearAccountVO clearAccountVO) {
        String endDate = clearAccountVO.getEndDate();
        if (endDate == null || endDate.length() == 0) {
            Date currentDate = Calendar.getInstance().getTime();
            endDate = DateUtil.makeDate2Str(currentDate, true);
        }
        return endDate;
    }

    /**
     * 将po转换成vo
     *
     * @param clearAccount
     * @return
     */
    private ClearAccountVO makePoToVo(ClearAccount clearAccount) {
        ClearAccountVO clearAccountVO = new ClearAccountVO();
        if (clearAccount != null) {
            clearAccountVO.setClearAccountId(clearAccount.getClearAccountId());
            clearAccountVO.setAccountMoney(clearAccount.getAccountMoney());
            Date startDate = clearAccount.getStartDate();
            clearAccountVO.setStartDate(DateUtil.makeDate2Str(startDate, false));
            Date endDate = clearAccount.getEndDate();
            clearAccountVO.setEndDate(DateUtil.makeDate2Str(endDate, false));
            clearAccountVO.setCreateUser(clearAccount.getCreateUser());
            Date createTime = clearAccount.getCreateTime();
            clearAccountVO.setCreateTime(DateUtil.makeDate2Str(createTime, true));
            clearAccountVO.setClearAccountRemark(clearAccount.getClearAccountRemark());
        }
        return clearAccountVO;
    }

    /**
     * 将vo转成成po
     *
     * @param clearAccount
     * @param clearAccountVO
     * @return
     */
    private ClearAccount makeVoToPo(ClearAccount clearAccount, ClearAccountVO clearAccountVO) {
        if (clearAccount == null) {
            clearAccount = new ClearAccount();
            String userName = AuthenticationUtil.getUserName();
            clearAccount.setCreateUser(userName);
            clearAccount.setCreateTime(new Date());
        }
        clearAccount.setAccountMoney(clearAccountVO.getAccountMoney());
        String startDate = clearAccountVO.getStartDate();
        clearAccount.setStartDate(DateUtil.makeStr2Date(startDate, false));
        String endDate = clearAccountVO.getEndDate();
        clearAccount.setEndDate(DateUtil.makeStr2Date(endDate, false));
        clearAccount.setClearAccountRemark(clearAccountVO.getClearAccountRemark());
        return clearAccount;
    }

    /**
     * 将明细vo转换成po
     *
     * @param detail
     * @param detailVO
     * @return
     */
    private ClearAccountDetail makeDetailVoToPo(ClearAccountDetail detail, ClearAccountDetailVO detailVO) {
        if (detail == null) {
            detail = new ClearAccountDetail();
        }
        detail.setAccountMoney(detailVO.getAccountMoney());
        detail.setClearMoney(detailVO.getClearMoney());
        Integer clearResult = detailVO.getClearResult();
        if (clearResult == ClearResultEnum.Get.getCode()) {
            detail.setOverStatus(OverStatusEnum.Clear.getCode());
        } else {
            detail.setOverStatus(OverStatusEnum.Not_Clear.getCode());
        }
        Long userId = detailVO.getUserId();
        detail.setUser(userDao.findOne(userId));
        return detail;
    }

    /**
     * 将结算明细po转换成vo
     *
     * @return
     */
    private ClearAccountDetailVO makeDetailPoToVo(ClearAccountDetail detail) {
        ClearAccountDetailVO detailVO = new ClearAccountDetailVO();
        detailVO.setClearDetailId(detail.getClearAccountDetailId());
        detailVO.setClearAccountId(detail.getClearAccount().getClearAccountId());
        detailVO.setUserId(detail.getUser().getUserId());
        detailVO.setUserName(detail.getUser().getUserName());
        detailVO.setAccountMoney(detail.getAccountMoney());
        BigDecimal clearMoney = detail.getClearMoney();
        int clearType = ClearResultEnum.Pay.getCode();
        if (clearMoney.compareTo(BigDecimal.ZERO) < 0) {
            clearMoney = clearMoney.abs();
            clearType = ClearResultEnum.Get.getCode();
        }
        detailVO.setClearMoney(clearMoney);
        detailVO.setClearResult(clearType);
        detailVO.setClearResultName(ClearResultEnum.getName(clearType));
        Integer overStatus = detail.getOverStatus();
        detailVO.setOverStatus(overStatus);
        detailVO.setOverStatusName(OverStatusEnum.getName(overStatus));
        detailVO.setDetailRemark(detail.getDetailRemark());
        return detailVO;
    }
}
