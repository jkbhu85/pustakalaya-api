package com.jk.pustakalaya.util;

import java.util.Locale;

/**
 * This class is used to get and set locale for current user which can be used
 * by other parts of the application. It also provides the default locale for
 * the application and what locales are supported by the application.
 *
 *
 * @author Jitendra
 *
 */
public class UserLocale {
	private static final ThreadLocal<Locale> mThreadLocal = new ThreadLocal<>();

	/**
	 * Locale for Hindi-India.
	 */
	public static final Locale HI_IN = new Locale("hi", "IN");

	/**
	 * An array of locales supported by this application.
	 */
	public static final Locale[] SUPPORTED_LOCALES = { Locale.US, HI_IN };

	/**
	 * Default locale of this application.
	 */
	public static final Locale DEFAULT_LOCALE = SUPPORTED_LOCALES[1];

	/**
	 * Returns a locale object either with language or with language and country
	 * provided in {@code localeStr}. Returns {@code null} if {@code localeStr} is
	 * {@code null} or empty.
	 *
	 * @param localeStr
	 *            string containing language and country(optional) separated by '-'
	 *            or '_'
	 * @return a locale object either with language or with language and country
	 *         provided in {@code localeStr} or {@code null} if {@code localeStr} is
	 *         {@code null} or empty
	 * @throws RuntimeException
	 *             if {@code localeStr} contains more than one separators (i.e. '-'
	 *             or '_')
	 */
	public static Locale toLocale(String localeStr) throws RuntimeException {
		if (localeStr == null || localeStr.isEmpty())
			return null;

		String[] parts = localeStr.split("[-_]");

		if (parts.length > 2) {
			throw new RuntimeException("Invalid locale string: " + localeStr);
		}

		Locale l = null;

		if (parts.length == 1) {
			l = new Locale(parts[0]);
		} else {
			l = new Locale(parts[0], parts[1]);
		}

		return l;
	}

	/**
	 * Sets locale for current user as specified in {@code localeStr}.
	 *
	 * @param localeStr
	 *            string containing language and country(optional) separated by '-'
	 *            or '_'
	 * @throws RuntimeException
	 *             if {@code localeStr} contains more than one separators (i.e. '-'
	 *             or '_')
	 *
	 * @see {@link #toLocale(String)}
	 */
	public static void setUserLocale(String localeStr) throws RuntimeException {
		setUserLocale(toLocale(localeStr));
	}

	/**
	 * Sets the specified locale for current user. If locale is not supported then
	 * sets {@link #DEFAULT_LOCALE} as locale for current user.
	 *
	 * @param locale
	 *            the specified locale for current user
	 */
	public static void setUserLocale(Locale locale) {
		boolean supported = isLocaleSupported(locale);

		if (!supported) {
			locale = DEFAULT_LOCALE;
		}

		mThreadLocal.set(locale);
	}

	/**
	 * Returns locale for the current user.
	 *
	 * <p>
	 * If this method is called after {@link #removeUserLocale()} then
	 * {@link #DEFAULT_LOCALE} is returned.
	 * </p>
	 *
	 * @return locale for the current user
	 */
	public static Locale getUserLocale() {
		Locale l = mThreadLocal.get();

		return (l == null ? null : DEFAULT_LOCALE);
	}

	/**
	 * Removes locale for current user.
	 */
	public static void removeUserLocale() {
		mThreadLocal.remove();
	}

	/**
	 * Returns {@code true} if the specified locale is supported by the application.
	 *
	 * @param l
	 *            the specified locale to check if it is supported by the
	 *            application
	 * @return {@code true} if the specified locale is supported by the application
	 */
	public static boolean isLocaleSupported(Locale l) {
		if (l == null)
			return false;

		for (Locale locale : SUPPORTED_LOCALES) {
			if (locale.equals(l))
				return true;
		}

		return false;
	}
}
