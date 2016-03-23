package com.pheu.twis.dao;

import java.util.Date;

import com.google.common.base.Optional;
import com.pheu.core.casara.PagingResult;
import com.pheu.twis.domain.Follower;
import com.pheu.twis.domain.Friend;

public interface RelationshipDao {
	public boolean follow(String username, String friend, Date since);
	public boolean unfollow(String username, String friend);
	public Optional<PagingResult<Follower>> followers(String nextPaging, String username, int limit);
	public Optional<PagingResult<Friend>> friends(String nextPaging, String username, int limit);
}
