package com.jk.ptk.f.user;

import com.jk.ptk.security.login.InvalidCredentialsException;

public interface UserService {
	User getUser(Long id);

	UserAuthInfo getUserAuthInfo(Long id);

	UserAuthInfo getUserAuthInfo(String email);

	void updatePassword(Long id, String oldPassword, String newPassword) throws InvalidCredentialsException;

	void updateSecurityQuestion(Long id, String password, String question, String answer) throws InvalidCredentialsException;

	void addUser(String email, String firstName, String lastName, String localeStr);

	default void lockAccount(Long id) {
	}

	default void revokeAccount(Long id) {
	}

	default void activateAccount(Long id) {
	}

	default void deleteAccount(Long id) {
	}
}
