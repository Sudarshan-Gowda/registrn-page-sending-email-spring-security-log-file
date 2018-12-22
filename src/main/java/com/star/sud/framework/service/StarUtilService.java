package com.star.sud.framework.service;

/*@Author Sudarshan*/
public interface StarUtilService {

	public Object save(Object entity);

	public <T> T createEntityObj(String entityClass, Class<T> type);

}
