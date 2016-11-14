package org.project.cassino.model.player.strategy;

import org.project.cassino.model.action.Action;
import org.project.cassino.model.player.Computer;
import org.project.cassino.model.player.Table;

public interface Strategy {
	public Action selectAction(Computer computer, Table table);
}
