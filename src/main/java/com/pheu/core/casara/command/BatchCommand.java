package com.pheu.core.casara.command;

import java.util.List;

public interface BatchCommand<T> {
	public void batchCreate(List<T> entities);
}
