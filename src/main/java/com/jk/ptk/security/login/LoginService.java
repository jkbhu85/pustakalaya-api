package com.jk.ptk.security.login;

import com.jk.ptk.security.auth.AccountLockedException;
import com.jk.ptk.security.auth.AccountRevokedException;

public interface LoginService {
	String login(LoginCredentials loginCred) throws InvalidCredentialsException, AccountLockedException, AccountRevokedException;
}
