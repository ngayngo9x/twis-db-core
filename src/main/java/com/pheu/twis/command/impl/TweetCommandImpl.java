package com.pheu.twis.command.impl;

import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.mapping.EntityTypeParser;
import com.datastax.driver.mapping.builder.MappingBuilder;
import com.datastax.driver.mapping.meta.EntityTypeMetadata;
import com.pheu.core.casara.CassandraSessionFactory;
import com.pheu.core.casara.PagingResult;
import com.pheu.core.casara.command.CollectionCommandUtil;
import com.pheu.core.casara.dao.GenericDaoCassandraImpl;
import com.pheu.twis.command.TweetCommand;
import com.pheu.twis.domain.Tweet;

public class TweetCommandImpl extends GenericDaoCassandraImpl<Tweet, String> implements TweetCommand {

	public TweetCommandImpl(CassandraSessionFactory sessionFactory) {
		super.sessionFactory = sessionFactory;
	}

	public List<String> list(int limit) {
		
		List<String> result = new ArrayList<String>();

		EntityTypeMetadata entity = EntityTypeParser.getEntityMetadata(Tweet.class);
		BuiltStatement buildStmt = select(entity.getPkDefinition()).from(entity.getTableName()).limit(bindMarker());

		PreparedStatement prepare = MappingBuilder.getOrPrepareStatement(connect().getSession(), buildStmt,
				"TweetsCommandImpl.list");

		ResultSet rs = connect().getSession().execute(prepare.bind(limit));
		for (Row row : rs) {
			result.add(row.getString(entity.getPkDefinition()));
		}

		return result;
	
	}

	public PagingResult<Tweet> listPaging(String pagingState, int limit) {
		
		EntityTypeMetadata entity = EntityTypeParser.getEntityMetadata(Tweet.class);
		
		BuiltStatement buildStmt = select(entity.getPkDefinition())
				.from(entity.getTableName());
		PreparedStatement prepared = MappingBuilder.getOrPrepareStatement(connect().getSession(), buildStmt,
				"TweetsCommandImpl.listPaging");
		BoundStatement st = prepared.bind();
		st.setFetchSize(limit);
		
		return CollectionCommandUtil.listPaging(pagingState, limit, connect(), Tweet.class, "tweetId", st, entity);
	
	}

	/*public PagingResult<String> listPaging(String pagingState, int limit) {
		List<String> ids = new ArrayList<String>();

		EntityTypeMetadata entity = EntityTypeParser.getEntityMetadata(Tweet.class);

		BuiltStatement buildStmt = select(entity.getPkDefinition()).from(entity.getTableName());
		PreparedStatement prepared = MappingBuilder.getOrPrepareStatement(mappingSession.getSession(), buildStmt,
				"TweetsCommandImpl.listPaging");
		BoundStatement st = prepared.bind();
		st.setFetchSize(limit);
		
		if (pagingState != null) {
			st.setPagingState(PagingState.fromString(pagingState));
		}

		ResultSet rs = mappingSession.getSession().execute(st);
		PagingState pagingStateNext = rs.getExecutionInfo().getPagingState();
		
		int remaining = rs.getAvailableWithoutFetching();
		for (Row row : rs) {
			ids.add(row.getString(entity.getPkDefinition()));
			if (--remaining == 0) {
				break;
			}
		}

		PagingResult<String> result = new PagingResult<String>();
		result.setNextPage(pagingStateNext.toString());
		result.setIds(ids);
		return result;
	}*/
	
}
