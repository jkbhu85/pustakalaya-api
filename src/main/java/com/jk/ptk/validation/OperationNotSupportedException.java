package com.jk.ptk.validation;

public class OperationNotSupportedException extends RuntimeException {
	private static final long serialVersionUID = 8088642051459538300L;

	public OperationNotSupportedException() {
		super();
	}

	public OperationNotSupportedException(String message) {
		super(message);
	}

}
