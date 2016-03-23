package com.pheu.core.casara.dao;

public interface GenericDao<T, PK> {
	void create(T newInstance);
	T get(PK id);
	void update(T instance);
	void delete(PK id);
}
