package com.jk.pustakalaya.security.jwt;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 41299215480315379L;

	private final Object principal;

	public JwtAuthenticationToken(Object principal) {
		this(principal, null, false);
	}

	public JwtAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities, boolean authenticated) {
		super(authorities);

		assert principal != null: ("Principal can not be null.");

		this.principal = principal;
		this.setAuthenticated(authenticated);
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
