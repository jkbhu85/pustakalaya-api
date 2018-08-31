package com.jk.ptk.f.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Profile {
	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private String imagePath;
	private String isdCode;
	private String mobile;
	private int bookQuota;
	private LocalDate dateOfBirth;
	private LocalDateTime createdOn;
	private String locale;

	/**
	 * Creates an instance with values from the specified user.
	 * 
	 * @param user
	 *             the specified user
	 * 
	 * @throws NullPointerException
	 *                              if the specified user is {@code null}
	 */
	public Profile(User user) {
		replicate(user);
	}

	private void replicate(User user) {
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.gender = user.getGender();
		this.imagePath = user.getImagePath();
		this.isdCode = user.getCountry().getIsdCode();
		this.mobile = user.getMobile();
		this.bookQuota = user.getBookQuota();
		this.dateOfBirth = user.getDateOfBirth();
		this.createdOn = user.getCreatedOn();
		this.locale = user.getLocaleValue();
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @return the isdCode
	 */
	public String getIsdCode() {
		return isdCode;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @return the bookQuota
	 */
	public int getBookQuota() {
		return bookQuota;
	}

	/**
	 * @return the dateOfBirth
	 */
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @return the createdOn
	 */
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

}
