package com.pheu.twis.command.impl;

import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.util.List;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.mapping.EntityTypeParser;
import com.datastax.driver.mapping.builder.MappingBuilder;
import com.datastax.driver.mapping.meta.EntityTypeMetadata;
import com.pheu.core.casara.CassandraSessionFactory;
import com.pheu.core.casara.PagingResult;
import com.pheu.core.casara.command.CollectionCommandUtil;
import com.pheu.core.casara.dao.GenericDaoCassandraImpl;
import com.pheu.twis.command.UserlineCommand;
import com.pheu.twis.domain.UserLine;
import com.pheu.twis.domain.UserLineKey;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

public class UserlineCommandImpl extends GenericDaoCassandraImpl<UserLine, UserLineKey> implements UserlineCommand {

	public UserlineCommandImpl(CassandraSessionFactory sessionFactory) {
		super.sessionFactory = sessionFactory;
	}
	
	/*
	 * public List<String> list(int limit) { return
	 * CollectionCommandUtil.list(limit, connect(), "UserlineCommandImpl.list",
	 * UserLine.class); }
	 * 
	 * public List<String> list(Date time, int limit) { return
	 * CollectionCommandUtil.list(time, "since", limit, connect(),
	 * "UserlineCommandImpl.list2", UserLine.class); }
	 */

	public void batchCreate(List<UserLine> items) {
		CollectionCommandUtil.batchCreate(items, connect());
	}

	public PagingResult<UserLine> listPaging(String nextPaging, String username, int limit) {
		
		EntityTypeMetadata entity = EntityTypeParser.getEntityMetadata(UserLine.class);
		
		BuiltStatement buildStmt = select().from(entity.getTableName())
				.where(eq(entity.getPkColumns().get(0), bindMarker()));
		
		PreparedStatement prepared = MappingBuilder.getOrPrepareStatement(connect().getSession(), buildStmt,
				"UserlineCommandImpl.listPaging");
		BoundStatement st = prepared.bind(username);
		st.setFetchSize(limit);

		return CollectionCommandUtil.listPaging(nextPaging, limit, connect(), UserLine.class, st, entity);
	
	}

}
