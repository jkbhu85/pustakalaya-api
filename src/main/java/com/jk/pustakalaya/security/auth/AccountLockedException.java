package com.jk.pustakalaya.security.auth;

public class AccountLockedException extends RuntimeException {
	private static final long serialVersionUID = -4004199264118030465L;

	public AccountLockedException() {
		super("User account is locked.");
	}
}
