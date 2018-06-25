package com.jk.ptk.security.auth.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.jk.ptk.security.header.AuthHeaderValidator;
import com.jk.ptk.security.header.BasicAuthHeaderValidator;
import com.jk.ptk.security.header.InvalidHeaderException;


public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final String TOKEN_HEADER_NAME = "Authentication";
	private AuthHeaderValidator authHeaderValidator = new BasicAuthHeaderValidator();
//	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	public JwtAuthenticationFilter(AuthenticationManager manager) {
		this(new AntPathRequestMatcher("/**"));
		this.setAuthenticationManager(manager);
	}

	protected JwtAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String jwt = request.getHeader(TOKEN_HEADER_NAME);
		System.out.println("jwt: " + jwt);

		try {
			jwt = authHeaderValidator.removeValidationMarker(jwt);
		} catch (InvalidHeaderException e) {
			jwt = "";
		}

		JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);

		return this.getAuthenticationManager().authenticate(authentication);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		SecurityContextHolder.getContext().setAuthentication(authResult);

		chain.doFilter(request, response);
	}

}
