package com.jk.pustakalaya.f.user;

public interface UserService {
	User getUser(Long id);

	UserAuthInfo getUserAuthInfo(Long id);

	UserAuthInfo getUserAuthInfo(String email);

	void updatePassword(Long id, String oldPassword, String newPassword);

	void updateSecurityQuestion(Long id, String password, String question, String answer);
	/*
	void lockAccount(Long id);

	void revokeAccount(Long id);

	void activateAccount(Long id);

	void deleteAccount(Long id);
	*/
}
