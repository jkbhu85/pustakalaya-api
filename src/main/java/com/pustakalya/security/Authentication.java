package com.pustakalya.security;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pustakalya.util.App;

/**
 * Servlet Filter implementation class Authentication
 */
@SuppressWarnings("unused")
public class Authentication implements Filter {
	private static String excludedUrlPattern = "(^img/*)|(^css/*)|(^js/*)";

    /**
     * Default constructor. 
     */
    public Authentication() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();
		
		if (excludedUrlPattern.matches(excludedUrlPattern)) {
			chain.doFilter(request, response);
		} else {
			boolean loggedIn = isLoggedIn(req);
			boolean authRequired = isAuthRequired(uri);
			
			if ((loggedIn && authRequired) || (!loggedIn && !authRequired)) {
				chain.doFilter(request, response);
			} else if (loggedIn) {
				// redirect to home page
			} else {
				// redirect to index page
				res.sendRedirect("/index.html");
			}
		}
		
	}
	
	private boolean isAuthRequired(String uri) {
		return (
				!uri.startsWith("index.html")
				);
	}
	
	private boolean isLoggedIn(HttpServletRequest req) {
		return req.getAttribute(App.ATTR_SESSION_USER) != null;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
