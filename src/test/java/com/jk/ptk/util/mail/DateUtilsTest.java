package com.jk.ptk.util.mail;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.jk.ptk.util.DateUtils;

public class DateUtilsTest {
	// day, month, year, hour, minute, second
	// january is 0
	private static final int[][] TEST_CASES = {
			{ 2018, 5, 1, 0, 0, 0 },
			{ 2018, 4, 31, 0, 0, 0 },
			{ 2018, 4, 12, 0, 0, 0 },
			{ 2018, 1, 1, 0, 0, 0 },
			{ 2018, 12, 1, 0, 0, 1 }
		};

	private static final String DATE_SEPARATOR = DateUtils.DEFAULT_DATE_SEPARTOR;

	private String toDateString(Calendar cal) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH) + 1;
		String str = "";

		if (day < 10)
			str += "0" + day;
		else
			str += day;

		str += DATE_SEPARATOR;

		if (month < 10)
			str += "0" + month;
		else
			str += month;

		str += DATE_SEPARATOR;

		str += cal.get(Calendar.YEAR);

		return str;
	}

	@Test
	public void dateStringMillis() {
		Calendar cal = Calendar.getInstance();
		int year, month, day, hour, minute, second;

		for (int i = 0; i < TEST_CASES.length; i++) {
			int[] row = TEST_CASES[i];
			year = row[0];
			month = row[1];
			day = row[2];
			hour = row[3];
			minute = row[4];
			second = row[5];

			cal.set(year, month, day, hour, minute, second);

			String str = DateUtils.dateString(cal.getTimeInMillis());
			String expectedStr = toDateString(cal);

			assertEquals(expectedStr, str);
		}
	}

	@Test
	public void dateString() {
		Calendar cal = Calendar.getInstance();
		int year, month, day, hour, minute, second;

		for (int i = 0; i < TEST_CASES.length; i++) {
			int[] row = TEST_CASES[i];
			year = row[0];
			month = row[1];
			day = row[2];
			hour = row[3];
			minute = row[4];
			second = row[5];

			cal.set(year, month, day, hour, minute, second);

			String str = DateUtils.dateString(cal.getTime());
			String expectedStr = toDateString(cal);

			assertEquals(expectedStr, str);
		}
	}

	@Test
	public void toUtilDate() {
		java.sql.Date expectedDate = new java.sql.Date(System.currentTimeMillis());

		Date actualDate = DateUtils.toUtilDate(expectedDate);

		assertEquals(expectedDate.getTime(), actualDate.getTime());
	}
}
