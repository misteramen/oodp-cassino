package org.project.cassino.model.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.project.cassino.model.card.Card;
import org.project.cassino.model.card.Deck;
import org.project.cassino.model.card.Rank;
import org.project.cassino.model.card.Suit;
import org.project.cassino.model.player.Player;
import org.project.cassino.model.player.Table;
import org.project.cassino.model.util.Utils;
import org.project.cassino.model.util.Utils.Associatives;

public class Action implements Comparable<Action> {
	private ActionType type;
	private int priority;
	private int playerSelection;
	private Associatives<Integer> tableSelections;
	
	private Action() {
		super();
		
		type = null;
		playerSelection = -1;
		tableSelections = null;
	}
	
	private void setType(ActionType type) {
		this.type = type;
	}
	
	private void setPlayerSelection(int playerSelection) {
		this.playerSelection = playerSelection;
	}
	
	private void setTableSelections(Associatives<Integer> tableSelections) {
		this.tableSelections = tableSelections;
	}
	
	private void setPriority(int priority) {
		this.priority = priority;
	}
	
	public ActionType getType() {
		return type;
	}
	
	public int getPlayerSelection() {
		return playerSelection;
	}
	
	public Associatives<Integer> getTableSelections() {
		return tableSelections;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public boolean hasConflict(Action comparableAction) {
		int index = 0;
		boolean[] conflictValidation = new boolean[tableSelections.size()];
		Iterator<Integer> iterator = tableSelections.iterator();
		
		Outer:
			while(iterator.hasNext()) {
				Integer selection = iterator.next();
				Iterator<Integer> comparableIterator = comparableAction.getTableSelections().iterator();
				
				while(comparableIterator.hasNext()) {
					Integer comparableSelection = comparableIterator.next();
					
					if(selection == comparableSelection) {
						conflictValidation[index] = true;
						index += 1;
						
						break Outer;
					}
				}
				
				conflictValidation[index] = false;
				index += 1;
			}
		
		if(Utils.trueAny(conflictValidation)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Action action) {
		if(priority < action.getPriority()) {
			return -1;
		} else if(priority == action.getPriority()) {
			return 0;
		} else  {
			return 1;
		}
	}
	
	@Override
	public boolean equals(Object object) {
		Action action = (Action) object;
		
		if(type.equals(action.getType()) &&
		   playerSelection == action.getPlayerSelection() &&
		   tableSelections.size() == action.getTableSelections().size()) {
			boolean[] validate = new boolean[tableSelections.size()];
			int counter = 0;
			
			Iterator<Integer> outer = action.getTableSelections().iterator();
			
			while(outer.hasNext()) {
				Iterator<Integer> inner = tableSelections.iterator();
				
				while(inner.hasNext()) {
					if(outer.next() == inner.next()) {
						validate[counter] = true;
						counter += 1;
						break;
					}
				}
			}
			
			if(Utils.trueAll(validate)) {
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return type + " : [" + playerSelection + "] : [" + tableSelections + "]";
	}
	
	public static Action newInstance(ActionType type, int playerSelection, Associatives<Integer> tableSelections, Table table) {
		Action action = new Action();
		
		action.setType(type);
		action.setPlayerSelection(playerSelection);
		action.setTableSelections(tableSelections);
		
		int priority = 0;
		
		if(type != ActionType.TRAILING) {
			Iterator<Integer> tableSelectionsIterator = tableSelections.iterator();
			
			while(tableSelectionsIterator.hasNext()) {
				Integer tableSelection = tableSelectionsIterator.next();
				
				if(table.peek(tableSelection).equals(new Card(Rank.DEUCE, Suit.SPADES))) {
					priority += 2;
				}
				
				if(table.peek(tableSelection).equals(new Card(Rank.TEN, Suit.DIAMONDS))) {
					priority += 2;
				}

				if(table.peek(tableSelection).equalsSuit(new Card(Rank.NULL_RANK, Suit.SPADES))) {
					priority += 1;
				}
			}
		} else {
			priority = -1;
		}
		
		action.setPriority(priority);
		
		return action;
	}
	
	public static Player perform(Action action, Player player, Table table) {
		switch(action.getType()) {
			case PAIRING:
				List<Card> captureCardsFromPairing = new ArrayList<Card>();
				
				for(int i = table.getCards().size(); i >= 0; i--) {
					if(action.getTableSelections().validate(i)) {
						captureCardsFromPairing.add(table.peek(i));
					}
				}
				
				player.capture(captureCardsFromPairing);
				
				return player;
			case COMBINING:
				List<Card> captureCardsFromCombining = new ArrayList<Card>();
				
				for(int i = table.getCards().size(); i >= 0; i--) {
					if(action.getTableSelections().validate(i)) {
						captureCardsFromCombining.add(table.peek(i));
					}
				}

				player.capture(captureCardsFromCombining);
				
				return player;
			case TRAILING:
				table.take(player.pick(action.getPlayerSelection()));
				
				break;
			default:
				break;
		}
		
		return player;
	}
	
	public static List<Action> merge(List<Action> left, List<Action> right) {
		for(Action action : right) {
			left.add(action);
		}
		
		return left;
	}
	
	public static void main(String[] args) {
		Deck deck = new Deck();
		Table table = new Table();
		table.take(deck.deal(6));
		
		Action pairAction    = Action.newInstance(ActionType.COMBINING, 3, new Associatives<Integer>(1, 3, 5),    table);
		Action combineAction = Action.newInstance(ActionType.COMBINING, 3, new Associatives<Integer>(0, 2, 4, 5), table);
		
		System.out.println("   Pair action: " + pairAction);
		System.out.println("Combine action: " + combineAction);
		System.out.println("Check conflict: " + pairAction.hasConflict(combineAction));
	}
}









































