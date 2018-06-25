package com.jk.ptk.util;

import java.util.regex.Pattern;

/**
 * Provides a number of useful patterns for validating values in the project.
 * 
 * @author Jitendra
 *
 */
public class PatternStore {
	/**
	 * Pattern to validate zip codes used in India.
	 */
	public static final Pattern ZIP_CODE_INDIA = Pattern.compile("\\d{6}");

	/**
	 * Pattern to validate a mobile number.
	 */
	public static final Pattern MOBILE = Pattern.compile("\\d{10}");

	/**
	 * Pattern to validate emails.
	 * 
	 * <p>
	 * Taken from <a href="https://www.regular-expressions.info/" target=
	 * "_blank">https://www.regular-expressions.info/</>
	 * </p>
	 */
	public static final Pattern EMAIL = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
}
