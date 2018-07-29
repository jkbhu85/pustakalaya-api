package com.jk.ptk.util;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.jk.ptk.app.DataLayerInitialized;
import com.jk.ptk.model.SupportedLocale;

@DataLayerInitialized
public final class LocaleUtil {
	// prevent instantiation
	private LocaleUtil() {
	}

	private static final Set<String> supportedLocaleNameSet = new HashSet<>();
	private static final Set<Locale> supportedLocaleSet = new HashSet<>();

	@SuppressWarnings("unused")
	private static void init(EntityManager em) {
		final String jpql = "select l from SupportedLocale l";
		TypedQuery<SupportedLocale> query = em.createQuery(jpql, SupportedLocale.class);

		for (SupportedLocale sl : query.getResultList())
			supportedLocaleNameSet.add(sl.getName());

		for (String ln : supportedLocaleNameSet)
			supportedLocaleSet.add(from(ln));
	}

	/**
	 * Returns {@code true} if the specified locale value is supported by the
	 * application.
	 *
	 * @param value the specified locale value
	 * @return {@code true} if the specified locale value is supported by the
	 * application.
	 */
	public static boolean isSupported(final String value) {
		for (String ln : supportedLocaleNameSet) {
			if (ln.equals(value))
				return true;
		}

		return false;
	}

	public static boolean isSupported(final Locale locale) {
		for (Locale l : supportedLocaleSet) {
			if (l.equals(locale))
				return true;
		}

		return false;
	}

	/**
	 * Returns an instance of {@link Locale} by parsing the specified string with
	 * language and country in the format language_COUNTRY where both language and
	 * country are two character long. Returns {@code null} if the specified string
	 * is {@code null}.
	 *
	 * @param languageCountry
	 *            the specified string to parse
	 * @return an instance of {@link Locale}
	 */
	public static Locale from(final String languageCountry) {
		if (languageCountry == null)
			return null;

		String[] multiple = languageCountry.split("[_]");

		switch (multiple.length) {
		case 2:
			return new Locale(multiple[0].toLowerCase(), multiple[1].toUpperCase());
		case 1:
			return new Locale(multiple[0].toLowerCase());
		default:
			return new Locale(multiple[0].toLowerCase());
		}
	}
}
