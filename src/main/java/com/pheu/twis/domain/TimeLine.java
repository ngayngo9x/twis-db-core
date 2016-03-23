package com.pheu.twis.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import com.datastax.driver.mapping.annotation.TableProperties;
import com.datastax.driver.mapping.annotation.TableProperty;

@Table(name = "timeline")
@TableProperties(values = { @TableProperty("CLUSTERING ORDER BY (time DESC)") })
public class TimeLine {

	@EmbeddedId
	private TimeLineKey key;
	@Column(name = "tweet_id")
	private String tweetId;

	public TimeLine() {
	}

	public TimeLine(String username, Date since, String tweetId) {
		this.key = new TimeLineKey();
		this.key.setUsername(username);
		this.key.setTime(since);
		this.tweetId = tweetId;
	}

	public TimeLineKey getKey() {
		return key;
	}

	public void setKey(TimeLineKey key) {
		this.key = key;
	}

	public String getTweetId() {
		return tweetId;
	}

	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}

	@Override
	public String toString() {
		return "TimeLine [key=" + key + ", tweetId=" + tweetId + "]";
	}

}
