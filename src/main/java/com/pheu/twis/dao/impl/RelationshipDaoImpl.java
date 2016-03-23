package com.pheu.twis.dao.impl;


import java.util.Date;
import java.util.logging.Logger;

import com.google.common.base.Optional;
import com.pheu.core.casara.PagingResult;
import com.pheu.twis.command.RelationshipCommand;
import com.pheu.twis.dao.RelationshipDao;
import com.pheu.twis.domain.Follower;
import com.pheu.twis.domain.Friend;

public class RelationshipDaoImpl implements RelationshipDao {

	private static Logger LOGGER = Logger.getLogger(RelationshipDaoImpl.class.getName());

	private RelationshipCommand relationshipCommand;
	
	public RelationshipDaoImpl(RelationshipCommand relationshipCommand) {
		this.relationshipCommand = relationshipCommand;
	}

	public boolean follow(String username, String friend, Date since) {
		try {
			relationshipCommand.follow(username, friend, since);
			return true;
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return false;
	}
	
	public boolean unfollow(String username, String friend) {
		try {
			relationshipCommand.unfollow(username, friend);
			return true;
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return false;
	}

	public RelationshipCommand getRelationshipCommand() {
		return relationshipCommand;
	}

	public void setRelationshipCommand(RelationshipCommand friendDao) {
		this.relationshipCommand = friendDao;
	}

	public Optional<PagingResult<Follower>> followers(String nextPaging, String username, int limit) {
		try {
			return Optional.fromNullable(relationshipCommand.followers(nextPaging, username, limit));
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return Optional.absent();
	}

	public Optional<PagingResult<Friend>> friends(String nextPaging, String username, int limit) {
		try {
			return Optional.fromNullable(relationshipCommand.friends(nextPaging, username, limit));
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return Optional.absent();
	}
}
