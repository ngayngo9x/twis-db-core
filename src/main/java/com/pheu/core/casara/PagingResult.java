package com.pheu.core.casara;

import java.util.List;

public class PagingResult<T> {

	private String nextPage;
	private List<T> ids;

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public List<T> getIds() {
		return ids;
	}

	public void setIds(List<T> ids) {
		this.ids = ids;
	}

}
