package com.pheu.twis.command.impl;

import java.util.Date;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.mapping.EntityTypeParser;
import com.datastax.driver.mapping.builder.MappingBuilder;
import com.datastax.driver.mapping.meta.EntityTypeMetadata;
import com.google.common.collect.Iterables;
import com.pheu.core.casara.CassandraSessionFactory;
import com.pheu.core.casara.PagingResult;
import com.pheu.core.casara.command.CollectionCommandUtil;
import com.pheu.core.casara.dao.GenericDaoCassandraImpl;
import com.pheu.twis.command.RelationshipCommand;
import com.pheu.twis.domain.Follower;
import com.pheu.twis.domain.FollowerKey;
import com.pheu.twis.domain.Friend;
import com.pheu.twis.domain.FriendKey;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

public class RelationshipCommandImpl extends GenericDaoCassandraImpl<Friend, FriendKey> implements RelationshipCommand {

	public RelationshipCommandImpl(CassandraSessionFactory factory) {
		super.sessionFactory = factory;
	}

	public boolean follow(String username, String friend, Date since) {

		connect().withBatch().save(new Friend(username, friend, since)).save(new Follower(friend, username, since))
				.execute();
		return true;
	}

	public boolean unfollow(String username, String friend) {

		connect().withBatch().delete(new FriendKey(username, friend)).delete(new FollowerKey(friend, username))
				.execute();
		return true;

	}

	public PagingResult<Follower> followers(String nextPaging, String username, int limit) {

		EntityTypeMetadata entity = EntityTypeParser.getEntityMetadata(Follower.class);

		BuiltStatement builtSt = select(Iterables.toArray(entity.getPkColumns(), String.class))
				.from(entity.getTableName()).where(eq(entity.getPkColumns().get(0), bindMarker()));

		PreparedStatement prepareSt = MappingBuilder.getOrPrepareStatement(connect().getSession(), builtSt,
				"RelationshipCommandImpl.followers");
		BoundStatement boundSt = prepareSt.bind(username);
		boundSt.setFetchSize(limit);
		
		return CollectionCommandUtil.listPaging(nextPaging, limit, connect(), Follower.class, "key", boundSt, entity);

	}

	public PagingResult<Friend> friends(String nextPaging, String username, int limit) {
		
		EntityTypeMetadata entity = EntityTypeParser.getEntityMetadata(Friend.class);

		BuiltStatement builtSt = select(Iterables.toArray(entity.getPkColumns(), String.class))
				.from(entity.getTableName()).where(eq(entity.getPkColumns().get(0), bindMarker()));

		PreparedStatement prepareSt = MappingBuilder.getOrPrepareStatement(connect().getSession(), builtSt,
				"RelationshipCommandImpl.friends");
		BoundStatement boundSt = prepareSt.bind(username);
		boundSt.setFetchSize(limit);
		
		return CollectionCommandUtil.listPaging(nextPaging, limit, connect(), Friend.class, "key", boundSt, entity);
	
	}
}
