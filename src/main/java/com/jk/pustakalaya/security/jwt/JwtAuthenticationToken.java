package com.jk.pustakalaya.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 41299215480315379L;

	private final Object principal;

	public JwtAuthenticationToken(String jwt) {
		super(null);
		this.principal = jwt;
	}

	/**
	 * This method returns {@code null} because in JWT authentication
	 * credentials are not required.
	 */
	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

}
