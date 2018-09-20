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
			} catch (NumberFormatException ignore) {}
		}

		if (passwordVersion == -1) {
			log.error("Password version property with name 'app.security.password.version' is not set.");
			System.exit(1);
		}
	}

	public static int getCurrentPasswordVersion() {
		return passwordVersion;
	}


	public static int getNoneBookQuota() {
		return 0;
	}

	private static int userMemberBookQuota = -1;

	public static int getMemberBookQuota() {
		if (userMemberBookQuota != -1) return userMemberBookQuota;

		if (AppProps.valueOf("user.member.book.quota") == null) {
			try {
				userMemberBookQuota = Integer.parseInt(AppProps.valueOf("user.member.book.quota"));
			} catch (NumberFormatException ignore) {}
		}
		
		if (userMemberBookQuota == -1) {
			userMemberBookQuota = 0;
			log.warn("Using default value of {} for property 'user.memeber.book.quota'.", userMemberBookQuota);
		}

		return userMemberBookQuota;
	}

	private static int userLibrarianBookQuota = -1;

	public static int getLibrarianBookQuota() {
		if (userLibrarianBookQuota != -1) return userLibrarianBookQuota;

		if (AppProps.valueOf("user.librarian.book.quota") != null) {
			try {
				userLibrarianBookQuota = Integer.parseInt(AppProps.valueOf("user.librarian.book.quota"));
			} catch (NumberFormatException ignore) {}
		}

		if (userLibrarianBookQuota == -1) {
			userLibrarianBookQuota = 0;
			log.warn("Using default value of {} for property 'user.librarian.book.quota'.", userLibrarianBookQuota);
		}

		return userLibrarianBookQuota;
	}

	private static int userAdminBookQuota = -1;

	public static int getAdminBookQuota() {
		if (userAdminBookQuota != -1) return userAdminBookQuota;

		if (AppProps.valueOf("user.admin.book.quota") != null) {
			try {
				userAdminBookQuota = Integer.parseInt(AppProps.valueOf("user.admin.book.quota"));
			} catch (NumberFormatException ignore) {}
		}

		if (userAdminBookQuota == -1) {
			userAdminBookQuota = 0;
			log.warn("Using default value of {} for property 'user.admin.book.quota'.", userAdminBookQuota);
		}

		return userAdminBookQuota;
	}

	private static int unsuccessfulLoginTriesThreshold = -1;

	/**
	 * Returns maximum number of tries with incorrect credentials after which
	 * account is
	 * locked.
	 * 
	 * @return maximum number of tries with incorrect credentials after which
	 *         account is
	 *         locked
	 */
	public static int getUnsuccessfulLoginTriesThreshold() {
		if (unsuccessfulLoginTriesThreshold != -1) return unsuccessfulLoginTriesThreshold;

		if (AppProps.valueOf("user.security.login.unsuccessful.tries.threshold") != null) {
			try {
				unsuccessfulLoginTriesThreshold = Integer
						.parseInt(AppProps.valueOf("user.security.login.unsuccessful.tries.threshold"));
			} catch (NumberFormatException ignore) {}
		}

		if (unsuccessfulLoginTriesThreshold == -1) {
			unsuccessfulLoginTriesThreshold = 4;
			log.warn("Using default value of {} for property 'user.security.login.unsuccessful.tries.threshold'.",
					unsuccessfulLoginTriesThreshold);
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
		if (registraionLinkExpirationHours != -1) return registraionLinkExpirationHours;

		if (AppProps.valueOf("user.partial.registration.link.expire.period") != null) {
			try {
				registraionLinkExpirationHours = Integer
						.parseInt(AppProps.valueOf("user.partial.registration.link.expire.period"));
			} catch (NumberFormatException ignore) {}
		}

		if (registraionLinkExpirationHours == -1) {
			registraionLinkExpirationHours = 72;
			log.warn("Using default value of {} for property 'user.partial.registration.link.expire.period'.",
					registraionLinkExpirationHours);
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
		if (uiDomain != null) return uiDomain;

		if (AppProps.valueOf("app.frontend.domain") != null) {
			uiDomain = AppProps.valueOf("app.frontend.domain");
		} else {
			uiDomain = "https://localhost:4200";
			log.warn("Using default value of {} for the property 'app.frontend.domain'.", uiDomain);
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

		if (uri.startsWith("/")) return appDomain() + uri;
		else
			return appDomain() + "/" + uri;
	}

	public static String getHmacSecret() {
		String hmacSecret = AppProps.valueOf("app.security.auth.jwt.hmac.secret");

		if (hmacSecret == null) {
			hmacSecret = new String(RandomUtil.getRandomBytes(32));
			log.warn("Could not load HMAC secret key from properties. Using a generated secret key.");
		}

		return hmacSecret;
	}

	private static int bookReturnPeriodDays = -1;

	public static int getBookReturnPeriodDays() {
		if (bookReturnPeriodDays != -1) return bookReturnPeriodDays;

		if (AppProps.valueOf("book.return.period.days") != null) {
			try {
				bookReturnPeriodDays = Integer.parseInt(AppProps.valueOf("book.return.period.days"));
			} catch (NumberFormatException ignore) {}
		}

		if (bookReturnPeriodDays == -1) {
			bookReturnPeriodDays = 30;
			log.warn("Using default value of {} for property 'book.return.period.days'.", bookReturnPeriodDays);
		}

		return bookReturnPeriodDays;
	}
}
