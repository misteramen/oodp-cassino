package org.project.cassino.model.player;

import java.util.List;

import org.project.cassino.model.action.ActionCompleter;

import org.project.cassino.model.card.Card;
import org.project.cassino.model.card.Hand;
import org.project.cassino.model.card.Peekable;
import org.project.cassino.model.card.Pickable;
import org.project.cassino.model.card.Taker;
import org.project.cassino.model.util.Resettable;
import org.project.cassino.model.util.Utils.Associatives;

public class Table implements Taker, Pickable, Peekable, ActionCompleter, Resettable {
	private Hand cards;
	private Associatives<Integer> selections;
	
	public Table() {
		cards = new Hand();
		selections = new Associatives<Integer>();
	}

	@Override
	public void take(Card...card) {
		cards.take(card);
	}

	@Override
	public Card peek(int selection) {
		return cards.peek(selection);
	}

	@Override
	public Card pick(int selection) {
		return cards.pick(selection);
	}

	public void addSelection(Integer...selection) {
		if(selection != null) {
			for(Integer index : selection) {
				selections.add(index);
			}
		}
	}

	@Override
	public void reset() {
		cards.reset();
		selections.clear();
	}
	
	@Override
	public void completeActions() {
		selections.sort();
		
		for(int i = (selections.size() - 1); i >= 0; i--) {
			pick(selections.get(i));
		}
		
		selections.clear();
	}
	
	public void checkSweep(Player player) {
		if(selections.size() == cards.size()) {
			player.addPoint(1);
			player.addSweep();
		}
	}
	
	public List<Card> getCards() {
		return cards.getCards();
	}
}
