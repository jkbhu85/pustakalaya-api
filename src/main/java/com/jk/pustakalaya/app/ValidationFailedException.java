package com.jk.pustakalaya.app;

/**
 * This excpetion is thrown when data received from client fails validation.
 * 
 * @author Jitendra
 *
 */
public class ValidationFailedException extends Exception {
	private static final long serialVersionUID = -3182256808687423712L;

	/**
	 * Constructs a new exception with specified detail message and cause.
	 * 
	 * @param message
	 *            detail message
	 * @param cause
	 *            the cause
	 */
	public ValidationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with specified detail message.
	 * 
	 * @param message
	 *            detail message
	 */
	public ValidationFailedException(String message) {
		super(message);
	}

}
