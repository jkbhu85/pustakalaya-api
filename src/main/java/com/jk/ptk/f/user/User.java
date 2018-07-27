package com.jk.ptk.f.user;

import java.util.Date;

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
@NamedQueries(
	@NamedQuery(name = "if_user_exist_by_email", query = "select COUNT(u) from User u where u.email=:email")
)
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

	private Date dateOfBirth;

	private int bookQuota;

	private Date createdOn;

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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getBookQuota() {
		return bookQuota;
	}

	public void setBookQuota(int bookQuota) {
		this.bookQuota = bookQuota;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Long getId() {
		return id;
	}

	public String getRole() {
		return role.getName();
	}

	public String getAccountStatus() {
		return accountStatus.getName();
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
}
