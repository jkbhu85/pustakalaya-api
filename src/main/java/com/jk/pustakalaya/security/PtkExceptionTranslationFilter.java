package com.jk.pustakalaya.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.filter.GenericFilterBean;

public class PtkExceptionTranslationFilter extends GenericFilterBean {
	private AuthenticationEntryPoint authEntryPoint;
	private AccessDeniedHandler accessDeniedHandler;

	private final Logger log = LoggerFactory.getLogger(getClass());



	public PtkExceptionTranslationFilter(AuthenticationEntryPoint authEntryPoint,
			AccessDeniedHandler accessDeniedHandler) {
		super();
		this.authEntryPoint = authEntryPoint;
		this.accessDeniedHandler = accessDeniedHandler;
	}



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			chain.doFilter(request, response);
		} catch (IOException | ServletException e) {
			throw e;
		} catch (AuthenticationException e) {

		} catch (AccessDeniedException e) {

		} catch (Exception e) {
			throw e;
		}

	}
}
