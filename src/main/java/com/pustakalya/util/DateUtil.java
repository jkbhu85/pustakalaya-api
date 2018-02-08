package com.pustakalya.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class DateUtil {
	private DateUtil() {}
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	public static String dateString(long timeInMillis) {
		return dateString(new Date(timeInMillis));
	}

	public static String dateString(Date date) {
		return DATE_FORMAT.format(date);
	}
	
	public static Date toDate(java.sql.Date sqlDate) {
		 return new Date(sqlDate.getTime());
	}
}
