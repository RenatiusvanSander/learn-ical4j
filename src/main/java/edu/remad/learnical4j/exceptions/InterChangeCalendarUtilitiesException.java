package edu.remad.learnical4j.exceptions;

import edu.remad.learnical4j.utilities.InterchangeCalendarUtilities;

public class InterChangeCalendarUtilitiesException extends RuntimeException {

	/**
	 * generated serial UID
	 */
	private static final long serialVersionUID = -5604209438976198340L;

	public InterChangeCalendarUtilitiesException() {
		super(InterchangeCalendarUtilities.class.getName() + " has errors");
	}
	
	public InterChangeCalendarUtilitiesException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}

	public InterChangeCalendarUtilitiesException(String errorMessage) {
		super(errorMessage);
	}
}
