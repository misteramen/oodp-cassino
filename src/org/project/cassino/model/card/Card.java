package org.project.cassino.model.card;

public class Card {
	private Rank rank;
	private Suit suit;
	
	// The following card is 
	public static final Card NULL_CARD = new Card(Rank.NULL_RANK, Suit.NULL_SUIT);
	
	public Card() {
		this(Rank.NULL_RANK, Suit.NULL_SUIT);
	}
	
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	public Card(Card card) {
		this.suit = card.suit;
		this.rank = card.rank;
	}
	
	public Rank getRank() {
		return this.rank;
	}
	
	public Suit getSuit() {
		return this.suit;
	}
	
	@Override
    public boolean equals(Object obj) {
		Card card = (Card) obj;
		
		if(equalsRank(card) && equalsSuit(card)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean greaterThan(Card card) {
		if(rank.getValue() > card.rank.getValue()) {
			return true;
		}
		
		return false;
	}
	
	public boolean lessThan(Card card) {
		if(this == Card.NULL_CARD) {
			return false;
		} else {
			if(rank.getValue() < card.rank.getValue()) {
				return true;
			}
			
			return false;
		}
	}
	
	public boolean isInferiorTo(Card card) {
		if(this == Card.NULL_CARD) {
			return false;
		} else {
			if(this.suit.getValue() < card.suit.getValue()) {
				return true;
			}
			
			return false;
		}
	}
	
	public boolean equalsRank(Card card) {
		if(rank.getValue() == card.rank.getValue()) {
			return true;
		}
		
		return false;
	}
	
	public boolean equalsSuit(Card card) {
		if(suit.getValue() == card.suit.getValue()) {
			return true;
		}
		
		return false;
	}
	
	public boolean equalsAny(Card card) {
		if(rank.getValue() == card.rank.getValue() || suit.getValue() == card.suit.getValue()) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		if(this == Card.NULL_CARD) {
			return "NULL_CARD";
		} else {
			return rank + " of " + suit;
		}
	}
}


