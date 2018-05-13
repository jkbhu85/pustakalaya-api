package com.jk.pustakalaya.security.login;

import org.springframework.stereotype.Component;

import com.jk.pustakalaya.security.auth.jwt.JwtPayload;
import com.jk.pustakalaya.security.auth.jwt.JwtUtil;
import com.jk.pustakalaya.security.header.AuthHeaderValidator;
import com.jk.pustakalaya.security.header.BasicAuthHeaderValidator;

@Component
public class LoginServiceImpl implements LoginService {
	private AuthHeaderValidator authHeaderValidator = new BasicAuthHeaderValidator();

	@Override
	public String login(LoginCredentials loginCred) throws InvalidCredentialsException {
		boolean authenticated = "jiten".equals(loginCred.getUsername()) && "pwd".equals(loginCred.getPassword());

		if (authenticated) {
			JwtPayload payload = new JwtPayload(
						"1",
						"Jitendra",
						"jitendra@kumar.com",
						new String[] {"USER"}
					);

			return authHeaderValidator.addValidationMarker(JwtUtil.encode(payload));
		}

		throw new InvalidCredentialsException();
	}

}
