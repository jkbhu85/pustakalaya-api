package com.jk.pustakalaya.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class PustakalayaWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/**").permitAll();
	}

	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		List<String> allowedOrigins = new ArrayList<>();
		List<String> allowedMethods = new ArrayList<>();
		List<String> allowedHeaders = new ArrayList<>();

		//allowedOrigins.add("**");
		allowedOrigins.add("http://localhost:4200");
		allowedMethods.add("GET,POST,PUT,PATCH,DELETE,OPTIONS");
		allowedHeaders.add("Content-Type");

		config.setAllowedOrigins(allowedOrigins);
		config.setAllowedMethods(allowedMethods);
		config.setAllowedHeaders(allowedHeaders);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/*", config);

		return source;
	}
}
