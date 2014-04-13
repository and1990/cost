package org.fire.cost.service.impl;

import org.fire.cost.dao.AccountDao;
import org.fire.cost.dao.GroupAccountDao;
import org.fire.cost.dao.GroupDao;
import org.fire.cost.entity.Group;
import org.fire.cost.service.GroupAccountService;
import org.fire.cost.util.DateUtil;
import org.fire.cost.vo.AccountVO;
import org.fire.cost.vo.GroupAccountVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 注释：组消费service实现类
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-19 下午9:01
 */
@Service
public class GroupAccountServiceImpl implements GroupAccountService {

    //@Resource
    private GroupAccountDao groupAccountDao;

    @Resource
    private GroupDao groupDao;

    @Resource
    private AccountDao accountDao;

    /**
     * 根据查询条件查询组消费信息
     *
     * @param vo
     * @return
     */
    @Override
    public List<GroupAccountVO> getGroupAccountByFilter(GroupAccountVO vo) {
        return null;
    }

    /**
     * 添加组消费
     *
     * @return
     */
    @Override
    public boolean addGroupAccount(Long groupId) {
        //TODO
        Group group = groupDao.findGroupByStatus();
        Date modifyTime = group.getModifyTime();
        String startTime = DateUtil.makeDate2Str(modifyTime, false);
        String endTime = null;
        AccountVO vo = new AccountVO();
        vo.setStartTime(startTime);
        vo.setEndTime(endTime);
        accountDao.getAccountByFilter(vo, null);
        return false;
    }
}
