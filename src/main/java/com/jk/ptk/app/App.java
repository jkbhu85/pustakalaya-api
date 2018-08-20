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
	private static final Logger log = LoggerFactory.getLogger(App.class);

	private App() {
	}

	static {
		if (AppProps.valueOf("app.security.password.version") == null) {
			passwordVersion = 0;
			log.error("Password version property is not set.");
			System.exit(-1);
		} else {
			try {
				passwordVersion = Integer.parseInt(AppProps.valueOf("app.security.password.version"));
			} catch (NumberFormatException ignore) {
				log.error("Password version property is not set.");
			}
		}
	}

	private static String appDomain = "https://localhost:8080";
	private static int registraionLinkExpirationHours = 72;
	private static int passwordVersion = -1;

	public static int getCurrentPasswordVersion() {
		return passwordVersion;
	}

	private static int unsuccessfulLoginTriesThreshold = -1;
	private static int userNoneBookQuota = -1;
	private static int userMemberBookQuota = -1;
	private static int userLibrarianBookQuota = -1;
	private static int userAdminBookQuota = -1;

	public static int getNoneBookQuota() {
		if (userNoneBookQuota != -1)
			return userNoneBookQuota;

		if (AppProps.valueOf("user.none.book.quota") == null) {
			userNoneBookQuota = 0;
		} else {
			try {
				userNoneBookQuota = Integer.parseInt(AppProps.valueOf("user.none.book.quota"));
			} catch (NumberFormatException ignore) {
				userNoneBookQuota = 0;
			}
		}

		return userNoneBookQuota;
	}

	public static int getMemberBookQuota() {
		if (userMemberBookQuota != -1)
			return userMemberBookQuota;

		if (AppProps.valueOf("user.none.book.quota") == null) {
			userMemberBookQuota = 0;
		} else {
			try {
				userMemberBookQuota = Integer.parseInt(AppProps.valueOf("user.member.book.quota"));
			} catch (NumberFormatException ignore) {
				userMemberBookQuota = 0;
			}
		}

		return userMemberBookQuota;
	}

	public static int getLibrarianBookQuota() {
		if (userLibrarianBookQuota != -1)
			return userLibrarianBookQuota;

		if (AppProps.valueOf("user.librarian.book.quota") == null) {
			userLibrarianBookQuota = 0;
		} else {
			try {
				userLibrarianBookQuota = Integer.parseInt(AppProps.valueOf("user.librarian.book.quota"));
			} catch (NumberFormatException ignore) {
				userLibrarianBookQuota = 0;
			}
		}

		return userLibrarianBookQuota;
	}

	public static int getAdminBookQuota() {
		if (userAdminBookQuota != -1)
			return userAdminBookQuota;

		if (AppProps.valueOf("user.admin.book.quota") == null) {
			userAdminBookQuota = 0;
		} else {
			try {
				userAdminBookQuota = Integer.parseInt(AppProps.valueOf("user.admin.book.quota"));
			} catch (NumberFormatException ignore) {
				userAdminBookQuota = 0;
			}
		}

		return userAdminBookQuota;
	}

	/**
	 * Maximum number of tries with incorrect credentials after which account is
	 * locked.
	 */
	public static int getUnsuccessfulLoginTriesThreshold() {
		if (unsuccessfulLoginTriesThreshold != -1)
			return unsuccessfulLoginTriesThreshold;

		if (AppProps.valueOf("user.security.login.unsuccessful.tries.threshold") == null) {
			unsuccessfulLoginTriesThreshold = 4;
		} else {
			try {
				unsuccessfulLoginTriesThreshold = Integer
						.parseInt(AppProps.valueOf("user.security.login.unsuccessful.tries.threshold"));
			} catch (NumberFormatException ignore) {
				unsuccessfulLoginTriesThreshold = 4;
			}
		}

		return unsuccessfulLoginTriesThreshold;
	}

	/**
	 * Returns URL for the specified URI. URI should not begin with '/'.
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
