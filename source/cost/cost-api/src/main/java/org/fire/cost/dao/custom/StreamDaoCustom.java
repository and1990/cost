package org.fire.cost.dao.custom;

import org.fire.cost.domain.Stream;
import org.fire.cost.vo.StreamVO;

import java.util.List;

/**
 * 流水账dao扩展类
 *
 * @author liutengfei
 */
public interface StreamDaoCustom {
    List<StreamVO> getStreamGroupByMonth(int year);

}
