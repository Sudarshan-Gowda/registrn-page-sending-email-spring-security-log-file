package com.star.sud.framework.service;

/*@Author Sudarshan*/
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.star.sud.framework.dao.StarUtilDao;

@Service("starUtilService")
public class StarUtilServiceImpl implements StarUtilService {

	@Resource(name = "starUtilDao")
	protected StarUtilDao starUtilDao;

	@Transactional
	public Object save(Object entity) {

		return starUtilDao.save(entity);
	}

	public <T> T createEntityObj(String entityClass, Class<T> type) {
		return starUtilDao.createEntityObj(entityClass, type);
	}

	 
}
