package com.jk.ptk.app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jk.ptk.util.RandomUtil;

/**
 * This class provides application specific values.
 *
 * @author Jitendra
 *
 */
public final class App {
	private App() {
	}

	private static final String APP_DOMAIN = "http://localhost:8080";
	private static final int REGISTRATION_LINK_EXPIRATION_HOURS = 72;

	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	/**
	 * Maximum number of tries with incorrect crdentials after which account is
	 * locked.
	 */
	public static final int UNSUCCESSFUL_LOGIN_TRIES_THRESHOLD = 4;

	/**
	 * Environment variable name for project configuration directory path.
	 */
	public static final String NAME_ENV_VAR_CONFIG = "PTK_CONFIG";

	private static Properties appProps = new Properties();

	static {
		try {
			String fileName = configDirPath() + File.separator + "app.properties";
			InputStream in = new BufferedInputStream(new FileInputStream(fileName));
			appProps.load(in);
		} catch (Exception e) {
			LOG.error("Failed to properties file.{}", e);
		}
	}

	/**
	 * Returns path of the project configuration directory.
	 *
	 * @return path of the project configuration directory
	 */
	public static String configDirPath() {
		return System.getenv(NAME_ENV_VAR_CONFIG);
	}

	/**
	 * Returns URL for the specified URI.
	 *
	 * @param uri
	 *            the speicified uri.
	 * @return URL for the specified URI
	 */
	public static String getUrl(String uri) {
		return appDomain() + "/" + uri;
	}

	/**
	 * Returns registration link expiration duration in hours.
	 *
	 * @return registration link expiration duration in hours
	 */
	public static int registrationLinkExpireDuration() {
		return REGISTRATION_LINK_EXPIRATION_HOURS;
	}

	/**
	 * Returns internet domain of the application.
	 *
	 * @return internet domain of the application
	 */
	public static String appDomain() {
		return APP_DOMAIN;
	}

	public static String getHmacSecret() {
		String hmacSecret = appProps.getProperty("security.hmac.secret");

		if (hmacSecret == null) {
			hmacSecret = new String(RandomUtil.getRandomBytes(32));
			LOG.error("Could not load HMAC secret key from properties. Using a generated secret key.");
		}

		return hmacSecret;
	}
}
