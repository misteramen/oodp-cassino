package org.project.cassino.model.player.strategy;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.project.cassino.model.action.Action;
import org.project.cassino.model.action.ActionQuery;
import org.project.cassino.model.player.Computer;
import org.project.cassino.model.player.Table;

public class EasyStrategy implements Strategy {
	@Override
	public Action selectAction(Computer computer, Table table) {
		int selection = new Random().nextInt(computer.showHand().size());
		List<Action> actions = ActionQuery.requestActions(
			computer, 
			table,
			selection
		);
		
		// By default adding the trailing action.
		actions.add(ActionQuery.makeTrail(selection, computer, table));
		
		Collections.sort(actions);

		System.out.println("------------------------------------------------------------------------------------------------");
		System.out.print("> Computer Actions -> ");
		for(Action action : actions) {
			System.out.print(action.getType() + "[" + action.getPlayerSelection() + ", " + action.getTableSelections() + "] : ");
		}
		System.out.println("\n------------------------------------------------------------------------------------------------");
		
		return actions.get(actions.size() - 1);
	}
}
