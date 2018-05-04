package com.jk.pustakalaya.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.filter.GenericFilterBean;

import com.jk.pustakalaya.security.login.InvalidCredentialsException;

public class PtkExceptionTranslationFilter extends GenericFilterBean {
	private AuthenticationEntryPoint authEntryPoint;
	private AccessDeniedHandler accessDeniedHandler;

	//private final Logger log = LoggerFactory.getLogger(getClass());

	public PtkExceptionTranslationFilter(AuthenticationEntryPoint authEntryPoint,
			AccessDeniedHandler accessDeniedHandler) {
		super();
		this.authEntryPoint = authEntryPoint;
		this.accessDeniedHandler = accessDeniedHandler;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		try {
			chain.doFilter(request, response);
		}
		catch (AuthenticationException e) {
			this.authEntryPoint.commence(req, res, e);
		}
		catch (AccessDeniedException e) {
			this.accessDeniedHandler.handle(req, res, e);
		}
		catch (InvalidCredentialsException e) {
			res.sendError(399, e.getMessage());
		}
		catch (IOException | ServletException e) {
			throw e;
		}
		catch (Exception e) {
			throw e;
		}

	}
}
