package org.fire.cost.service.impl;

import org.fire.cost.dao.ClearAccountDao;
import org.fire.cost.domain.ClearAccount;
import org.fire.cost.service.ClearAccountService;
import org.fire.cost.util.AuthenticationUtil;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.ClearAccountVO;
import org.fire.cost.vo.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 注释：结算service实现类
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-19 下午9:01
 */
@Service
public class ClearAccountServiceImpl implements ClearAccountService {

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
     * 获取结算记录数
     *
     * @return
     */
    @Override
    public int getClearTotal() {
        int total = clearAccountDao.getClearTotal();
        return total;
    }

    @Override
    public void clearData() {

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
