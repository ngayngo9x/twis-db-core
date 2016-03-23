package com.pheu.twis.dao;

import com.google.common.base.Optional;
import com.pheu.twis.domain.Tweet;

public interface TweetDao extends CollectionDao<Tweet> {
	public boolean post(Tweet tweet);
	public Optional<Tweet> get(String id);
}
