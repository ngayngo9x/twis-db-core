package com.pheu.twis.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Table(name = "tweets", indexes = { @Index(name = "since_idx", columnList = "since") })

public class Tweet {
	@Id
	@Column(name = "tweet_id")
	private String tweetId;
	@Column(name = "username")
	private String username;
	@Column(name = "body")
	private String body;
	@Column(name = "since")
	private Date since;

	public String getTweetId() {
		return tweetId;
	}

	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	@Override
	public String toString() {
		return "Tweet [tweetId=" + tweetId + ", username=" + username + ", body=" + body + ", since=" + since + "]";
	}

	

}
