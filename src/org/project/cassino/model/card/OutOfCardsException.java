package org.project.cassino.model.card;

public class OutOfCardsException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutOfCardsException() {
        super();
    }
    
    public OutOfCardsException(String message) {
        super(message);
    }
}