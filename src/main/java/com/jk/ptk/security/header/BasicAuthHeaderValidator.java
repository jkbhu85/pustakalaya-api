package com.jk.ptk.security.header;

import org.springframework.stereotype.Component;

/**
 * Provides basic implementation of {@link AuthHeaderValidator} interface.
 *
 * @author Jitendra
 *
 */
@Component
public class BasicAuthHeaderValidator implements AuthHeaderValidator {
	private static final String HEADER_PREFIX = "Bearer: ";

	@Override
	public boolean isHeaderValid(String headerValue) {
		return (headerValue != null && headerValue.startsWith(HEADER_PREFIX));
	}

	@Override
	public String addValidationMarker(String headerValue) throws NullPointerException {
		headerValue.length();

		return HEADER_PREFIX + headerValue;
	}

	@Override
	public String removeValidationMarker(String headerValue) throws InvalidHeaderException {
		if (!isHeaderValid(headerValue)) throw new InvalidHeaderException();

		return headerValue.substring(HEADER_PREFIX.length());
	}

}
