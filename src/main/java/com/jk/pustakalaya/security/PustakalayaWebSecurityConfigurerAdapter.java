package com.jk.pustakalaya.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Component
public class PustakalayaWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		http
			.authorizeRequests()
				.antMatchers("/**").permitAll()
				.and()
			.cors()
				.configurationSource(this.corsConfigurationSource())
				.and();
		*/
	}
	
	private CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		List<String> allowedOrigins = new ArrayList<>();
		List<String> allowedMethods = new ArrayList<>();
		
		//allowedOrigins.add("**");
		allowedOrigins.add("null");
		allowedMethods.add("**");
		
		config.setAllowedOrigins(allowedOrigins);
		config.setAllowedMethods(allowedMethods);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
}
