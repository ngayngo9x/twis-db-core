package com.pheu.core.casara.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.datastax.driver.mapping.MappingSession;
import com.pheu.core.casara.CassandraSessionFactory;

public class GenericDaoCassandraImpl<T, PK> implements GenericDao<T, PK> {

	protected CassandraSessionFactory sessionFactory;
	protected MappingSession mappingSession;
	
	private Class<T> entityType;
	private Class<PK> keyType;

	private ParameterizedType pt;

	public void create(T newInstance) {
		connect().save(newInstance);
	}

	public T get(PK id) {
		return connect().get(getEntityType(), id);
	}

	public void update(T instance) {
		connect().save(instance);
	}

	public void delete(PK id) {
		connect().delete(getEntityType(), id);
	}

	public CassandraSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(CassandraSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected MappingSession connect() {
		if (mappingSession != null) {
			return mappingSession;
		}
		mappingSession = new MappingSession(sessionFactory.getKeyspace(), sessionFactory.connect());
		return mappingSession;
	}

	private void getParameterizedType() {
		if (pt == null) {
			Type t = getClass().getGenericSuperclass();
			pt = (ParameterizedType) t;
		}
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getEntityType() {
		if (entityType == null) {
			getParameterizedType();
			entityType = (Class<T>) pt.getActualTypeArguments()[0];
		}
		return entityType;
	}

	@SuppressWarnings("unchecked")
	protected Class<PK> getKeyType() {
		if (keyType == null) {
			getParameterizedType();
			keyType = (Class<PK>) pt.getActualTypeArguments()[1];
		}
		return keyType;
	}

}
