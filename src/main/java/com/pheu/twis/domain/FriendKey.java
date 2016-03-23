package com.pheu.twis.domain;

import javax.persistence.Column;

public class FriendKey {
	@Column(name="username")
	private String username;
	@Column(name="friend")
	private String friend;
	
	public FriendKey() {}

	public FriendKey(String username, String friend) {
		this.username = username;
		this.friend = friend;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFriend() {
		return friend;
	}

	public void setFriend(String friend) {
		this.friend = friend;
	}

	@Override
	public String toString() {
		return "FriendKey [username=" + username + ", friend=" + friend + "]";
	}

	
	
}
