package com.jk.ptk.security.login;

public class AccountRevokedException extends Exception {
	private static final long serialVersionUID = -6148397147449477754L;

	public AccountRevokedException() {
		super("Access to account has been revoked");
	}
}
