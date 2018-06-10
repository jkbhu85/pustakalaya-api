package com.jk.pustakalaya.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.GenericFilterBean;

import com.jk.pustakalaya.security.auth.jwt.JwtAuthenticationFilter;
import com.jk.pustakalaya.security.auth.jwt.JwtAuthenticationProvider;


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

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.authenticationProvider(jwtAuthenticationProvider());
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers("/api/login")
				.antMatchers("/test/*")
				.antMatchers("/**")
				.and()
			;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
				.disable()
			.logout()
				.disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.headers()
				.disable()
			.requestCache()
				.disable()
			.anonymous()
				.disable();

		//http.cors().configurationSource(corsConfigurationSource());

		http
			.authorizeRequests()
				.antMatchers("/resources/**").permitAll()
				.anyRequest().authenticated();

		http
			.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class)
			.addFilterBefore(appExceptionTranslationFilter(), ExceptionTranslationFilter.class);
	}


	@Bean
	public GenericFilterBean appExceptionTranslationFilter() {
		return new PtkExceptionTranslationFilter(appAuthenticationEntryPoint(), appAccessDeniedHandler());
	}


	@Bean
	public AccessDeniedHandler appAccessDeniedHandler() {
		log.debug("Custom AccessDeniedHandler created");
		return (request, response, exception) -> {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		};
	}

	@Bean
	public AuthenticationProvider jwtAuthenticationProvider() {
		return new JwtAuthenticationProvider();
	}

	@Bean
	public AuthenticationEntryPoint appAuthenticationEntryPoint() {
		log.debug("Custom AuthenticationEntryPoint created");
		return (request, response, authException) -> {
			System.out.println("Authentication entry point called");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		};
	}


	//@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		List<String> allowedOrigins = new ArrayList<>();
		List<String> allowedMethods = new ArrayList<>();
		List<String> allowedHeaders = new ArrayList<>();

		//allowedOrigins.add("null");
		allowedOrigins.add("http://localhost:4200");

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
//		config.setAllowedHeaders(allowedHeaders);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		log.debug("Cors configuration source called");

		return source;
	}
}
