package com.jk.ptk.config;

import java.io.File;

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
	private static final int DEFAULT_HTTP_PORT = 8080;
	private static final int DEFAULT_HTTPS_PORT = 8443;
	private int httpPort;
	private int httpsPort;

	// redirect all traffic of HTTP to HTTPS
	@Bean
	public ServletWebServerFactory servletContainer() {
		setPortNumbers();
		
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				// enforce HTTPS over entire application
				SecurityConstraint constraint = new SecurityConstraint();
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				constraint.setUserConstraint("CONFIDENTIAL");
				constraint.addCollection(collection);

				context.addConstraint(constraint);
				
				
				// enable PUT mapping for API, i.e. for URI's starting with '/ptk'
				/*
				SecurityConstraint putMappingConstraint = new SecurityConstraint();
				SecurityCollection putMappingCollection = new SecurityCollection();
				putMappingCollection.addPattern("/ptk");
				putMappingCollection.addMethod("PUT");
				putMappingConstraint.addCollection(putMappingCollection);
				
				context.addConstraint(putMappingConstraint);
				*/
			}
		};

		serverFactory.setPort(httpPort);
		//serverFactory.addAdditionalTomcatConnectors(httpConnector());
		serverFactory.addAdditionalTomcatConnectors(httpsConnector());

		return serverFactory;
	}
	
	private void setPortNumbers() {
		try {
			httpPort = Integer.parseInt(AppProps.valueOf("app.security.http.port"));
		} catch (NumberFormatException ignore) {
			log.warn("Port number for HTTP is not defined or is incorrect. Using default port {}", DEFAULT_HTTP_PORT);
			httpPort = DEFAULT_HTTP_PORT;
		}
		
		try {
			httpsPort = Integer.parseInt(AppProps.valueOf("app.security.https.port"));
		} catch (NumberFormatException e) {
			log.warn("Port number for HTTPS is not defined. Using default port {}", DEFAULT_HTTPS_PORT);
			httpsPort = DEFAULT_HTTPS_PORT;
		}
	}
	
	private Connector httpConnector() {
		Connector httpConnector = new Connector();
		
		httpConnector.setPort(httpPort);
		httpConnector.setRedirectPort(httpsPort);
		httpConnector.setProperty("connectionTimeout", "20000");
		httpConnector.setProperty("protocol", "HTTP/1.1");
		
		return httpConnector;
	}

	private Connector httpsConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		
		String basePath = System.getenv(AppProps.NAME_ENV_VAR_CONFIG);
		String storePath = basePath + File.separator + AppProps.valueOf("app.security.ssl.keystore");
		String storeType = AppProps.valueOf("app.security.ssl.keystore-type");
		String storePass = AppProps.valueOf("app.security.ssl.keystore-password");
		
		/*SSLHostConfigCertificate certificate = new SSLHostConfigCertificate();
		certificate.setCertificateKeystoreFile(storePath);
		certificate.setCertificateKeystorePassword(storePass);
		certificate.setCertificateKeystoreType(storeType);
		
		SSLHostConfig sslConfig = new SSLHostConfig();
		sslConfig.addCertificate(certificate);*/
		
		// enable SSL
		//connector.addSslHostConfig(sslConfig);

		connector.setProperty("SSLEnabled", "true");
		connector.setProperty("keystoreFile", storePath);
		connector.setProperty("keystorePass", storePass); 
		connector.setProperty("keystoreType", storeType);
		connector.setProperty("sslProtocol", "TLS");
		connector.setProperty("clientAuth", "false");
		connector.setScheme("https");
		connector.setPort(httpsPort);
		connector.setSecure(true);
		
		connector.setProperty("maxThreads", "150");

		return connector;
	}
}
