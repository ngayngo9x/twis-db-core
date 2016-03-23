package com.pheu.twis.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

@Table(name="followers")
public class Follower {

	@EmbeddedId
	private FollowerKey key;

	@Column(name="since")
	private Date since;

	public Follower() {}
	
	public Follower(String username, String follower, Date since) {
		this.key = new FollowerKey();
		this.key.setUsername(username);
		this.key.setFollower(follower);
		this.since = since;
	}
	public FollowerKey getKey() {
		return key;
	}
	public void setKey(FollowerKey key) {
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
		return "Follower [key=" + key + ", since=" + since + "]";
	}
	
}
