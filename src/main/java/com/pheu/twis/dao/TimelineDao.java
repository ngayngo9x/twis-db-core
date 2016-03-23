package com.pheu.twis.dao;

import java.util.List;

import com.google.common.base.Optional;
import com.pheu.core.casara.PagingResult;
import com.pheu.twis.domain.TimeLine;

public interface TimelineDao {
	public Optional<PagingResult<TimeLine>> listPaging(String nextPaging, String username, int limit);
	public void batchCreate(List<TimeLine> timelines);
}
