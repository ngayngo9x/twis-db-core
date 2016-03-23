package com.pheu.core.casara.command;

import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;

import static com.datastax.driver.core.querybuilder.QueryBuilder.gt;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.mapping.EntityTypeParser;
import com.datastax.driver.mapping.MappingSession;
import com.datastax.driver.mapping.MappingSession.BatchExecutor;
import com.datastax.driver.mapping.builder.MappingBuilder;
import com.datastax.driver.mapping.meta.EntityFieldMetaData;
import com.datastax.driver.mapping.meta.EntityTypeMetadata;
import com.datastax.driver.mapping.meta.PrimaryKeyMetadata;
import com.pheu.core.casara.PagingResult;

public class CollectionCommandUtil {

	public static List<String> list(int limit, MappingSession mappingSession, String cacheKey, Class<?> entityClass) {
		List<String> result = new ArrayList<String>();

		EntityTypeMetadata entity = EntityTypeParser.getEntityMetadata(entityClass);

		PreparedStatement prepare = MappingBuilder.getOrPrepareStatement(mappingSession.getSession(),
				select(entity.getPkDefinition()).from(entity.getTableName()).limit(bindMarker()), cacheKey);

		ResultSet rs = mappingSession.getSession().execute(prepare.bind(limit));
		for (Row row : rs) {
			result.add(row.getString(entity.getPkDefinition()));
		}

		return result;
	}

	public static List<String> list(Date time, String timeProperty, int limit, MappingSession mappingSession,
			String cacheKey, Class<?> entityClass) {
		List<String> result = new ArrayList<String>();

		EntityTypeMetadata entity = EntityTypeParser.getEntityMetadata(entityClass);
		BuiltStatement buildStmt = select(entity.getPkDefinition()).from(entity.getTableName())
				.where(gt(entity.getFieldMetadata(timeProperty).getColumnName(), bindMarker())).limit(bindMarker());

		PreparedStatement prepare = MappingBuilder.getOrPrepareStatement(mappingSession.getSession(), buildStmt,
				cacheKey);
		prepare.bind(time, limit);

		ResultSet rs = mappingSession.getSession().execute(prepare.bind());
		for (Row row : rs) {
			result.add(row.getString(entity.getPkDefinition()));
		}

		return result;
	}

	public static <T> PagingResult<T> listPaging(String pagingState, int limit, MappingSession mappingSession,
			Class<T> entityClass, BoundStatement st, EntityTypeMetadata entity) {
		List<T> ids = new ArrayList<T>();

		if (pagingState != null) {
			st.setPagingState(PagingState.fromString(pagingState));
		}

		ResultSet rs = mappingSession.getSession().execute(st);
		PagingState pagingStateNext = rs.getExecutionInfo().getPagingState();

		int remaining = rs.getAvailableWithoutFetching();
		for (Row row : rs) {
			T t = mappingSession.getFromRow(entityClass, row);
			ids.add(t);
			if (--remaining == 0) {
				break;
			}
		}

		PagingResult<T> result = new PagingResult<T>();
		if (pagingStateNext != null) {
			result.setNextPage(pagingStateNext.toString());
		}
		result.setIds(ids);
		return result;
	}
	
	public static <T> PagingResult<T> listPaging(String pagingState, int limit, MappingSession mappingSession,
			Class<T> entityClass, String primaryField, BoundStatement st, EntityTypeMetadata entity) {
		List<T> ids = new ArrayList<T>();

		if (pagingState != null) {
			st.setPagingState(PagingState.fromString(pagingState));
		}

		ResultSet rs = mappingSession.getSession().execute(st);
		PagingState pagingStateNext = rs.getExecutionInfo().getPagingState();

		int remaining = rs.getAvailableWithoutFetching();
		for (Row row : rs) {
			try {
				T t = primaryKeyFromRow(primaryField, entityClass, entity, row);
				ids.add(t);
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
			if (--remaining == 0) {
				break;
			}
		}

		PagingResult<T> result = new PagingResult<T>();
		if (pagingStateNext != null) {
			result.setNextPage(pagingStateNext.toString());
		}
		result.setIds(ids);
		return result;
	}

	private static <T> T primaryKeyFromRow(String keyField, Class<T> entityClass, EntityTypeMetadata entity, Row row)
			throws InstantiationException, IllegalAccessException {
		// create PK

		T t = entityClass.newInstance();
		try {
			Object primaryKey = null;
			Object partitionKey = null;
			PrimaryKeyMetadata pkmeta = entity.getPrimaryKeyMetadata();
			if (pkmeta.isCompound()) {
				EntityFieldMetaData pkField = pkmeta.getOwnField();
				primaryKey = pkField.getType().newInstance();
				pkField.setValue(t, primaryKey);
				if (pkmeta.hasPartitionKey()) {
					PrimaryKeyMetadata partmeta = pkmeta.getPartitionKey();
					EntityFieldMetaData partField = partmeta.getOwnField();
					partitionKey = partField.getType().newInstance();
					partField.setValue(primaryKey, partitionKey);
				}
				List<EntityFieldMetaData> fields = pkmeta.getFields();
				for (EntityFieldMetaData field : fields) {
					Object value = MappingBuilder.getValueFromRow(row, field);
					if (value != null) {
						if (field.isPartition()) {
							field.setValue(partitionKey, value);
						} else if (field.isPrimary()) {
							field.setValue(primaryKey, value);
						}
					}
				}
			} else {
				EntityFieldMetaData field = entity.getFieldMetadata(keyField);
				Object value = MappingBuilder.getValueFromRow(row, field);
				field.setValue(t, value);
			}
		} catch (Exception e) {
			// skip error to support any-2-any
		}

		return t;
	}

	public static <T> void batchCreate(List<T> items, MappingSession mappingSession) {
		BatchExecutor batch = mappingSession.withBatch();
		for (T item : items) {
			batch = batch.save(item);
		}
		batch.execute();
	}

}
