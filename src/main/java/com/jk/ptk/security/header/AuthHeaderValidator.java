package com.jk.ptk.security.header;


/**
 * Provides validation of authentication header.
 *
 * @author Jitendra
 *
 */
public interface AuthHeaderValidator {

	/**
	 * Returns {@code true} if the header value is valid {@code false} otherwise.
	 *
	 * @param headerValue the string to validate
	 * @return {@code true} if the header value is valid {@code false} otherwise
	 */
	boolean isHeaderValid(String headerValue);


	/**
	 * Adds authentication marker in the specified header value.
	 *
	 * @param headerValue header value in which validation marker would be added
	 * @return header value after adding validation marker
	 *
	 * @throws NullPointerException if the specified value is null
	 */
	String addValidationMarker(String headerValue) throws NullPointerException;


	/**
	 *
	 * Removes authentication marker from the specified header value.
	 *
	 * @param headerValue header value from which validation marker would be removed
	 * @return header value after removing validation marker
	 *
	 * @throws InvalidHeaderException if {@link #isHeaderValid(String)} returns false on the specified value
	 */
	String removeValidationMarker(String headerValue) throws InvalidHeaderException;
}
