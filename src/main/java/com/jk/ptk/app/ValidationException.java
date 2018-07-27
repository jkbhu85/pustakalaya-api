package com.jk.ptk.app;

import java.util.List;

/**
 * This exception is thrown when data received from client fails validation.
 * 
 * @author Jitendra
 *
 */
public class ValidationException extends Exception {
	private static final long serialVersionUID = -3182256808687423712L;

	List<ErrorInfo> errorList;

	/**
	 * Creates an instance with the specified list of validation errors.
	 * 
	 * @param list list of validation errors
	 */
	public ValidationException(List<ErrorInfo> list) {
		super("Invalid data.");
		this.errorList = list;
	}

	/**
	 * Returns list of validation errors.
	 * 
	 * @return list of validation errors
	 */
	public List<ErrorInfo> getErrorList() {
		return errorList;
	}

}
