package com.pheu.twis.command.impl;

import com.pheu.core.casara.CassandraSessionFactory;
import com.pheu.core.casara.dao.GenericDaoCassandraImpl;
import com.pheu.twis.command.UserCommand;
import com.pheu.twis.domain.User;

public class UserCommandImpl extends GenericDaoCassandraImpl<User, String> implements UserCommand {
	public UserCommandImpl(CassandraSessionFactory factory) {
		super.sessionFactory = factory;
	}
}
