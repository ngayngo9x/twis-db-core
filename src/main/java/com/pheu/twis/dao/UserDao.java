package com.pheu.twis.dao;

import com.google.common.base.Optional;
import com.pheu.twis.domain.User;

public interface UserDao {
	public boolean login(String username, String password);
	public boolean signup(String username, String password);
	public Optional<User> get(String username);
}
