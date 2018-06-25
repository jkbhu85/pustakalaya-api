package com.jk.ptk.security.auth.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException
	extends AuthenticationException {

	private static final long serialVersionUID = 5461894465725762406L;

	public JwtAuthenticationException(String msg) {
		super(msg);
	}

	public JwtAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

}
