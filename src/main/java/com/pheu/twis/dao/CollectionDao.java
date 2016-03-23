package com.pheu.twis.dao;

import com.google.common.base.Optional;
import com.pheu.core.casara.PagingResult;

public interface CollectionDao<T> {
	public Optional<PagingResult<T>> list(String nextPaging, int limit);
}
