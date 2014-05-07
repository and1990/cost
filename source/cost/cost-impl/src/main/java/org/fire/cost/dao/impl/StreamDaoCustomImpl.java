package org.fire.cost.dao.impl;

import org.fire.cost.dao.custom.StreamDaoCustom;
import org.fire.cost.domain.Stream;

import javax.persistence.Query;
import java.util.List;

/**
 * 注释：收入扩展实现类
 * 时间：2014年05月06日 下午3:11
 * 作者：刘腾飞
 */
public class StreamDaoCustomImpl extends BaseJpaDaoSupport<Stream, Long> implements StreamDaoCustom {
    @Override
    public List<Stream> getStreamGroupByMonth() {
        String sql = "";
        Query query = entityManager.createNativeQuery(sql, Stream.class);
        List<Stream> list = query.getResultList();
        return list;
    }
}
