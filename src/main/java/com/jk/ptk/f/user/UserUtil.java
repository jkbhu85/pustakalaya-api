package com.jk.ptk.f.user;

import java.util.HashMap;
import java.util.Map;

public final class UserUtil {
	private UserUtil() {
	}

	/**
	 * Returns a map of email, first name, last name, gender, image path,
	 * ISD code, mobile, book quota, date of birth, account creation time
	 * and locale form the the specified user.
	 * 
	 * @param user
	 *             the specified user
	 * @return a map of user attributes
	 * @throws NullPointerException
	 *                              if the specified user is {@code null}
	 */
	public static Map<String, String> toProfile(User user) {
		Map<String, String> profile = new HashMap<>();

		profile.put(UserV.FIELD_EMAIL, user.getEmail());
		profile.put(UserV.FIELD_FIRST_NAME, user.getFirstName());
		profile.put(UserV.FIELD_LAST_NAME, user.getLastName());
		profile.put(UserV.FIELD_GENDER, user.getGender());
		profile.put(UserV.FIELD_IMAGE_PATH, user.getImagePath());
		profile.put(UserV.FIELD_ISD_CODE, user.getCountry().getIsdCode());
		profile.put(UserV.FIELD_MOBILE, user.getMobile());
		profile.put(UserV.FIELD_BOOK_QUOTA, String.valueOf(user.getBookQuota()));
		profile.put(UserV.FIELD_DATE_OF_BIRTH, user.getDateOfBirth().toString());
		profile.put(UserV.FIELD_CREATED_ON, user.getCreatedOn().toString());
		profile.put(UserV.FIELD_LOCALE, user.getLocaleValue());

		return profile;
	}
}
