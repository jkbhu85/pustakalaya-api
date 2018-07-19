package com.jk.ptk.security.login;

public interface LoginService {
	String login(LoginCredentials loginCred) throws InvalidCredentialsException, AccountLockedException, AccountRevokedException;
}
