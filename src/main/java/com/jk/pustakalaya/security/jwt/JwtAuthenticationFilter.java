package com.jk.pustakalaya.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final String TOKEN_HEADER_NAME = "Authentication";
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	public JwtAuthenticationFilter() {
		this(new AntPathRequestMatcher("/**"));
	}

	protected JwtAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		String jwt = request.getHeader(TOKEN_HEADER_NAME);

		if (jwt == null || jwt.length() == 0) throw new JwtAuthenticationException("JSON Web Token was not found in request header.");

		log.debug("jwt: {}", jwt);

		JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);

		log.debug("token: {}", authentication);

		return this.getAuthenticationManager().authenticate(authentication);
	}

}
