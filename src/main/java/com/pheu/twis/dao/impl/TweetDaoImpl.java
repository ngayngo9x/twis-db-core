package com.pheu.twis.dao.impl;

import java.util.logging.Logger;

import com.google.common.base.Optional;
import com.pheu.core.casara.PagingResult;
import com.pheu.twis.command.TweetCommand;
import com.pheu.twis.dao.TweetDao;
import com.pheu.twis.domain.Tweet;

public class TweetDaoImpl implements TweetDao {

	private static Logger LOGGER = Logger.getLogger(TweetDaoImpl.class.getName());

	private TweetCommand tweetsCommand;

	public TweetDaoImpl(TweetCommand tweetCommand) {
		this.tweetsCommand = tweetCommand;
	}
	
	public boolean post(Tweet tweet) {
		try {
			tweetsCommand.create(tweet);
			return true;
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return false;
	}

	public Optional<PagingResult<Tweet>> list(String nextPaging, int limit) {
		try {
			return Optional.fromNullable(tweetsCommand.listPaging(nextPaging, limit));
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return Optional.absent();
	}

	public TweetCommand getTweetsDao() {
		return tweetsCommand;
	}

	public void setTweetCommand(TweetCommand tweetsDao) {
		this.tweetsCommand = tweetsDao;
	}

	public Optional<Tweet> get(String id) {
		try {
			return Optional.fromNullable(tweetsCommand.get(id));
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return Optional.absent();
	}

}
