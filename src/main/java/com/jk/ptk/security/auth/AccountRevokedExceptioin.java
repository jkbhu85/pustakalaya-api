package com.jk.ptk.security.auth;

public class AccountRevokedExceptioin extends RuntimeException {
	private static final long serialVersionUID = -6148397147449477754L;

	public AccountRevokedExceptioin() {
		super("Access to account has been revoked");
	}
}
