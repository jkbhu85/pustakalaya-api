package com.jk.ptk.security.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.jk.ptk.f.user.UserAuthInfo;
import com.jk.ptk.f.user.UserService;
import com.jk.ptk.model.UserAcStatus;
import com.jk.ptk.security.auth.AccountLockedException;
import com.jk.ptk.security.auth.AccountRevokedExceptioin;
import com.jk.ptk.security.auth.jwt.JwtPayload;
import com.jk.ptk.security.auth.jwt.JwtUtil;
import com.jk.ptk.security.cred.CredentialsUtil;
import com.jk.ptk.security.header.AuthHeaderValidator;

@Component
public class LoginServiceImpl implements LoginService {
	private static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private AuthHeaderValidator authHeaderValidator;

	@Autowired
	private UserService service;

	public String login2(LoginCredentials loginCred) {
		String username = loginCred.getUsername();
		String password = loginCred.getPassword();

		log.info("Authenticating user with username: {}", username);

		// if username is not valid, throw exception
		if (username == null || username.length() == 0) {
			throw new InvalidCredentialsException();
		}

		UserAuthInfo authInfo = service.getUserAuthInfo(username);

		// no auth info found, throw exception
		if (authInfo == null || authInfo.getAccountStatus().equals(UserAcStatus.DELETED)) {
			throw new InvalidCredentialsException();
		}

		if (authInfo.getAccountStatus().equals(UserAcStatus.LOCKED)) {
			throw new AccountLockedException();
		}

		if (authInfo.getAccountStatus().equals(UserAcStatus.REVOKED)) {
			throw new AccountRevokedExceptioin();
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

			log.info("Authenticated user with username: {}", username);

			return authHeaderValidator.addValidationMarker(jwt);
		}

		throw new InvalidCredentialsException();
	}

	@Override
	public ResponseEntity<String> login(LoginCredentials loginCred) {
		String username = loginCred.getUsername();
		String password = loginCred.getPassword();

		log.info("Authenticating user with username: {}", username);

		// if username is not valid, throw exception
		if (username == null || username.length() == 0) {
			return new ResponseEntity<>("invalid credentials", HttpStatus.BAD_REQUEST);
		}

		UserAuthInfo authInfo = service.getUserAuthInfo(username);

		// no auth info found, throw exception
		if (authInfo == null || authInfo.getAccountStatus().equals(UserAcStatus.DELETED)) {
			return new ResponseEntity<>("invalid credentials", HttpStatus.BAD_REQUEST);
		}

		if (authInfo.getAccountStatus().equals(UserAcStatus.LOCKED)) {
			return new ResponseEntity<>("account locked", HttpStatus.BAD_REQUEST);
		}

		if (authInfo.getAccountStatus().equals(UserAcStatus.REVOKED)) {
			return new ResponseEntity<>("access revoked", HttpStatus.BAD_REQUEST);
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

			log.info("Authenticated user with username: {}", username);

			return new ResponseEntity<>(jwt, HttpStatus.OK);
		}

		return new ResponseEntity<>("invalid credentials", HttpStatus.BAD_REQUEST);
	}

	private JwtPayload getPayload(UserAuthInfo authInfo) {
		JwtPayload payload = new JwtPayload();

		payload.setId(authInfo.getId().toString());
		payload.setEmail(authInfo.getEmail());
		payload.setName(authInfo.getFirstName());
		payload.setRole(authInfo.getRole());
		payload.setLocale(authInfo.getLocaleValue());

		return payload;
	}

}
