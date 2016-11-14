package org.project.cassino.model.card;

public enum Suit {
	NULL_SUIT(0), SPADES(1), DIAMONDS(4), CLUBS(3), HEARTS(2);
	private int value;
	public static final int CAPACITY = 4;
	
	private Suit(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}