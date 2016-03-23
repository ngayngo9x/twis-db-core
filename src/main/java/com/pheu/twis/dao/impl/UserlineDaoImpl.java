package com.pheu.twis.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.base.Optional;
import com.pheu.core.casara.PagingResult;
import com.pheu.twis.command.UserlineCommand;
import com.pheu.twis.dao.UserlineDao;
import com.pheu.twis.domain.UserLine;

public class UserlineDaoImpl implements UserlineDao {

	private static Logger LOGGER = Logger.getLogger(UserlineDaoImpl.class.getName());
	
	private UserlineCommand userlineCommand;

	public UserlineDaoImpl(UserlineCommand userlineCommand) {
		this.userlineCommand = userlineCommand;
	}

	public Optional<PagingResult<UserLine>> listPaging(String nextPaging, String username, int limit) {
		try {
			return Optional.fromNullable(userlineCommand.listPaging(nextPaging, username, limit));
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return Optional.absent();
	}

	public void batchCreate(List<UserLine> userlines) {
		try {
			userlineCommand.batchCreate(userlines);
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
	}

}
