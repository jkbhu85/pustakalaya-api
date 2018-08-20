package com.jk.ptk.security.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jk.ptk.app.App;
import com.jk.ptk.f.user.User;
import com.jk.ptk.f.user.UserAcStatus;
import com.jk.ptk.f.user.UserService;
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
	public String login(LoginCredentials loginCred)
			throws InvalidCredentialsException, AccountLockedException, AccountRevokedException {
		String username = loginCred.getUsername();
		String password = loginCred.getPassword();

		log.info("Authenticating user with username: {}", username);

		// if username is not valid, throw exception
		if (username == null || username.length() == 0) {
			throw new InvalidCredentialsException();
		}

		User user = service.findByEmail(username);

		// no user found, throw exception
		Integer accountStatus = user.getAccountStatus().getId();
		if (user == null || UserAcStatus.CLOSED.getId().equals(accountStatus)) {
			throw new InvalidCredentialsException();
		}

		if (UserAcStatus.LOCKED.getId().equals(accountStatus)) {
			throw new AccountLockedException();
		}

		if (UserAcStatus.REVOKED.getId().equals(accountStatus)) {
			throw new AccountRevokedException();
		}

		// get password hash
		String passwordHash = CredentialsUtil.getPasswordHash(password, user.getPasswordSalt(),
				user.getPasswordVersion());

		if (user.getPasswordHash().equals(passwordHash)) {
			JwtPayload payload = getPayload(user);
			String jwt = JwtUtil.encode(payload);
			jwt = authHeaderValidator.addValidationMarker(jwt);

			service.updateUnsuccessfulTries(user.getEmail(), 0);

			log.info("Authenticated user with username: {}", username);

			return jwt;
		} else {
			Integer unsuccefullTries = user.getUnsuccessfulTries() + 1;

			if (user.getUnsuccessfulTries() == App.getUnsuccessfulLoginTriesThreshold()) {
				user.setAccountStatus(UserAcStatus.LOCKED);
				log.info("Locking the account {}", user.getEmail());
			}

			service.updateUnsuccessfulTries(user.getEmail(), unsuccefullTries);
			throw new InvalidCredentialsException();
		}
	}

	private JwtPayload getPayload(User authInfo) {
		JwtPayload payload = new JwtPayload();

		payload.setEmail(authInfo.getEmail());
		payload.setName(authInfo.getFirstName());
		payload.setRole(authInfo.getRole().getName());
		payload.setLocale(authInfo.getLocaleValue());

		return payload;
	}

}
