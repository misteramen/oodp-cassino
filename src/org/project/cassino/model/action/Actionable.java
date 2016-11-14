package org.project.cassino.model.action;

import org.project.cassino.model.player.Table;

public interface Actionable {
	public Action performAction(Table table, Action action);
}
