package com.pheu.twis.dao.impl;

import java.util.logging.Logger;

import com.google.common.base.Optional;
import com.pheu.twis.command.UserCommand;
import com.pheu.twis.dao.UserDao;
import com.pheu.twis.domain.User;

public class UserDaoImpl implements UserDao {

	private static Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getName());

	private UserCommand userCommand;

	public UserDaoImpl(UserCommand userCommand) {
		this.userCommand = userCommand;
	}
	
	public boolean login(String username, String password) {
		try {
			User user = userCommand.get(username);
			if (user.getPassword().equals(password)) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return false;
	}

	public boolean signup(String username, String password) {
		try {
			userCommand.create(new User(username, password));
			return true;
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return false;
	}
	
	public Optional<User> get(String username) {
		try {
			return Optional.fromNullable(userCommand.get(username));
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return Optional.absent();
	}

	public UserCommand getUserCommand() {
		return userCommand;
	}

	public void setUserCommand(UserCommand usersDao) {
		this.userCommand = usersDao;
	}

}
