package com.jk.ptk.f.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "LibUser")
@NamedQuery(name = "find_by_email", query = "select u from UserAuthInfo u where u.email=:email")
public class UserAuthInfo {
	@Id
	private Long id;

	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "emailUk", nullable = false, unique = true)
	private String email;

	@Column(name = "unsuccessfulTries")
	private int unsuccessfulTries;

	@Column(name = "passwordHash", nullable = false)
	private String passwordHash;

	@Column(name = "passwordSalt", nullable = false)
	private String passwordSalt;

	@Column(name = "passwordVersion")
	private int passwordVersion;

	@Column(name = "securityQuestion", nullable = false)
	private String securityQuestion;

	@Column(name = "securityAnswer", nullable = false)
	private String securityAnswer;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleFk", nullable = false)
	private UserRole role;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "accountStatusFk", nullable = false)
	private UserAcStatus accountStatus;

	@Column(name = "locale", nullable = false)
	private String localeValue;

	public UserAuthInfo() {
	}

	public String getFirstName() {
		return firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public int getPasswordVersion() {
		return passwordVersion;
	}

	public void setPasswordVersion(int passwordVersion) {
		this.passwordVersion = passwordVersion;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getRole() {
		return role.getName();
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public UserAcStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(UserAcStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Long getId() {
		return id;
	}

	/**
	 * @return the unsuccessfulTries
	 */
	public int getUnsuccessfulTries() {
		return unsuccessfulTries;
	}

	/**
	 * @param unsuccessfulTries
	 *            the unsuccessfulTries to set
	 */
	public void setUnsuccessfulTries(int unsuccessfulTries) {
		this.unsuccessfulTries = unsuccessfulTries;
	}

	/**
	 * @return the localeValue
	 */
	public String getLocaleValue() {
		return localeValue;
	}

	/**
	 * @param localeValue
	 *            the localeValue to set
	 */
	public void setLocaleValue(String localeValue) {
		this.localeValue = localeValue;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "UserAuthInfo [id=" + id + ", firstName=" + firstName + ", email=" + email + ", unsuccessfulTries=" + unsuccessfulTries
				+ ", passwordVersion=" + passwordVersion + ", role=" + role.getName() + ", accountStatus=" + accountStatus.getName()
				+ ", localeValue=" + localeValue + "]";
	}

}
