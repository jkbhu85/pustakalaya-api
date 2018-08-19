package com.jk.ptk.f.user;

/**
 * Represents user values as received from front end.
 *
 * @author Jitendra
 *
 */
public class UserFormValues {
	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_FIRST_NAME = "firstName";
	public static final String FIELD_LAST_NAME = "lastName";
	public static final String FIELD_GENDER = "gender";
	public static final String FIELD_DATE_OF_BIRTH = "dateOfBirth";
	public static final String FIELD_MOBILE = "mobile";
	public static final String FIELD_ISD_CODE = "isdCode";
	public static final String FIELD_LOCALE = "locale";
	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_CONFIRM_PASSWORD = "confirmPassword";
	public static final String FIELD_SECURITY_QUESTION = "securityQuestion";
	public static final String FIELD_SECURITY_ANSWER = "securityAnswer";

	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private String dateOfBirth;
	private String locale;
	private String isdCode;
	private String mobile;
	private String password;
	private String confirmPassword;
	private String securityQuestion;
	private String securityAnswer;

	public UserFormValues() {
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
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
	 *            the firstName to set
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
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return the isdCode
	 */
	public String getIsdCode() {
		return isdCode;
	}

	/**
	 * @param isdCode
	 *            the isdCode to set
	 */
	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the securityQuestion
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * @param securityQuestion
	 *            the securityQuestion to set
	 */
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	/**
	 * @return the securityAnswer
	 */
	public String getSecurityAnswer() {
		return securityAnswer;
	}

	/**
	 * @param securityAnswer
	 *            the securityAnswer to set
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserFormValues [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", gender="
				+ gender + ", dateOfBirth=" + dateOfBirth + ", locale=" + locale + ", isdCode=" + isdCode + ", mobile="
				+ mobile + ", password=" + password + ", confirmPassword=" + confirmPassword + ", securityQuestion="
				+ securityQuestion + ", securityAnswer=" + securityAnswer + "]";
	}

}
