package com.jk.ptk.security.login;

public class InvalidCredentialsException extends Exception {

	private static final long serialVersionUID = 9084656373655016037L;

	public InvalidCredentialsException() {
		this("Invalid credentials");
	}
	
	public InvalidCredentialsException(String msg) {
		super(msg);
	}
}
