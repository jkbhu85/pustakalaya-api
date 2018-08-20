package com.jk.ptk.f.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.jk.ptk.f.country.Country;

@Entity
@Table(name = "LibUser")
@NamedQueries({
	@NamedQuery(name = "user_find_by_email", query = "SELECT u FROM User u WHERE u.email=:email"),
	@NamedQuery(name = "user_exist_by_email", query = "SELECT COUNT(u) FROM User u WHERE u.email=:email"),
	@NamedQuery(name = "user_mobile_exists", query = "SELECT COUNT(u) FROM User u WHERE u.mobile=:mobile"),
	@NamedQuery(name = "user_update_password", query = "UPDATE User u SET u.passwordHash=:passwordHash, u.passwordSalt=:passwordSalt,u.passwordVersion=:passwordVersion WHERE u.email=:email"),
	@NamedQuery(name = "user_update_security_question", query = "UPDATE User u SET u.securityQuestion=:securityQuestion, u.securityAnswer=:securityAnswer WHERE email=:email"),
	@NamedQuery(name = "user_update_unsuccessful_tries", query = "UPDATE User u SET u.unsuccessfulTries=:unsuccessfulTries WHERE email=:email")
})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;

	private String lastName;

	@Column(name = "mobileUk", unique = true)
	private String mobile;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "isdCodeFk")
	private Country country;

	@Column(name = "emailUk", nullable = false, unique = true)
	private String email;

	private String gender;

	private String imagePath;

	private LocalDate dateOfBirth;

	private int bookQuota;

	private LocalDateTime createdOn;

	@Column(name = "locale")
	private String localeValue;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleFk", nullable = false)
	private UserRole role;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "accountStatusFk", nullable = false)
	private UserAcStatus accountStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "acCreatedByFk")
	private User acCreatedBy;

	private String passwordHash;
	private String passwordSalt;
	private Integer passwordVersion;
	private Integer unsuccessfulTries;
	private String securityQuestion;
	private String securityAnswer;

	public User() {
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getImgPath() {
		return imagePath;
	}

	public void setImgPath(String imgPath) {
		this.imagePath = imgPath;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getBookQuota() {
		return bookQuota;
	}

	public void setBookQuota(int bookQuota) {
		this.bookQuota = bookQuota;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public Long getId() {
		return id;
	}

	public UserRole getRole() {
		return role;
	}

	public UserAcStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(UserAcStatus status) {
		accountStatus = status;
	}

	public String getIsdCode() {
		return country.getIsdCode();
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLocaleValue() {
		return localeValue;
	}

	public void setLocaleValue(String localeValue) {
		this.localeValue = localeValue;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @param acCreatedBy the acCreatedBy to set
	 */
	public void setAcCreatedBy(User acCreatedBy) {
		this.acCreatedBy = acCreatedBy;
	}

	/**
	 * @return the passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @return the passwordSalt
	 */
	public String getPasswordSalt() {
		return passwordSalt;
	}

	/**
	 * @param passwordSalt the passwordSalt to set
	 */
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	/**
	 * @return the passwordVersion
	 */
	public Integer getPasswordVersion() {
		return passwordVersion;
	}

	/**
	 * @param passwordVersion the passwordVersion to set
	 */
	public void setPasswordVersion(Integer passwordVersion) {
		this.passwordVersion = passwordVersion;
	}

	/**
	 * @return the unsuccessfulTries
	 */
	public Integer getUnsuccessfulTries() {
		return unsuccessfulTries;
	}

	/**
	 * @param unsuccessfulTries the unsuccessfulTries to set
	 */
	public void setUnsuccessfulTries(Integer unsuccessfulTries) {
		this.unsuccessfulTries = unsuccessfulTries;
	}

	/**
	 * @return the securityQuestion
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * @param securityQuestion the securityQuestion to set
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
	 * @param securityAnswer the securityAnswer to set
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	/**
	 * @return the acCreatedBy
	 */
	public User getAcCreatedBy() {
		return acCreatedBy;
	}



}
