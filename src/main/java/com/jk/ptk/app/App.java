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

	private static int passwordVersion = -1;

	static {
		if (AppProps.valueOf("app.security.password.version") != null) {
			try {
				passwordVersion = Integer.parseInt(AppProps.valueOf("app.security.password.version"));
				log.info("Password version in used is {}.", passwordVersion);
			} catch (NumberFormatException ignore) {
			}
		}
		
		if (passwordVersion == -1) {
			log.error("Password version property is not set.");
			System.exit(1);
		}
	}

	public static int getCurrentPasswordVersion() {
		return passwordVersion;
	}


	private static int userNoneBookQuota = -1;
	
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


	private static int userMemberBookQuota = -1;
	
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


	private static int userLibrarianBookQuota = -1;
	
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
	

	private static int userAdminBookQuota = -1;

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


	private static int unsuccessfulLoginTriesThreshold = -1;
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


	private static int registraionLinkExpirationHours = -1;
	
	/**
	 * Returns registration link expiration duration in hours.
	 *
	 * @return registration link expiration duration in hours
	 */
	public static int registrationLinkExpireDuration() {
		if (registraionLinkExpirationHours != -1)
			return registraionLinkExpirationHours;

		if (AppProps.valueOf("user.partial.registration.link.expire.period") == null) {
			registraionLinkExpirationHours = 72;
		} else {
			try {
				registraionLinkExpirationHours = Integer
						.parseInt(AppProps.valueOf("user.partial.registration.link.expire.period"));
			} catch (NumberFormatException ignore) {
				registraionLinkExpirationHours = 72;
			}
		}

		return registraionLinkExpirationHours;
	}


	private static String uiDomain = null;
	/**
	 * Returns Internet domain of the front end application.
	 *
	 * @return Internet domain of the front end application
	 */
	public static String appDomain() {
		if (uiDomain != null)
			return uiDomain;

		if (AppProps.valueOf("app.frontend.domain") == null) {
			uiDomain = "https://localhost:4200";
		} else {
			uiDomain = AppProps.valueOf("app.frontend.domain");
		}
		
		return uiDomain;
	}
	
	/**
	 * Returns URL for the specified URI for front end application.
	 *
	 * @param uri
	 *            the specified URI
	 * @return URL for the specified URI
	 */
	public static String getUrl(String uri) {
		if (uri == null) return null;
		
		if (uri.startsWith("/")) return uiDomain + uri;
		else return uiDomain + "/" + uri;
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
