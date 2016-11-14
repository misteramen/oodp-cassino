package org.project.cassino.model.card;

public class InvokingASingleValuedSuitException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvokingASingleValuedSuitException() {
		super("Cannot invoke a Suit with a single value.");
	}
	
	public InvokingASingleValuedSuitException(String message) {
		super(message);
	}
}