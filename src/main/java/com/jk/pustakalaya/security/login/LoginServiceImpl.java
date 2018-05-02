package com.jk.pustakalaya.security.login;

import com.jk.pustakalaya.security.auth.jwt.JwtPayload;
import com.jk.pustakalaya.security.auth.jwt.JwtUtil;

public class LoginServiceImpl implements LoginService {

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

			return JwtUtil.encode(payload);
		}

		throw new InvalidCredentialsException();
	}

}
