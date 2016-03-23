package com.pheu.twis.command;

import java.util.Date;

import com.pheu.core.casara.PagingResult;
import com.pheu.core.casara.dao.GenericDao;
import com.pheu.twis.domain.Follower;
import com.pheu.twis.domain.Friend;
import com.pheu.twis.domain.FriendKey;

public interface RelationshipCommand extends GenericDao<Friend, FriendKey> {
	public boolean follow(String username, String friend, Date since);
	public boolean unfollow(String username, String friend);
	public PagingResult<Follower> followers(String nextPaging, String username, int limit);
	public PagingResult<Friend> friends(String nextPaging, String username, int limit);
}
