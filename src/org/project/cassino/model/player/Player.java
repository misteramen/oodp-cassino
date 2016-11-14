package org.project.cassino.model.player;

import java.util.ArrayList;
import java.util.List;

import org.project.cassino.model.action.Action;
import org.project.cassino.model.action.ActionCompleter;
import org.project.cassino.model.action.Actionable;
import org.project.cassino.model.card.Card;
import org.project.cassino.model.card.Hand;
import org.project.cassino.model.card.Peekable;
import org.project.cassino.model.card.Pickable;
import org.project.cassino.model.card.Taker;
import org.project.cassino.model.util.Resettable;

public class Player implements Taker, Peekable, Pickable, Actionable, ActionCompleter, Resettable {
	private int selection;
	private int score;
	private int sweepCounter;
	private String name;
	private Hand hand;
	private List<Card> capturedCards;
	
	public Player(String name) {
		this.name = name;
		score = 0;
		sweepCounter = 0;
		selection = -1;
		hand = new Hand();
		capturedCards = new ArrayList<Card>();
	}
	
	public void capture(Card...captureCard) {
		for(Card card : captureCard) {
			capturedCards.add(card);
		}
	}
	
	public void capture(List<Card> captureCard) {
		for(Card card : captureCard) {
			capturedCards.add(card);
		}
	}
	
	@Override
	public void take(Card...takenCards) {
		hand.take(takenCards);
	}

	@Override
	public Card peek(int selection) {
		return hand.peek(selection);
	}
	
	public Card pick(int selection) {
		return hand.pick(selection);
	}
	
	public void select(int selection) {
		 this.selection = selection;
	}
	
	public void addPoint(int point) {
		score += point;
	}
	
	public void addSweep() {
		sweepCounter += 1;
	}
	
	public void resetSweeps() {
		sweepCounter = 0;
	}
	
	public List<Card> showHand() {
		return hand.getCards();
	}
	
	public List<Card> showCaptured() {
		return capturedCards;
	}

	@Override
	public Action performAction(Table table, Action action) {
		Action.perform(
			action, 
			this, 
			table
		);
		
		return action;
	}
	
	@Override
	public void completeActions() {
		if(hasSelected()) {
			capture(hand.pick(selection));
			selection = -1;
		}
	}
	
	@Override
	public void reset() {
		score = 0;
		selection = -1;
		hand.reset();
		capturedCards.clear();
	}
	
	public void nextRound() {
		selection = -1;
		hand.reset();
		capturedCards.clear();
	}
	
	public int getScore() {
		return score;
	}
	
	public int getSweeps() {
		return sweepCounter;
	}
	
	public boolean hasSelected() {
		if(selection != -1 && selection >= 0 && selection < hand.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasEmptyHand() {
		return hand.isEmpty();
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "N[" + name + "] : H["+ hand +"] : C["+ capturedCards +"]";
	}
}






