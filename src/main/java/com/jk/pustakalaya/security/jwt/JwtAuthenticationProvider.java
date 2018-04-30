package com.jk.pustakalaya.security.jwt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class JwtAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		boolean authenticated = false;
		
		try {
			authenticated = JwtUtil.isAuthenticated((String)authentication.getPrincipal());

			if (!authenticated) throw new JwtAuthenticationException("JWT expired");
			
			
			
		} catch (JWTVerificationException e) {
			throw new JwtAuthenticationException("Invalid JWT. Might be an attack.");
		}
		
		return create(authentication);
	}
	
	private Authentication create(Authentication token) {
		JwtPayload payload = JwtUtil.decode((String) token.getPrincipal());
		Collection<GrantedAuthority> auths = new ArrayList<>();
		
		for (String role: payload.getRoles()) {
			auths.add(new SimpleGrantedAuthority(role));
		}
		
		JwtAuthenticationToken newToken = new JwtAuthenticationToken(
					payload,
					auths
				);
		
		return newToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
