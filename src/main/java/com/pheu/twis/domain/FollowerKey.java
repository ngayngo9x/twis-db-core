package com.pheu.twis.domain;

import javax.persistence.Column;

public class FollowerKey {
	@Column(name = "username")
	private String username;
	@Column(name = "follower")
	private String follower;

	public FollowerKey() {
	}

	public FollowerKey(String username, String follower) {
		this.username = username;
		this.follower = follower;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFollower() {
		return follower;
	}

	public void setFollower(String follower) {
		this.follower = follower;
	}

	@Override
	public String toString() {
		return "FollowerKey [username=" + username + ", follower=" + follower + "]";
	}

}
