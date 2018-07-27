package com.jk.ptk.app;

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

	private static String appDomain = "http://localhost:8080";
	private static int registraionLinkExpirationHours = 72;

	private static final Logger log = LoggerFactory.getLogger(App.class);

	/**
	 * Maximum number of tries with incorrect credentials after which account is
	 * locked.
	 */
	public static int unsuccessfulLoginTriesThreshold = 4;
	
	/**
	 * Email of admin.
	 */
	public static String adminEmail = "jk.bhu85@gmail.com";

	/**
	 * Returns URL for the specified URI.
	 *
	 * @param uri
	 *            the specified URI
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
		return registraionLinkExpirationHours;
	}

	/**
	 * Returns Internet domain of the application.
	 *
	 * @return Internet domain of the application
	 */
	public static String appDomain() {
		return appDomain;
	}

	public static String getHmacSecret() {
		String hmacSecret = AppProps.valueOf("app.security.auth.jwt.hmac.secret");

		if (hmacSecret == null) {
			hmacSecret = new String(RandomUtil.getRandomBytes(32));
			log.warn("Could not load HMAC secret key from properties. Using a generated secret key.");
		}

		return hmacSecret;
	}
}
