package com.jk.pustakalaya.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jk.pustakalaya.security.jwt.JwtAuthenticationFilter;
import com.jk.pustakalaya.security.jwt.JwtAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class PustakalayaWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	private static final Logger log = LoggerFactory.getLogger(PustakalayaWebSecurityConfigurerAdapter.class); 
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/login");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/resources/assets/**").permitAll()
				.anyRequest().authenticated()
				.and()/*
			.cors()
				.configurationSource(corsConfigurationSource())
				.and()*/
			.csrf().disable()
			.addFilterAt(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.sessionManagement()
				.disable()
			.logout()
				.disable()
			.headers()
				.disable()
			.authenticationProvider(new JwtAuthenticationProvider());
	}
	
	
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		List<String> allowedOrigins = new ArrayList<>();
		List<String> allowedMethods = new ArrayList<>();
		List<String> allowedHeaders = new ArrayList<>();

		allowedOrigins.add("**");
		
		allowedMethods.add("GET");
		allowedMethods.add("POST");
		allowedMethods.add("PUT");
		allowedMethods.add("PATCH");
		allowedMethods.add("DELETE");
		allowedMethods.add("OPTIONS");
		allowedHeaders.add("Content-Type");

		config.setAllowedOrigins(allowedOrigins);
		config.setAllowedMethods(allowedMethods);
		config.setAllowedHeaders(allowedHeaders);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		log.debug("Cors configuration source called");

		return source;
	}
}
