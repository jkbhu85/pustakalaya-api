package com.jk.ptk.f.user;

import com.jk.ptk.security.login.InvalidCredentialsException;
import com.jk.ptk.validation.ValidationException;

public interface UserService {
	boolean userExists(String email);
	
	void addUser(User user) throws ValidationException;

	User getUser(String email);
	
	LightUser getLightUser(String email);

	void updateUser(String email, String fieldName, String newValue) throws ValidationException;

	UserAuthInfo getUserAuthInfo(String email);

	void updateUserAuthInfo(UserAuthInfo authInfo);

	void updatePassword(Long id, String oldPassword, String newPassword) throws InvalidCredentialsException;

	void updateSecurityQuestion(Long id, String password, String question, String answer) throws InvalidCredentialsException;
}
