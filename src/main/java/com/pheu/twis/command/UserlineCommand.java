package com.pheu.twis.command;

import com.pheu.core.casara.PagingResult;
import com.pheu.core.casara.command.BatchCommand;
import com.pheu.core.casara.dao.GenericDao;
import com.pheu.twis.domain.UserLine;
import com.pheu.twis.domain.UserLineKey;

public interface UserlineCommand extends GenericDao<UserLine, UserLineKey>, BatchCommand<UserLine> {
	public PagingResult<UserLine> listPaging(String nextPaging, String username, int limit);
}
