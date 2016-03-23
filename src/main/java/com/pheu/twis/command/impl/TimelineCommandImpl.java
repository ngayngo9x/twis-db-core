package com.pheu.twis.command.impl;

import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.util.List;

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
import com.pheu.twis.command.TimelineCommand;
import com.pheu.twis.domain.TimeLine;
import com.pheu.twis.domain.TimeLineKey;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

public class TimelineCommandImpl extends GenericDaoCassandraImpl<TimeLine, TimeLineKey> implements TimelineCommand {

	/*public List<String> list(int limit) {
		return CollectionCommandUtil.list(limit, mappingSession, "TimelineCommandImpl.list", TimeLine.class);
	}

	public List<String> list(Date time, int limit) {
		return CollectionCommandUtil.list(time, "since", limit, mappingSession, "TimelineCommandImpl.list2", TimeLine.class);
	}
*/
	public TimelineCommandImpl(CassandraSessionFactory sessionFactory) {
		super.sessionFactory = sessionFactory;
	}
	
	public void batchCreate(List<TimeLine> items) {
		CollectionCommandUtil.batchCreate(items, connect());
	}

	public PagingResult<TimeLine> listPaging(String nextPaging, String username, int limit) {
		
		EntityTypeMetadata entity = EntityTypeParser.getEntityMetadata(TimeLine.class);

		BuiltStatement buildStmt = select(Iterables.toArray(entity.getPkColumns(), String.class))
				.from(entity.getTableName())
				.where(eq(entity.getPkColumns().get(0), bindMarker()));
		
		PreparedStatement prepared = MappingBuilder.getOrPrepareStatement(connect().getSession(), buildStmt,
				"TimelineCommandImpl.listPaging");
		BoundStatement st = prepared.bind(username);
		
		st.setFetchSize(limit);
		return CollectionCommandUtil.listPaging(nextPaging, limit, connect(), TimeLine.class, "key", st, entity);

	}

}
