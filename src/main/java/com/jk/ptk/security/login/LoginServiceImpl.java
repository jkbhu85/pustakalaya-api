package com.jk.ptk.security.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jk.ptk.app.App;
import com.jk.ptk.f.user.UserAuthInfo;
import com.jk.ptk.f.user.UserService;
import com.jk.ptk.model.UserAcStatus;
import com.jk.ptk.security.auth.jwt.JwtPayload;
import com.jk.ptk.security.auth.jwt.JwtUtil;
import com.jk.ptk.security.header.AuthHeaderValidator;

@Component
public class LoginServiceImpl implements LoginService {
	private static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private AuthHeaderValidator authHeaderValidator;

	@Autowired
	private UserService service;

	@Override
	public String login(LoginCredentials loginCred) throws InvalidCredentialsException, AccountLockedException, AccountRevokedException {
		String username = loginCred.getUsername();
		String password = loginCred.getPassword();

		log.info("Authenticating user with username: {}", username);

		// if username is not valid, throw exception
		if (username == null || username.length() == 0) {
			throw new InvalidCredentialsException();
		}

		UserAuthInfo authInfo = service.getUserAuthInfo(username);

		// no auth info found, throw exception
		if (authInfo == null || authInfo.getAccountStatus().equals(UserAcStatus.CLOSED.getName())) {
			throw new InvalidCredentialsException();
		}

		if (authInfo.getAccountStatus().equals(UserAcStatus.LOCKED.getName())) {
			throw new AccountLockedException();
		}

		if (authInfo.getAccountStatus().equals(UserAcStatus.REVOKED.getName())) {
			throw new AccountRevokedException();
		}

		// get password hash
		String passwordHash =
				CredentialsUtil.getPasswordHash
				(
						password,
						authInfo.getPasswordSalt(),
						authInfo.getPasswordVersion()
				);

		if (authInfo.getPasswordHash().equals(passwordHash)) {
			JwtPayload payload = getPayload(authInfo);
			String jwt = JwtUtil.encode(payload);
			jwt = authHeaderValidator.addValidationMarker(jwt);
			authInfo.setUnsuccessfulTries(0);

			service.updateUserAuthInfo(authInfo);

			log.info("Authenticated user with username: {}", username);

			return jwt;
		} else {
			authInfo.setUnsuccessfulTries(authInfo.getUnsuccessfulTries() + 1);
			
			if (authInfo.getUnsuccessfulTries() == App.UNSUCCESSFUL_LOGIN_TRIES_THRESHOLD) {
				authInfo.setAccountStatus(UserAcStatus.LOCKED);
			}

			service.updateUserAuthInfo(authInfo);	
			
			throw new InvalidCredentialsException();
		}
	}

	private JwtPayload getPayload(UserAuthInfo authInfo) {
		JwtPayload payload = new JwtPayload();

		payload.setEmail(authInfo.getEmail());
		payload.setName(authInfo.getFirstName());
		payload.setRole(authInfo.getRole());
		payload.setLocale(authInfo.getLocaleValue());

		return payload;
	}

}
