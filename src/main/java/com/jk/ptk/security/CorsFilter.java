package com.jk.ptk.security;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

import com.jk.ptk.app.App;

/**
 * Provides CORS filtering.
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
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
		if (origin == null) origin = "null";
		
		for (String oa: allowedOrigins) {
			if (origin.indexOf(oa) == 0) return true;
		}
		
		return false;
	}

	/**
	 * Loads CORS configuraion from property file.
	 * 
	 * @param fConfig filter configuration, not used in this filter
	 * @throws ServletException no exception is thrown, merely for contract
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		Properties props = new Properties();
		String configDirPath = App.configDirPath();
		String propFilePath = configDirPath + File.separator + "app.properties";

		try (InputStream in = new BufferedInputStream(new FileInputStream(propFilePath))) {
			props.load(in);

			corsEnabled = "true".equalsIgnoreCase(props.getProperty("cors.enable"));

			if (!corsEnabled) {
				log.info("CORS is disabled.");
				return;
			}

			allowedHeaders = props.getProperty("cors.allowed.headers");
			allowedMethods = props.getProperty("cors.allowed.methods");
			String origins = props.getProperty("cors.allowed.origins");

			if (allowedHeaders == null || allowedHeaders.isEmpty()) {
				throw new Exception("Allowed http headers are not provided.");
			}

			if (allowedMethods == null || allowedMethods.isEmpty()) {
				throw new Exception("Allowed http methods are not provided.");
			}

			if (origins == null || origins.isEmpty()) {
				throw new Exception("Allowed origins are not provided.");
			}

			allowedOrigins = origins.split(",");
			log.info("successfully loaded CORS configuraiton.");
		} catch (Exception e) {
			log.error("Failed to load CORS configuration due to following error.{}", e);
		}
	}

}
