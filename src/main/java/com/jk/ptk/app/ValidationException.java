package com.jk.ptk.app;

/**
 * This excpetion is thrown when data received from client fails validation.
 * 
 * @author Jitendra
 *
 */
public class ValidationException extends Exception {
	private static final long serialVersionUID = -3182256808687423712L;

	private int errorCode;
	private String fieldName;

	/**
	 * Creates a instance with the specified {@code errorCode} and specified
	 * {@code fieldName}.
	 * 
	 * @see <a href=
	 *      "https://github.com/optimus29/pustakalaya/blob/master/src/ptkdocs/error-codes.html">Error
	 *      codes</a>
	 * 
	 * @param errorCode
	 *            error code describing what is wrong with the value being validated
	 * @param fieldName
	 *            name of the field that failed validation
	 */
	public ValidationException(int errorCode, String fieldName) {
		super(errorCode + ":" + fieldName);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getFieldName() {
		return fieldName;
	}

}
