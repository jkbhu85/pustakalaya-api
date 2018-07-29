package com.jk.ptk;

import java.util.Locale;

import com.jk.ptk.util.LocaleUtil;

public class Test {
	public static void main(String[] args) {
		String[] locales = {
				"hi_IN",
				"en_IN",
				"bn_IN",
				"te_IN"
		};
		try {
			for (String l: locales) {
				Locale locale = LocaleUtil.from(l);
				System.out.format("locale: %s, language: %s, country: %s\n", l, locale.getDisplayLanguage(), locale.getDisplayCountry());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
