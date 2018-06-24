package com.jk.ptk.security.header;

public class InvalidHeaderException extends RuntimeException {

	private static final long serialVersionUID = -3844357914286412035L;

	public InvalidHeaderException() {
		super("Header value is invalid.");
	}

	public InvalidHeaderException(String msg) {
		super (msg);
	}

	public InvalidHeaderException(String msg, Throwable t) {
		super (msg, t);
	}

}
