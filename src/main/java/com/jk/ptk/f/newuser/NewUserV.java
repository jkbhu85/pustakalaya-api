package com.jk.ptk.f.newuser;

/**
 * Represents form values as received from the front end application.
 * 
 * @author Jitendra
 */
public class NewUserV {
	public static final String FIELD_REGISTRATION_ID = "registrationId";
	public static final String FIELD_FIRST_NAME = "firstName";
	public static final String FIELD_LAST_NAME = "lastName";
	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_LOCALE = "locale";

	private String email;
	private String firstName;
	private String lastName;
	private String locale;

	public NewUserV() {
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *              the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *                  the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *                 the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *               the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

}
