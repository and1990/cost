package org.fire.cost.dao;

import org.fire.cost.dao.custom.StreamDaoCustom;
import org.fire.cost.domain.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 流水账dao
 *
 * @author liutengfei
 */
public interface StreamDao extends JpaRepository<Stream, Long>, StreamDaoCustom {
    /**
     * 查询月份对应的流水账
     *
     * @return
     */
    List<Stream> findStreamByYear(int year);
}
