package com.jk.pustakalaya.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.core.config.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Servlet Filter implementation class CorsFilter
 */
//@WebFilter(dispatcherTypes = {DispatcherType.REQUEST } , urlPatterns = { "/*" })
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(CorsFilter.class);

    public CorsFilter() {
    	System.err.println("-------- CorsFilter created --------");
    	log.info("-------- CorsFilter created --------");
    }

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String origin = req.getHeader("origin");
		System.out.println("Request origin: " + origin);

		res.setHeader("Access-Control-Allow-Origin", origin);
		//res.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,PATCH,DELETE,OPTIONS");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
