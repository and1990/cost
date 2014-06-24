package org.fire.cost.dao;

import org.fire.cost.dao.custom.ClearAccountDetailDaoCustom;
import org.fire.cost.domain.ClearAccount;
import org.fire.cost.domain.ClearAccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 注释：结算明细Dao
 * 作者：liutengfei 【刘腾飞】
 * 时间：14-2-19 下午8:58
 */
public interface ClearAccountDetailDao extends JpaRepository<ClearAccountDetail, Long>, ClearAccountDetailDaoCustom {
    /**
     * 获取结算明细信息
     *
     * @param clearAccount
     * @return
     */
    List<ClearAccountDetail> findByClearAccount(ClearAccount clearAccount);

    /**
     * 更新结算明细结算状态
     *
     * @param overStatus
     */
    @Modifying
    @Query("update ClearAccountDetail set overStatus=?1 where clearAccountDetailId=?2")
    void updateClearDetail(int overStatus, Long clearAccountDetailId);
}
