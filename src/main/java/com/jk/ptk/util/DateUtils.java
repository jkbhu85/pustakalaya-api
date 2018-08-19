package com.jk.ptk.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
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

	public static LocalDate parseDate(String dateStr) {
		if (dateStr == null || dateStr.isEmpty())
			return null;

		String[] multiple = dateStr.split("-");

		if (multiple.length != 3)
			return null;

		try {
			int dayOfMonth = Integer.parseInt(multiple[0]);
			int month = Integer.parseInt(multiple[1]);
			int year = Integer.parseInt(multiple[2]);

			return LocalDate.of(year, month, dayOfMonth);
		} catch (NumberFormatException | DateTimeException ignore) {
		}

		return null;
	}
}
