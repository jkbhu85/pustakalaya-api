package com.jk.ptk.f.user;

public interface UserService {
	User getUser(Long id);

	UserAuthInfo getUserAuthInfo(Long id);

	UserAuthInfo getUserAuthInfo(String email);

	void updatePassword(Long id, String oldPassword, String newPassword);

	void updateSecurityQuestion(Long id, String password, String question, String answer);

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
