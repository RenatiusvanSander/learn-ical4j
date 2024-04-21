package edu.remad.learnical4j.exceptions;

import edu.remad.learnical4j.InterchangeCalendarBuilder;

public class InterchangeCalendarBuilderException extends RuntimeException {
	
	/**
	 * generated serial UID
	 */
	private static final long serialVersionUID = 3961562043013239827L;

	public InterchangeCalendarBuilderException() {
		super(InterchangeCalendarBuilder.class.getName() + " has errors");
	}
	
	public InterchangeCalendarBuilderException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
