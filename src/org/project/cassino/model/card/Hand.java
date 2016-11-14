package org.project.cassino.model.card;

import java.util.ArrayList;
import java.util.List;

import org.project.cassino.model.util.Resettable;

public class Hand implements Taker, Peekable, Pickable, Resettable {
	private List<Card> cards;
	
	public Hand() {
		cards = new ArrayList<Card>();
	}

	@Override
	public void take(Card...takenCards) {
		for(Card card : takenCards) {
			cards.add(card);
		}
	}

	@Override
	public Card peek(int selection) {
		if(selection >= 0 && selection <= (size() - 1)) {
			return cards.get(selection);
		} else {
			return null;
		}
	}
	
	@Override
	public Card pick(int selection) {
		Card removedCard = null;
		
		if(selection >= 0 && selection <= (size() - 1)) {
			removedCard = cards.get(selection);
		}
		
		cards.remove(removedCard);

		return removedCard;
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public int size() {
		return cards.size();
	}
	
	public boolean isEmpty() {
		if(cards != null && size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void reset() {
		cards.clear();
	}
	
	public static int numberOfInstansiated(Object[] array) {
		int size = 0;
		
		for(Object element : array) {
			if(element != null) {
				size += 1;
			}
		}
		
		return size;
	}
	
	public static Integer[] cleanArray(Integer[] array) {
		Integer[] newArray = new Integer[numberOfInstansiated(array)];

		for(int i = 0, j = 0; i < array.length; i++) {
			if(array[i] != null) {
				newArray[j] = array[i];
				j += 1;
			}
		}
		
		return newArray;
	}
	
	@Override
	public String toString() {
		return cards.toString();
	}
}












