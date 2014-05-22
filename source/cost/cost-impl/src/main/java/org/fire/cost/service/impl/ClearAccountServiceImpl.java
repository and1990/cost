package org.fire.cost.service.impl;

import org.fire.cost.dao.AccountDao;
import org.fire.cost.dao.ClearAccountDao;
import org.fire.cost.dao.ClearAccountDetailDao;
import org.fire.cost.dao.GroupDao;
import org.fire.cost.domain.*;
import org.fire.cost.enums.OverStatusEnum;
import org.fire.cost.service.ClearAccountService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.ClearAccountVO;
import org.fire.cost.vo.PageData;
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

    /**
     * 获取结算信息
     *
     * @return
     */
    @Override
    public List<ClearAccountVO> getClearData(PageData pageData) {
        List<ClearAccount> clearAccountList = clearAccountDao.getClearData(pageData);
        List<ClearAccountVO> clearAccountVOList = new ArrayList<ClearAccountVO>();
        for (ClearAccount clearAccount : clearAccountList) {
            ClearAccountVO clearAccountVO = makePoToVo(clearAccount);
            clearAccountVOList.add(clearAccountVO);
        }
        return clearAccountVOList;
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
     * 结算
     *
     * @return
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = RollbackException.class)
    public void clearData() {
        //获取用户对应的结算明细
        List<ClearAccountDetail> detailList = getDetailList();
        //设置结算明细数据
        setClearAccountDetail(detailList);
        //获取结算数据
        ClearAccount clearAccount = getClearAccount(detailList);
        clearAccount = clearAccountDao.save(clearAccount);

        //保存明细数据
        for (ClearAccountDetail detail : detailList) {
            detail.setClearAccount(clearAccount);
        }
        detailDao.save(detailList);
    }

    /**
     * 获取用户对应的结算明细
     *
     * @return
     */
    private List<ClearAccountDetail> getDetailList() {
        Group group = groupDao.getGroupByType(2);
        List<User> allUserList = getUserList(group);
        List<ClearAccountDetail> detailList = new ArrayList<ClearAccountDetail>();
        for (User user : allUserList) {
            ClearAccountDetail detail = new ClearAccountDetail();
            detail.setUser(user);
            detail.setPayMoney(BigDecimal.ZERO);
            detail.setAccountMoney(BigDecimal.ZERO);
            detail.setClearMoney(BigDecimal.ZERO);
            detail.setOverStatus(OverStatusEnum.Not_Clear.getCode());
            detailList.add(detail);
        }
        return detailList;
    }

    /**
     * 设置结算明细
     *
     * @param detailList
     */
    private void setClearAccountDetail(List<ClearAccountDetail> detailList) {
        Map<Long, List<AccountVO>> accountDataMap = getAccountData();
        if (accountDataMap == null || accountDataMap.size() == 0) {
            return;
        }
        Set<Long> groupIdSet = accountDataMap.keySet();
        Iterable<Group> groups = groupDao.findAll(groupIdSet);
        Iterator<Group> groupIterator = groups.iterator();
        while (groupIterator.hasNext()) {
            Group group = groupIterator.next();
            List<AccountVO> accountVOList = accountDataMap.get(group.getGroupId());
            List<User> groupUserList = getUserList(group);
            BigDecimal payMoney = getPayMoney(accountVOList, groupUserList);
            setDetailAccountUser(detailList, accountVOList, groupUserList, payMoney);
            setDetailNotAccountUser(detailList, accountVOList, groupUserList, payMoney);

        }
    }


    /**
     * 获取消费数据
     *
     * @return
     */
    private Map<Long, List<AccountVO>> getAccountData() {
        String lastDate = getLatestClearDate();
        String currentDate = DateUtil.makeDate2Str(Calendar.getInstance().getTime(), true);
        Map<Long, List<AccountVO>> listMap = accountDao.getAccountGroupByGroupAndUser(lastDate, currentDate);
        return listMap;
    }

    /**
     * 获取组对应的用户
     *
     * @param group
     * @return
     */
    private List<User> getUserList(Group group) {
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
     * 设置已付金额
     *
     * @param detailList
     * @param accountVOList
     * @param groupUserList
     * @param payMoney
     */
    private void setDetailAccountUser(List<ClearAccountDetail> detailList, List<AccountVO> accountVOList, List<User> groupUserList, BigDecimal payMoney) {
        for (ClearAccountDetail detail : detailList) {
            User user = detail.getUser();
            Long userId = user.getUserId();
            for (AccountVO accountVO : accountVOList) {
                Long id = accountVO.getUserId();
                boolean equalUser = userId.toString().equals(id.toString());
                if (!equalUser) {
                    continue;
                }
                BigDecimal voMoney = accountVO.getAccountMoney();
                BigDecimal accountMoney = detail.getAccountMoney();
                detail.setAccountMoney(accountMoney.add(voMoney));
                if (isInCurrentGroup(accountVO, groupUserList)) {
                    BigDecimal clearMoney = detail.getClearMoney();
                    BigDecimal subMoney = payMoney.subtract(voMoney);
                    detail.setClearMoney(clearMoney.add(subMoney));
                }
            }
        }
    }

    private void setDetailNotAccountUser(List<ClearAccountDetail> detailList, List<AccountVO> accountVOList, List<User> groupUserList, BigDecimal payMoney) {
        List<User> notAccountUserList = new ArrayList<User>();
        for (User user : groupUserList) {
            Long userId = user.getUserId();
            boolean isContain = false;
            for (AccountVO accountVO : accountVOList) {
                Long accountVOUserId = accountVO.getUserId();
                if (userId.toString().equals(accountVOUserId.toString())) {
                    isContain = true;
                }
            }
            if (!isContain) {
                notAccountUserList.add(user);
            }
        }
        for (ClearAccountDetail detail : detailList) {
            Long userId = detail.getUser().getUserId();
            boolean isContain = false;
            for (User user : notAccountUserList) {
                Long id = user.getUserId();
                if (userId.toString().equals(id.toString())) {
                    isContain = true;
                }
            }
            if (isContain) {
                BigDecimal clearMoney = detail.getClearMoney();
                detail.setClearMoney(clearMoney.add(payMoney));
            }
        }
    }

    /**
     * 判断填写消费记录的用户是否在当前组里面
     *
     * @param accountVO
     * @param groupUserList
     * @return
     */
    private boolean isInCurrentGroup(AccountVO accountVO, List<User> groupUserList) {
        Long id = accountVO.getUserId();
        for (User user : groupUserList) {
            Long userId = user.getUserId();
            if (userId.toString().equals(id.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置还需支付金额
     *
     * @param detailList
     */
    private void setClearMoney(List<ClearAccountDetail> detailList) {
        if (detailList == null || detailList.size() == 0) {
            return;
        }
        for (ClearAccountDetail detail : detailList) {
            BigDecimal payMoney = detail.getPayMoney();
            BigDecimal accountMoney = detail.getAccountMoney();
            BigDecimal clearMoney = payMoney.subtract(accountMoney);
            detail.setClearMoney(clearMoney);
        }
    }

    /**
     * 获取结算数据
     */
    private ClearAccount getClearAccount(List<ClearAccountDetail> detailList) {
        BigDecimal totalMoney = BigDecimal.ZERO;
        for (ClearAccountDetail detail : detailList) {
            totalMoney = totalMoney.add(detail.getAccountMoney());
        }
        ClearAccount clearAccount = new ClearAccount();
        clearAccount.setAccountMoney(totalMoney);
        String lastDate = getLatestClearDate();
        String currentDate = DateUtil.makeDate2Str(Calendar.getInstance().getTime(), true);
        clearAccount.setStartDate(DateUtil.makeStr2Date(lastDate, true));
        clearAccount.setEndDate(DateUtil.makeStr2Date(currentDate, true));
        clearAccount.setCreateUser(AuthenticationUtil.getUserName());
        clearAccount.setCreateTime(new Date());
        return clearAccount;
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
            clearAccount.setCreateTime(clearAccount.getCreateTime());
            clearAccount.setClearAccountRemark(clearAccount.getClearAccountRemark());
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
}
