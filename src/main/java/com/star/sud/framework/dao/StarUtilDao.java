package com.star.sud.framework.dao;

/*@Author Sudarshan*/
public interface StarUtilDao {

	public Object save(Object entity);

	public Object create(String entityClass);

	public <T> T createEntityObj(String entityClass, Class<T> type);

}
