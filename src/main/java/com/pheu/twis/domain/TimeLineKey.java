package com.pheu.twis.domain;

import java.util.Date;

import javax.persistence.Column;

public class TimeLineKey {
	@Column(name = "username")
	private String username;
	@Column(name = "time")
	private Date time;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "TimeLineKey [username=" + username + ", time=" + time + "]";
	}

}
