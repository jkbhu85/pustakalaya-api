package com.jk.ptk.validation;

import com.jk.ptk.app.response.ResponseCode;

public class InvalidArgumentException extends Exception {
	private static final long serialVersionUID = -4317137765883120659L;

	private ResponseCode errorCode;
	private String fieldName;

	public InvalidArgumentException(ResponseCode errorCode, String fieldName) {
		this.errorCode = errorCode;
		this.fieldName = fieldName;
	}

	public ResponseCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ResponseCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
