package com.star.sud.framework.dao;
/*@Author Sudarshan*/
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.star.sud.EntityConfiguration;

@Repository("starUtilDao")
public class StarUtilDaoImp implements StarUtilDao {

	@PersistenceContext(unitName = "star")
	private EntityManager em;

	@Resource(name = "starEntityConfiguration")
	protected EntityConfiguration entityConfiguration;

	public Object save(Object entity) {
		return em.merge(entity);
	}

	@SuppressWarnings("unchecked")
	public <T> T createEntityObj(String entityClass, Class<T> type) {
		return (T) create(entityClass);
	}

	public Object create(String entityClass) {
		return entityConfiguration.createEntityInstance(entityClass);

	}

}
