package by.testrest.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

public class BaseAbstractDao {

	public BaseAbstractDao(){}
	
		
	protected Object getEntityById(SessionFactory sessionFactory, Class<?> clazz, Serializable id) {
		return sessionFactory.getCurrentSession().get(clazz, id);
	}

	protected Long saveEntity(SessionFactory sessionFactory, Object entity) {
		return (Long)sessionFactory.getCurrentSession().save(entity);
	}
	
	protected void updateEntity(SessionFactory sessionFactory, Object entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	protected void deleteEntity(SessionFactory sessionFactory, Object entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

	protected void deleteEntityById(SessionFactory sessionFactory, Class<?> clazz, Serializable id) {
		Object entity = getEntityById(sessionFactory, clazz, id);
		if (entity != null) {
			deleteEntity(sessionFactory, entity);
		}
	}
	
	protected List<?> findAll(SessionFactory sessionFactory, Class<?> clazz, String orderBy) {
		return sessionFactory.getCurrentSession().createQuery("from "+clazz.getName()+" order by "+orderBy+" asc").list();
	}
}
