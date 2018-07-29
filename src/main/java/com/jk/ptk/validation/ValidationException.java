package com.jk.ptk.validation;

import java.util.Map;

import com.jk.ptk.app.response.ResponseCode;

/**
 * This exception is thrown when data received from client fails validation.
 *
 * @author Jitendra
 *
 */
public class ValidationException extends Exception {
	private static final long serialVersionUID = -3182256808687423712L;

	Map<String, ResponseCode> errorMap;

	/**
	 * Creates an instance with the specified list of validation errors.
	 *
	 * @param errorMap map of validation errors
	 */
	public ValidationException(Map<String, ResponseCode> errorMap) {
		super("Invalid data.");
		this.errorMap = errorMap;
	}

	/**
	 * Returns map of validation errors with field names as key.
	 *
	 * @return map of validation errors
	 */
	public Map<String, ResponseCode> getErrorMap() {
		return errorMap;
	}

}
