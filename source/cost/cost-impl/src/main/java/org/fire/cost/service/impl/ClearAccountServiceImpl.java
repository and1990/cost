package org.fire.cost.service.impl;

import org.fire.cost.dao.AccountDao;
import org.fire.cost.dao.ClearAccountDao;
import org.fire.cost.service.GroupAccountService;
import org.fire.cost.vo.ClearAccountVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 注释：结算service实现类
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-19 下午9:01
 */
@Service
public class ClearAccountServiceImpl implements GroupAccountService {

    @Resource
    private ClearAccountDao clearAccountDao;

    @Resource
    private AccountDao accountDao;

    /**
     * 根据查询条件查询组消费信息
     *
     * @param vo
     * @return
     */
    @Override
    public List<ClearAccountVO> getGroupAccountByFilter(ClearAccountVO vo) {
        return null;
    }

    /**
     * 添加组消费
     *
     * @return
     */
    @Override
    public boolean addGroupAccount(Long groupId) {
        return false;
    }
}
