package com.jk.ptk.security;

import java.io.IOException;
import java.util.Arrays;

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

import com.jk.ptk.app.AppProps;

/**
 * Enables or disables CORS (Cross Origin Resource Sharing).
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
	private final Logger log = LoggerFactory.getLogger(CorsFilter.class);
	private static String[] allowedOrigins = {};
	private static String allowedHeaders;
	private static String allowedMethods;
	private static boolean corsEnabled = false;

	/**
	 * Default constructor.
	 */
	public CorsFilter() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (corsEnabled) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;

			String origin = req.getHeader("origin");

			if (isOriginAllowed(origin)) {
				res.setHeader("Access-Control-Allow-Origin", origin);
				res.setHeader("Access-Control-Allow-Methods", allowedMethods);
				res.setHeader("Access-Control-Allow-Headers", allowedHeaders);
			}
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	private boolean isOriginAllowed(String origin) {
		// in some cases origin can be `null`
		if (origin == null)
			origin = "null";

		for (String oa : allowedOrigins) {
			if (origin.indexOf(oa) == 0)
				return true;
		}

		return false;
	}

	/**
	 * Loads CORS configuration.
	 * 
	 * @param fConfig filter configuration
	 * @throws ServletException no exception is thrown, merely for contract
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		try {
			corsEnabled = "true".equalsIgnoreCase(AppProps.valueOf("app.security.cors.enable"));

			if (!corsEnabled) {
				log.info("CORS is disabled.");
				return;
			}

			allowedHeaders = AppProps.valueOf("app.security.cors.allowed.headers");
			allowedMethods = AppProps.valueOf("app.security.cors.allowed.methods");
			String origins = AppProps.valueOf("app.security.cors.allowed.origins");

			if (allowedHeaders == null || allowedHeaders.isEmpty()) {
				throw new Exception("Allowed HTTP headers are not provided.");
			}

			if (allowedMethods == null || allowedMethods.isEmpty()) {
				throw new Exception("Allowed HTTP methods are not provided.");
			}

			if (origins == null || origins.isEmpty()) {
				throw new Exception("Allowed origins are not provided.");
			}

			allowedOrigins = origins.split(",");
			log.debug("CORS Headers: " + allowedHeaders);
			log.debug("CORS Methods: " + allowedMethods);
			log.debug("CORS Origins: " + Arrays.toString(allowedOrigins));
			log.info("CORS was configured successfully.");
		} catch (Exception e) {
			log.error("Failed to configure CORS.{}", e);
			// disable CORS
			corsEnabled = false;
		}
	}

}
