package org.project.cassino.model.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.project.cassino.model.util.*;
import org.project.cassino.model.util.Utils.Associatives;

public class Deck {
	protected Card[] cards;
	protected Card[] cardHistory;

	public static final int CAPACITY = 52;
	
	public Deck() {
		int i = 0;
		cards = new Card[CAPACITY];

		for(Rank rank : Rank.values()) {
			for(Suit suit : Suit.values()) {
				if(rank != Rank.NULL_RANK && suit != Suit.NULL_SUIT) {
					cards[i] = new Card(rank, suit);
					i += 1;
				}
			}
		}
	}
	
	private Deck(Deck deck) {
		this.setCards(deck.cards);
	}
	
	private Deck(Card[] card) {
		this.setCards(card);
	}
	
	public void restack() {
		int i = 0;
		for(Rank r : Rank.values()) {
			for(Suit s : Suit.values()) {
				if(r != Rank.NULL_RANK && s != Suit.NULL_SUIT) {
					cards[i] = new Card(r, s);
					i += 1;
				}
			}
		}
	}
	
	public final void setCards(Card[] cards) {
		try {
			this.cards = cards;
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized int size() {
		int count = 0;
		
		try {
			while(count < cards.length && cards[count] != Card.NULL_CARD) {
				count += 1;
			}
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	public synchronized static Card pop(Deck deck) throws NullPointerException {
		if(!deck.isEmpty()) {
			Card card = Manipulate.extract(deck.cards, 0);
			deck.setCards(
				Manipulate.shift(
					deck.cards, 
					1, 
					(deck.cards.length - 1), 
					1, 
					Direction.LEFT
				)
			);
			
			return card;
		}
		
		return Card.NULL_CARD;
	}
	
	public synchronized Card deal() throws OutOfCardsException {
		Card card = Card.NULL_CARD;
		
		if((card = Deck.pop(this)) != Card.NULL_CARD) {
			return card;
		}
		
		throw new OutOfCardsException("This Deck instance is out of cards!");
	}
	
	public synchronized Card[] deal(int numberOfCards) {
		Card[] cards = new Card[numberOfCards];
		
		for(int i = 0; i < numberOfCards; i++) {
			cards[i] = this.deal();
		}
		
		return cards;
	}
	
	public Deck shuffle(Shuffle mode) throws NullPointerException {
		Card[] backToArray;
		
		switch(mode) {
			case PILE:
				if(this.size() > 8) {
					Card[] leftHand6 = new Card[this.size()];
					
					for(int i = 0; i < leftHand6.length; i++) {
						leftHand6[i] = this.deal();
					}
					
					int[] stackCountJ = new int[8];
					Card[][] stack = new Card[8][7];
					Associatives<Integer> stack1 = new Associatives<Integer>(0,  8, 16, 24, 32, 40, 48);
					Associatives<Integer> stack2 = new Associatives<Integer>(1,  9, 17, 25, 33, 41, 49);
					Associatives<Integer> stack3 = new Associatives<Integer>(2, 10, 18, 26, 34, 42, 50);
					Associatives<Integer> stack4 = new Associatives<Integer>(3, 11, 19, 27, 35, 43, 51);
					Associatives<Integer> stack5 = new Associatives<Integer>(4, 12, 20, 28, 36, 44, 52);
					Associatives<Integer> stack6 = new Associatives<Integer>(5, 13, 21, 29, 37, 45);
					Associatives<Integer> stack7 = new Associatives<Integer>(6, 14, 22, 30, 38, 46);
					Associatives<Integer> stack8 = new Associatives<Integer>(7, 15, 23, 31, 39, 47);
					
					for(int i = 0; i < stackCountJ.length; i++) {
						stackCountJ[i] = 0;
					}
					
					for(int i = 0, stackCountI = 0; i < leftHand6.length; i++, stackCountI++) {
						if(stackCountI >= 8) {
							stackCountI = 0;
						}
						
						if(stack1.validate(i)) {
							stack[stackCountI][stackCountJ[0]] = leftHand6[i];
							stackCountJ[0] += 1;
						} else if(stack2.validate(i)) {
							stack[stackCountI][stackCountJ[1]] = leftHand6[i];
							stackCountJ[1] += 1;
						} else if(stack3.validate(i)) {
							stack[stackCountI][stackCountJ[2]] = leftHand6[i];
							stackCountJ[2] += 1;
						} else if(stack4.validate(i)) {
							stack[stackCountI][stackCountJ[3]] = leftHand6[i];
							stackCountJ[3] += 1;
						} else if(stack5.validate(i)) {
							stack[stackCountI][stackCountJ[4]] = leftHand6[i];
							stackCountJ[4] += 1;
						} else if(stack6.validate(i)) {
							stack[stackCountI][stackCountJ[5]] = leftHand6[i];
							stackCountJ[5] += 1;
						} else if(stack7.validate(i)) {
							stack[stackCountI][stackCountJ[6]] = leftHand6[i];
							stackCountJ[6] += 1;
						} else if(stack8.validate(i)) {
							stack[stackCountI][stackCountJ[7]] = leftHand6[i];
							stackCountJ[7] += 1;
						}
					}
					
					// System.out.println("stack[0]: " + Extras.toStringArray(stack[0]));
					
					if(leftHand6.length == Deck.CAPACITY) {
						Card[] flattenedArray = Manipulate.flatten(stack);
						List<Card> dynamicStack = new ArrayList<Card>();
						
						for(int i = 0; i < flattenedArray.length; i++) {
							if(flattenedArray[i] == null) {
								continue;
							}else if(flattenedArray[i] instanceof Card) {
								dynamicStack.add(flattenedArray[i]);
							}
						}
						
						Card[] backToArray3 = new Card[dynamicStack.size()];
						backToArray = dynamicStack.toArray(backToArray3);
						
						return new Deck(backToArray);
					} else if(leftHand6.length != Deck.CAPACITY) {
						int numberOfNullCards = Deck.CAPACITY - leftHand6.length;
						Card[] flattenedArray = Manipulate.flatten(stack);
						Card[] nullCards = new Card[numberOfNullCards];
						List<Card> staticStack = new ArrayList<Card>();
						List<Card> dynamicStack = new ArrayList<Card>();
						
						for(int i = 0; i < numberOfNullCards; i++) {
							nullCards[i] = Card.NULL_CARD;
						}
						
						for(int i = 0; i < flattenedArray.length; i++) {
							if(flattenedArray[i] == null) {
								continue;
							} else if(flattenedArray[i] instanceof Card) {
								staticStack.add(flattenedArray[i]);
							}
						}
						
						dynamicStack.addAll(staticStack);
						dynamicStack.addAll(Arrays.asList(nullCards));
						
						Card[] backToArray3 = new Card[dynamicStack.size()];
						backToArray = dynamicStack.toArray(backToArray3);
						
						return new Deck(backToArray);
					}
				} else  {
					return new Deck(this).shuffle(Shuffle.RANDOM);
				}
			case RANDOM:
				Card[] card1 = this.cards;
				Random randomGenerator = new Random();
				
				for(int e = 0; e < this.size(); e++) {
					int position = randomGenerator.nextInt(this.size());
					
					Card temp = card1[e];
					card1[e] = card1[position];
					card1[position] = temp;
				}
				
				return new Deck(card1);
			case FISHER_YATES:
				List<Card> FY_old_cards = new ArrayList<Card>(Arrays.asList(cards));
				Card[] FY_new_cards = Deck.createNullCardArray();
				
				Random FY_random = new Random();
				int FY_newIndex = 0;
				int FY_length = size() - 1;
				
				while(FY_length >= 0) {
					int FY_randomIndex = FY_random.nextInt(FY_length + 1);
					
					FY_new_cards[FY_newIndex] = FY_old_cards.get(FY_randomIndex);
					FY_old_cards.remove(FY_new_cards[FY_newIndex]);
					
					FY_length -= 1; 
					FY_newIndex += 1;
				}
				
				return new Deck(FY_new_cards);
			default:
				return null;
		}
	}
	
	public static Card[] createNullCardArray() {
		Card[] cards = new Card[52];
		
		for(int i = 0; i < cards.length; i++) {
			cards[i] = Card.NULL_CARD;
		}
		
		return cards;
	}
	
	public Deck sort(Sort mode) {
		switch(mode) {
			case RANK:
				return new Deck(Manipulate.sort(this.cards, Sort.RANK));
			case SUIT:
				return new Deck(Manipulate.sort(this.cards, Sort.SUIT));
			case SUIT_VS_RANK:
				return new Deck(Manipulate.sort(this.cards, Sort.SUIT_VS_RANK));
			case RANK_VS_SUIT:
				return new Deck(Manipulate.sort(this.cards, Sort.RANK_VS_SUIT));
			default:
				return null;
		}
	}
        
    public boolean isEmpty() {
        if(cards[0].equals(Card.NULL_CARD)) {
            return true;
        } else {
            return false;
        }
    }
}






