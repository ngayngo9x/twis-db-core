package com.pheu.twis.command;

import com.pheu.core.casara.PagingResult;
import com.pheu.core.casara.command.BatchCommand;
import com.pheu.core.casara.dao.GenericDao;
import com.pheu.twis.domain.TimeLine;
import com.pheu.twis.domain.TimeLineKey;

public interface TimelineCommand extends GenericDao<TimeLine, TimeLineKey>, BatchCommand<TimeLine> {
	public PagingResult<TimeLine> listPaging(String nextPaging, String username, int limit);
}
