package com.jk.ptk.security.auth;

public class AccountLockedException extends Exception {
	private static final long serialVersionUID = -4004199264118030465L;

	public AccountLockedException() {
		super("User account is locked.");
	}
}
