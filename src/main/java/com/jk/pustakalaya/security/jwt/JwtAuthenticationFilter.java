package com.jk.pustakalaya.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final String TOKEN_HEADER_NAME = "json_web_token";
	
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

		if (jwt == null) throw new JwtAuthenticationException("JSON Web Token was not found in request header.");

		JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);

		return this.getAuthenticationManager().authenticate(authentication);
	}

}
