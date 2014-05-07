package org.fire.cost.dao;

import org.fire.cost.dao.custom.StreamDaoCustom;
import org.fire.cost.domain.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 流水账dao
 *
 * @author liutengfei
 */
public interface StreamDao extends JpaRepository<Stream, Long>, StreamDaoCustom {

}
