package org.project.cassino.control;

import java.util.Iterator;
import java.util.List;

import org.project.cassino.model.action.Action;
import org.project.cassino.model.action.ActionQuery;
import org.project.cassino.model.action.ActionType;
import org.project.cassino.model.util.Utils.Associatives;

public class GamePerformer {
	private GameController gameController;
	
	public GamePerformer(GameController gameController) {
		this.gameController = gameController;
	}
	
	public Action requestPairAction(int playerIndex, int tableIndex) {
		return ActionQuery.pairing(playerIndex, gameController.getPlayer(), tableIndex, gameController.getTable());
	}
	
	public Action requestCombineAction(int playerIndex, Associatives<Integer> tableIndices) {
		return ActionQuery.combining(playerIndex, gameController.getPlayer(), tableIndices, gameController.getTable());
	}
	
	public void performActions(List<Action> actions) {
		Action lastPerformedAction = null;
		
		for(Action action : actions) {
			lastPerformedAction = action;
			
			Iterator<Integer> selectionIterator = action.getTableSelections().iterator();
			
			while(selectionIterator.hasNext()) {
				gameController.getTable().addSelection(selectionIterator.next());
			}
			
			gameController.getPlayer().performAction(gameController.getTable(), lastPerformedAction);
		}
		
		if(lastPerformedAction != null) {
			gameController.getPlayer().select(lastPerformedAction.getPlayerSelection());
			gameController.getTable().checkSweep(gameController.getPlayer());
			gameController.getTable().completeActions();
			gameController.getPlayer().completeActions();
		}
	}
	
	public void performTrailAction(int playerIndex) {
		Action action = ActionQuery.makeTrail(playerIndex, gameController.getPlayer(), gameController.getTable());
		
		gameController.getPlayer().performAction(gameController.getTable(), action);
	}
	
	public void performComputerAction() {
		Action action = gameController.getComputer().performAction(gameController.getTable(), null);
		
		if(action.getType().equals(ActionType.PAIRING)) {
			gameController.getComputer().select(action.getPlayerSelection());
			gameController.getTable().addSelection(action.getTableSelections().get(0));
			gameController.getTable().checkSweep(gameController.getComputer());
			gameController.getTable().completeActions();
			gameController.getComputer().completeActions();
		} else {
			gameController.getTable().completeActions();
		}
	}
}
