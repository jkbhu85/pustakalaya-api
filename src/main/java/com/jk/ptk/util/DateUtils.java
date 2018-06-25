package com.jk.ptk.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class provides methods to manipulate dates.
 *
 * @author Jitendra
 *
 */
public final class DateUtils {
	private DateUtils() {
	}

	public static final String DEFAULT_DATE_SEPARTOR = "/";
	public static final String DEFAULT_DATE_PATTERN = "dd" + DEFAULT_DATE_SEPARTOR + "MM" + DEFAULT_DATE_SEPARTOR
			+ "yyyy";
	public static final DateFormat FORMAT_DDMMYYYY = new SimpleDateFormat(DEFAULT_DATE_PATTERN);

	public static String dateString(long timeInMillis) {
		return dateString(new Date(timeInMillis));
	}

	public static String dateString(Date date) {
		return FORMAT_DDMMYYYY.format(date);
	}

	public static Date toUtilDate(java.sql.Date sqlDate) {
		return new Date(sqlDate.getTime());
	}
}
