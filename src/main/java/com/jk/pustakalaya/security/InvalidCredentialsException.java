package com.jk.pustakalaya.security;

public class InvalidCredentialsException extends RuntimeException {
	private static final long serialVersionUID = 5578240907648986917L;

	public InvalidCredentialsException() {
		super("Invalid credentials");
	}

	public InvalidCredentialsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCredentialsException(String message) {
		super(message);
	}

}
