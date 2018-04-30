package com.jk.pustakalaya.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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


@EnableWebSecurity
public class PustakalayaWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	private static final Logger log = LoggerFactory.getLogger(PustakalayaWebSecurityConfigurerAdapter.class);
	/*
	@Autowired
	@Qualifier("jwtAuthenticationProvider")
	private AuthenticationProvider authenticationProvider;


	@Autowired
	@Qualifier("jwtAuthenticationFilter")
	private Filter authenticationFilter;


*/
	@Autowired
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.authenticationProvider(new JwtAuthenticationProvider());
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/login");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.csrf()
				.disable()
			.logout()
				.disable()
			.sessionManagement()
				.disable()
			.headers()
				.disable()
			.requestCache()
				.disable()
			.anonymous()
				.disable()
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.addFilterAt(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}


	public AuthenticationManager authenticationManager(AuthenticationProvider ...authProviders) {
		List<AuthenticationProvider> list = new ArrayList<>();

		for (AuthenticationProvider ap: authProviders) {
			list.add(ap);
		}

		return new ProviderManager(list);
	}

	@Bean
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
		allowedHeaders.add("Authorization");

		config.setAllowedOrigins(allowedOrigins);
		config.setAllowedMethods(allowedMethods);
		config.setAllowedHeaders(allowedHeaders);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		log.debug("Cors configuration source called");

		return source;
	}
}
