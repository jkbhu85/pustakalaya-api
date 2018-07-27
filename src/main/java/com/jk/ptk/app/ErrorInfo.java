package com.jk.ptk.app;

import com.jk.ptk.app.response.ResponseCode;

/**
 * Represents an error in validation which includes a field name and an error
 * code describing type of the error.
 * 
 * @author Jitendra
 *
 */
public class ErrorInfo {

	private ResponseCode errorCode;
	private String fieldName;

	public ErrorInfo() {
	}

	/**
	 * Creates a instance with the specified error code.
	 * 
	 * @param errorCode
	 *                  the specified error code
	 * 
	 * @see {@link ResponseCode}
	 */
	public ErrorInfo(ResponseCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Creates a instance with the specified error code and specified field name.
	 * 
	 * @param errorCode
	 *                  the specified error code
	 * @param fieldName
	 *                  the specified field name
	 * 
	 * @see {@link ResponseCode}
	 */
	public ErrorInfo(ResponseCode errorCode, String fieldName) {
		this.errorCode = errorCode;
		this.fieldName = fieldName;
	}

	/**
	 * @return the errorCode
	 */
	public ResponseCode getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *                  the errorCode to set
	 */
	public void setErrorCode(ResponseCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *                  the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
