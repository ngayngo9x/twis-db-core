package com.pheu.twis.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.base.Optional;
import com.pheu.core.casara.PagingResult;
import com.pheu.twis.command.TimelineCommand;
import com.pheu.twis.dao.TimelineDao;
import com.pheu.twis.domain.TimeLine;

public class TimelineDaoImpl implements TimelineDao {

	private static Logger LOGGER = Logger.getLogger(TimelineDaoImpl.class.getName());

	private TimelineCommand timelineCommand;

	public TimelineDaoImpl(TimelineCommand timelineCommand) {
		this.timelineCommand = timelineCommand;
	}
	
	/*public List<String> list(int limit) {
		try {
			return timelineCommand.list(limit);
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return Collections.emptyList();
	}

	public List<String> list(Date time, int limit) {
		try {
			return timelineCommand.list(time, limit);
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return Collections.emptyList();
	}*/

	public TimelineCommand getTimelineCommand() {
		return timelineCommand;
	}

	public void setTimelineCommand(TimelineCommand timelineCommand) {
		this.timelineCommand = timelineCommand;
	}

	public Optional<PagingResult<TimeLine>> listPaging(String nextPaging, String username, int limit) {
		try {
			return Optional.fromNullable(timelineCommand.listPaging(nextPaging, username, limit));
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		}
		return Optional.absent();
	}

	public void batchCreate(List<TimeLine> timelines) {
		try {
			timelineCommand.batchCreate(timelines);
		} catch (Exception e) {
			LOGGER.finest(e.getMessage());
		} 
	}

}
