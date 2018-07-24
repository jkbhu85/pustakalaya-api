package com.jk.ptk.f.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jk.ptk.app.ValidationException;
import com.jk.ptk.security.login.CredentialsUtil;
import com.jk.ptk.security.login.InvalidCredentialsException;

@Component
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;

	@Override
	public User getUser(String email) {
		return repository.findUser(email);
	}

	@Override
	public void addUser(User user) throws ValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUser(String email, String fieldName, String newValue) throws ValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public UserAuthInfo getUserAuthInfo(String email) {
		return repository.findUserAuthInfo(email);
	}

	@Override
	public void updateUserAuthInfo(UserAuthInfo authInfo) {
		repository.udpateUserAuthInfo(authInfo);

	}

	@Override
	public void updatePassword(Long id, String oldPassword, String newPassword) throws InvalidCredentialsException {
		UserAuthInfo authInfo = repository.findUserAuthInfo(id);

		if (authInfo == null)
			throw new RuntimeException("Invalid user");

		if (isPasswordValid(oldPassword, authInfo)) {
			String newPasswordHash = CredentialsUtil.getPasswordHash(newPassword, authInfo.getPasswordSalt(),
					authInfo.getPasswordVersion());
			authInfo.setPasswordHash(newPasswordHash);

			repository.udpateUserAuthInfo(authInfo);
		} else
			throw new InvalidCredentialsException("Old password didn't match.");
	}

	private boolean isPasswordValid(String password, UserAuthInfo authInfo) {
		String passwordHash = CredentialsUtil.getPasswordHash(password, authInfo.getPasswordSalt(),
				authInfo.getPasswordVersion());

		return authInfo.getPasswordHash().equals(passwordHash);
	}

	@Override
	public void updateSecurityQuestion(Long id, String password, String question, String answer)
			throws InvalidCredentialsException {
		UserAuthInfo authInfo = repository.findUserAuthInfo(id);

		if (isPasswordValid(password, authInfo)) {
			authInfo.setSecurityQuestion(question);
			authInfo.setSecurityAnswer(answer);

			repository.udpateUserAuthInfo(authInfo);
		} else
			throw new InvalidCredentialsException("Password didn't match.");
	}

}
