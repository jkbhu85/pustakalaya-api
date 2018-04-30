package com.jk.pustakalaya.security.jwt;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 41299215480315379L;

	private final Object principal;

	public JwtAuthenticationToken(Object principal) {
		this(principal, null);
	}
	
	public JwtAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal =principal;
		this.setAuthenticated(true);
	}

	/**
	 * This method returns {@code null} because in JWT authentication
	 * password is not required.
	 */
	@Override
	public Object getCredentials() {
		return null;
	}
	
	/**
	 * Returns JWT string.
	 */

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

}
