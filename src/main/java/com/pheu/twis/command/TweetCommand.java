package com.pheu.twis.command;

import java.util.List;

import com.pheu.core.casara.command.CollectionCommand;
import com.pheu.core.casara.dao.GenericDao;
import com.pheu.twis.domain.Tweet;

public interface TweetCommand extends GenericDao<Tweet, String>, CollectionCommand<Tweet> {
	public List<String> list(int limit);
}
