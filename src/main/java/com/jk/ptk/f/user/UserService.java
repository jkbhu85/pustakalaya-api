package com.jk.ptk.f.user;

import com.jk.ptk.app.ValidationException;
import com.jk.ptk.security.login.InvalidCredentialsException;

public interface UserService {
	void addUser(User user) throws ValidationException;

	User getUser(String email);

	void updateUser(String email, String fieldName, String newValue) throws ValidationException;

	UserAuthInfo getUserAuthInfo(String email);

	void updateUserAuthInfo(UserAuthInfo authInfo);

	void updatePassword(Long id, String oldPassword, String newPassword) throws InvalidCredentialsException;

	void updateSecurityQuestion(Long id, String password, String question, String answer) throws InvalidCredentialsException;
}
