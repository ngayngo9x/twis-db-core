package com.pheu.twis.dao;

import java.util.List;

import com.google.common.base.Optional;
import com.pheu.core.casara.PagingResult;
import com.pheu.twis.domain.UserLine;

public interface UserlineDao {
	public Optional<PagingResult<UserLine>> listPaging(String nextPaging, String username, int limit);
	public void batchCreate(List<UserLine> userlines);
}
