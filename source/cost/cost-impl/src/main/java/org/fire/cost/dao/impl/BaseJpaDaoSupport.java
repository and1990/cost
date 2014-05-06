package org.fire.cost.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * jpa dao支持的基础类
 *
 * @param <T>
 * @param <ID>
 */
public class BaseJpaDaoSupport<T extends Serializable, ID extends Serializable> {

    @PersistenceContext
    protected EntityManager entityManager;
}
