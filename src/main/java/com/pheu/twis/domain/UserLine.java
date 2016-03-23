package com.pheu.twis.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import com.datastax.driver.mapping.annotation.TableProperties;
import com.datastax.driver.mapping.annotation.TableProperty;

@Table(name = "userline")
@TableProperties(values = { @TableProperty("CLUSTERING ORDER BY (time DESC)") })
public class UserLine {
	@EmbeddedId
	private UserLineKey key;
	@Column(name = "tweet_id")
	private String tweetId;

	public UserLine() {}
	
	public UserLine(String username, Date time, String tweetId) {
		this.key = new UserLineKey();
		this.key.setUsername(username);
		this.key.setTime(time);
		this.tweetId = tweetId;
	}

	public String getTweetId() {
		return tweetId;
	}

	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}

	public UserLineKey getKey() {
		return key;
	}

	public void setKey(UserLineKey key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "UserLine [key=" + key + ", tweetId=" + tweetId + "]";
	}

}
