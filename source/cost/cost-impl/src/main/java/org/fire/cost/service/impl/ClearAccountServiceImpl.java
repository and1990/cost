package org.fire.cost.service.impl;

import org.fire.cost.dao.AccountDao;
import org.fire.cost.dao.ClearAccountDao;
import org.fire.cost.dao.GroupDao;
import org.fire.cost.domain.*;
import org.fire.cost.service.ClearAccountService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.ClearAccountVO;
import org.fire.cost.vo.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * 获取最后一次的结算记录
     *
     * @return
     */
    @Override
    public ClearAccountVO getLatestClearData() {
        ClearAccount clearAccount = clearAccountDao.getLatestClearData();
        ClearAccountVO clearAccountVO = makePoToVo(clearAccount);
        return clearAccountVO;
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
    public void clearData() {
        Map<Long, List<AccountVO>> accountDataMap = getAccountData();
        Set<Long> groupIdSet = accountDataMap.keySet();
        Iterable<Group> groups = groupDao.findAll(groupIdSet);
        Iterator<Group> groupIterator = groups.iterator();
        Map<Long, List<User>> userDataMap = new HashMap<Long, List<User>>();
        while (groupIterator.hasNext()) {
            Group group = groupIterator.next();
            List<GroupUser> groupUserList = group.getGroupUserList();
            List<User> userList = getUserList(groupUserList);
            Long groupId = group.getGroupId();
            userDataMap.put(groupId, userList);
        }

        Map<Long, List<ClearAccountDetail>> clearDataDetailMap = new HashMap<Long, List<ClearAccountDetail>>();
        groupIterator = groups.iterator();
        while (groupIterator.hasNext()) {
            Group group = groupIterator.next();
            Long groupId = group.getGroupId();
            List<AccountVO> accountVOList = accountDataMap.get(groupId);
            List<User> userList = userDataMap.get(groupId);
            BigDecimal totalMoney = getTotalAccountMoney(accountVOList);
            int size = userList.size();
            BigDecimal payMoney = totalMoney.divide(new BigDecimal(size), BigDecimal.ROUND_HALF_UP);
            List<ClearAccountDetail> detailList = new ArrayList<ClearAccountDetail>();
            for (User user : userList) {
                Long userId = user.getUserId();
                BigDecimal accountMoney = BigDecimal.ZERO;
                for (AccountVO accountVO : accountVOList) {
                    Long tempUserId = accountVO.getUserId();
                    if (userId.toString().equals(tempUserId.toString())) {
                        accountMoney = accountMoney.add(accountVO.getAccountMoney());
                    }
                }
                BigDecimal clearMoney = payMoney.divide(accountMoney);
                ClearAccountDetail detail = new ClearAccountDetail();
                detail.setUser(user);
                detail.setPayMoney(payMoney);
                detail.setAccountMoney(accountMoney);
                detail.setClearMoney(clearMoney);
                detail.setClearType(1);
                detail.setOverStatus(1);
                detailList.add(detail);
            }
            clearDataDetailMap.put(groupId, detailList);
        }

        List<User> allUserList = null;//TODO
        List<ClearAccountDetail> detailList = new ArrayList<ClearAccountDetail>();
        groupIterator = groups.iterator();
        for (User user : allUserList) {
            Long userId = user.getUserId();
            while (groupIterator.hasNext()) {
                Group group = groupIterator.next();
                List<ClearAccountDetail> details = clearDataDetailMap.get(group.getGroupId());
                for (ClearAccountDetail detail : details) {
                    Long tempUserId = detail.getUser().getUserId();
                    if (userId.toString().equals(tempUserId.toString())) {

                    }
                }
            }
        }

        //ClearAccount clearAccount = getClearAccount(accountVOList);
        //List<ClearAccountDetail> detailList = getClearAccountDetail(clearAccount, accountVOList);
    }

    private List<User> getUserList(List<GroupUser> groupUserList) {
        List<User> userList = new ArrayList<User>();
        boolean groupUserNotNull = groupUserList != null && groupUserList.size() != 0;
        if (groupUserNotNull) {
            for (GroupUser groupUser : groupUserList) {
                userList.add(groupUser.getUser());
            }
        }
        return userList;
    }

    /**
     * 获取消费数据
     *
     * @return
     */
    private Map<Long, List<AccountVO>> getAccountData() {
        ClearAccountVO clearAccountVO = getLatestClearData();
        String lastDate = clearAccountVO.getEndDate();
        String currentDate = DateUtil.makeDate2Str(Calendar.getInstance().getTime(), true);
        Map<Long, List<AccountVO>> listMap = accountDao.getAccountGroupByGroupAndUser(lastDate, currentDate);
        return listMap;
    }

    /**
     * 获取结算数据
     */
    private ClearAccount getClearAccount(List<AccountVO> accountVOList) {
        BigDecimal totalMoney = getTotalAccountMoney(accountVOList);
        ClearAccount clearAccount = new ClearAccount();
        clearAccount.setAccountMoney(totalMoney);
        ClearAccountVO clearAccountVO = getLatestClearData();
        String lastDate = clearAccountVO.getEndDate();
        String currentDate = DateUtil.makeDate2Str(Calendar.getInstance().getTime(), true);
        clearAccount.setStartDate(DateUtil.makeStr2Date(lastDate, true));
        clearAccount.setEndDate(DateUtil.makeStr2Date(currentDate, true));
        clearAccount.setCreateUser(AuthenticationUtil.getUserName());
        clearAccount.setCreateTime(new Date());
        return clearAccount;
    }

    /**
     * 获取消费总金额
     *
     * @param accountVOList
     * @return
     */
    private BigDecimal getTotalAccountMoney(List<AccountVO> accountVOList) {
        BigDecimal totalMoney = BigDecimal.ZERO;
        if (accountVOList != null && accountVOList.size() != 0) {
            for (AccountVO accountVO : accountVOList) {
                BigDecimal accountMoney = accountVO.getAccountMoney();
                totalMoney = totalMoney.add(accountMoney);
            }
        }
        return totalMoney;
    }

    /**
     * 获取结算明细记录
     *
     * @param clearAccount
     * @param accountVOList
     * @return
     */
    private List<ClearAccountDetail> getClearAccountDetail(ClearAccount clearAccount, List<AccountVO> accountVOList) {
        List<ClearAccountDetail> detailList = new ArrayList<ClearAccountDetail>();
        BigDecimal totalMoney = clearAccount.getAccountMoney();
        int size = accountVOList.size();
        if (accountVOList != null && size != 0) {
            BigDecimal payMoney = totalMoney.divide(new BigDecimal(size), BigDecimal.ROUND_HALF_UP);
            for (AccountVO accountVO : accountVOList) {
                ClearAccountDetail detail = new ClearAccountDetail();
                BigDecimal accountMoney = accountVO.getAccountMoney();
                detail.setAccountMoney(accountMoney);
                BigDecimal clearMoney = payMoney.subtract(accountMoney);
                detail.setClearMoney(clearMoney);
            }
        }
        return detailList;
    }

    /**
     * 将po转换成vo
     *
     * @param clearAccount
     * @return
     */
    private ClearAccountVO makePoToVo(ClearAccount clearAccount) {
        ClearAccountVO clearAccountVO = new ClearAccountVO();
        clearAccountVO.setClearAccountId(clearAccount.getClearAccountId());
        clearAccountVO.setAccountMoney(clearAccount.getAccountMoney());
        Date startDate = clearAccount.getStartDate();
        clearAccountVO.setStartDate(DateUtil.makeDate2Str(startDate, false));
        Date endDate = clearAccount.getEndDate();
        clearAccountVO.setEndDate(DateUtil.makeDate2Str(endDate, false));
        clearAccountVO.setCreateUser(clearAccount.getCreateUser());
        clearAccount.setCreateTime(clearAccount.getCreateTime());
        clearAccount.setClearAccountRemark(clearAccount.getClearAccountRemark());
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
