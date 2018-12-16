package com.jk.ptk.f.user;

public class UserProfile {
	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private String imagePath;
	private String isdCode;
	private String mobile;
	private int bookQuota;
	private int bookAssignCount;
	private String dateOfBirth;
	private String createdOn;
	private String locale;
	private String accountStatus;

	public UserProfile() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getBookQuota() {
		return bookQuota;
	}

	public void setBookQuota(int bookQuota) {
		this.bookQuota = bookQuota;
	}

	public int getBookAssignCount() {
		return bookAssignCount;
	}

	public void setBookAssignCount(int bookAssignCount) {
		this.bookAssignCount = bookAssignCount;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

}
