package com.jk.pustakalaya.login;

public class InvalidCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 9084656373655016037L;

	public InvalidCredentialsException() {
		super("Invalid credentials");
	}
}
