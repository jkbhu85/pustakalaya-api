package com.jk.pustakalaya.security;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CorsFilter
 */
//@WebFilter(dispatcherTypes = {DispatcherType.REQUEST } , urlPatterns = { "/*" })
public class CorsFilter implements Filter {
    public CorsFilter() {
    	System.err.println("-------- CorsFilter created --------");
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String origin = req.getHeader("origin");
		System.out.println("Request origin: " + origin);
		
		res.setHeader("Access-Control-Allow-Origin", origin);

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
