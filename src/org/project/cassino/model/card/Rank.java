package org.project.cassino.model.card;

public enum Rank {
	NULL_RANK(0), 
	ACE(1, 14), DEUCE(2), THREE(3), FOUR(4), FIVE(5), SIX(6), 
	SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);
	
	private int value;
	
	// An ACE can assume one of two values either 1 or 14. Depending on the game played.
	private boolean dualValued;
	private int level;
	private int[] values = new int[2];
	
	public static final int CAPACITY = 13;
	public static final int LOW  = 0x01;
	public static final int HIGH = 0x0E;
	
	private Rank(int value) {
		dualValued = false;
		this.value = value;
	}
	
	private Rank(int firstValue, int secondValue) {
		dualValued = true;
		values[0] = firstValue;
		values[1] = secondValue;
		level = HIGH;
	}
	
	public void setDefaultLevel(int level) {
		if(level == LOW) {
			level = LOW;
		} else if(level == HIGH) {
			level = HIGH;
		}
	}
	
	public int getValue() {
		if(!dualValued) {
			return value;
		} else {
			if(level == LOW) {
				return values[0];
			} else {
				return values[1];
			}
		}
	}
	
	public int getHigher() {
		if(dualValued == true) {
			return values[1];
		} else {
			throw new InvokingASingleValuedSuitException();
		}
	}
	
	public int getLower() {
		if(dualValued == true) {
			return values[0];
		} else {
			throw new InvokingASingleValuedSuitException();
		}
	}
	
	public int[] getValues() {
		if(dualValued == true) {
			return values;
		} else {
			throw new InvokingASingleValuedSuitException();
		}
	}
}

















