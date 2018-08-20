package com.jk.ptk.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jk.ptk.app.AppProps;

@Configuration
public class HttpsConfig {
	private static final Logger log = LoggerFactory.getLogger(HttpsConfig.class);

	// redirect all traffic of HTTP to HTTPS
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint constraint = new SecurityConstraint();
				constraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				constraint.addCollection(collection);

				context.addConstraint(constraint);
			}
		};

		tomcat.addAdditionalTomcatConnectors(httpsConnector());

		return tomcat;
	}

	private Connector httpsConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");

		int httpPort;
		int httpsPort;

		try {
			httpPort = Integer.parseInt(AppProps.valueOf("app.security.http.port"));
			
		} catch (NumberFormatException e) {
			httpPort = 8080;
			log.warn("Port number for HTTP is not defined.");
		}
		
		try {
			httpsPort = Integer.parseInt(AppProps.valueOf("app.security.https.port"));
		} catch (NumberFormatException e) {
			httpsPort = 8443;
			log.warn("Port number for HTTPS is not defined.");
		}

		connector.setScheme("http");
		connector.setPort(httpPort);
		connector.setRedirectPort(httpsPort);
		connector.setSecure(false);

		log.info("Using {} as HTTP and {} as HTTPS port number.", httpPort, httpsPort);

		return connector;
	}
}
