package com.jk.pustakalaya.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;



public class JwtAuthenticationProvider implements AuthenticationProvider {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);


	public JwtAuthenticationProvider() {

	}


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String jwt = (String) authentication.getPrincipal();
		boolean authenticated = false;
		JwtPayload payload = null;

		log.debug("principal: {}", jwt);

		try {
			authenticated = JwtUtil.isAuthenticated(jwt);

			if (authenticated) payload = JwtUtil.decode(jwt);
		} catch (RuntimeException e) {
			log.debug("authentication failed");
			throw new JwtAuthenticationException(e.getMessage(), e);
		}

		Authentication token = new JwtAuthenticationToken(
					payload,
					AuthorityUtils.createAuthorityList(payload.getRoles()),
					true
				);

		log.debug("authentication successful");
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		log.debug("authentication support test for: {}", authentication.getName());
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
