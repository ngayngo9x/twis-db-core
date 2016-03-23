package com.pheu.core.casara.command;

import com.pheu.core.casara.PagingResult;

public interface CollectionCommand<T> {
	public PagingResult<T> listPaging(String nextPaging, int limit);
}
