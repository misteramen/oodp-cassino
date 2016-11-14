package org.project.cassino.model.action;

import java.util.ArrayList;
import java.util.List;

import org.project.cassino.model.card.Card;
import org.project.cassino.model.card.Rank;
import org.project.cassino.model.player.Computer;
import org.project.cassino.model.player.Player;
import org.project.cassino.model.player.Table;
import org.project.cassino.model.util.Utils.Associatives;

public class ActionQuery {
	public static Action pairing(int selection, Player player, int tableCardSelection, Table table) {
		Action action = null;
		
		Card cardOfPlayer = player.peek(selection);
		Card cardOfTable = table.peek(tableCardSelection);
		
		if(cardOfPlayer.getRank().getValue() == cardOfTable.getRank().getValue()) {
			action = Action.newInstance(ActionType.PAIRING, selection, new Associatives<Integer>(tableCardSelection), table);
		}
		
		if(action == null) {
			throw new IllegalActionException();
		}
		
		return action;
	}
	
	public static Action combining(int selection, Player player, Associatives<Integer> tableCardSelections, Table table) {
		Action action = null;
		Card cardOfPlayer = player.peek(selection);
		int rankOfSelectedCard = cardOfPlayer.getRank().getValue();
		int rankOfSelectedTableCards = 0;
		
		for(int i = 0; i < table.getCards().size(); i++) {
			if(tableCardSelections.validate(i)) {
				if(!table.getCards().get(i).equalsRank(new Card(Rank.ACE, null))) {
					rankOfSelectedTableCards += table.getCards().get(i).getRank().getValue();
				} else {
					rankOfSelectedTableCards += table.getCards().get(i).getRank().getLower();
				}
			}
		}
		
		if(rankOfSelectedCard == rankOfSelectedTableCards) {
			action = Action.newInstance(ActionType.COMBINING, selection, tableCardSelections, table);
		}
		
		if(action == null) {
			throw new IllegalActionException();
		}
		
		return action;
	}
	
	public static Action makeTrail(int selection, Player player, Table table) {
		return Action.newInstance(
			ActionType.TRAILING, 
			selection, 
			null, 
			table
		);
	}
	
	public static List<Action> requestActions(Computer computer, Table table, int selection) {
		List<Action> actions = new ArrayList<Action>();
		
		int rankOfPlayerSelection = computer.peek(selection).getRank().getValue();
		
		// Query Pairing Actions.
		for(int i = 0; i < table.getCards().size(); i++) {
			if(rankOfPlayerSelection == table.peek(i).getRank().getValue()) {
				actions.add(
					Action.newInstance(
						ActionType.PAIRING, 
						selection, 
						new Associatives<Integer>(i), 
						table
					)
				);
			}
		}
		
		return actions;
	}
}
