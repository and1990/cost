package org.fire.cost.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * jpa dao支持的基础类
 * 
 * @param <T>
 * @param <ID>
 * @author hanya
 */
public class BaseJpaDaoSupport<T extends Serializable, ID extends Serializable>
{

	@PersistenceContext
	protected EntityManager entityManager;

	/**
	 * 查询数据库返回集合
	 * 
	 * @param sqlString
	 *            查询语句
	 * @param paramList
	 *            查询所需参数
	 * @return List<T> 数据集合
	 * @author tiandaiyin
	 */
	protected List<T> executeQuery(String sqlString, List<Object> paramList)
	{

		Query dataQuery = this.entityManager.createQuery(sqlString);
		for (int i = 0; i < paramList.size(); i++)
		{
			dataQuery.setParameter(i + 1, paramList.get(i));
		}
		return dataQuery.getResultList();
	}

}
