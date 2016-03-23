package com.pheu.twis.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

@Table(name = "friends")
public class Friend {

	@EmbeddedId
	private FriendKey key;
	@Column(name = "since")
	private Date since;

	public Friend() {}
	
	public Friend(String username, String friend, Date since) {
		this.key = new FriendKey();
		this.key.setUsername(username);
		this.key.setFriend(friend);
		this.since = since;
	}

	public FriendKey getKey() {
		return key;
	}

	public void setKey(FriendKey key) {
		this.key = key;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	@Override
	public String toString() {
		return "Friend [key=" + key + ", since=" + since + "]";
	}

	
	
}
