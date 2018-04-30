package com.jk.pustakalaya.login;

import java.util.Arrays;

import com.jk.pustakalaya.security.jwt.JwtPayload;
import com.jk.pustakalaya.security.jwt.JwtUtil;

public class LoginServiceImpl implements LoginService {

	@Override
	public String login(LoginCredentials loginCred) throws InvalidCredentialsException {
		boolean authenticated = "jiten".equals(loginCred.getUsername()) && "pwd".equals(loginCred.getPassword());
		
		if (authenticated) {
			JwtPayload payload = new JwtPayload(
						"1",
						"Jitendra",
						"jitendra@kumar.com",
						Arrays.asList("USER")
					);
			
			return JwtUtil.encode(payload);
		}
		
		throw new InvalidCredentialsException();
	}

}