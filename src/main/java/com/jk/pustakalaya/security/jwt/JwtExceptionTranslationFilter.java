package com.jk.pustakalaya.security.jwt;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;

public class JwtExceptionTranslationFilter extends ExceptionTranslationFilter {

	public JwtExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
		super(authenticationEntryPoint);
	}

}
