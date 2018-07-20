package com.jk.ptk.f.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jk.ptk.f.address.Address;
import com.jk.ptk.f.country.Country;

@Entity
@Table(name="LibUser")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="firstName")
	private String firstName;

	@Column(name="lastName")
	private String lastName;

	@Column(name="mobileUk")
	private String mobile;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="isdCodeFk")
	private Country country;

	@Column(name="emailUk")
	private String email;

	private String gender;

	@Column(name="imagePath")
	private String imagePath;

	@Column(name="dateOfBirth")
	private Date dateOfBirth;

	@Column(name="bookQuota")
	private int bookQuota;

	@Column(name="createdOn")
	private Date createdOn;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="roleFk")
	private UserRole role;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="accountStatusFk")
	private UserAcStatus accountStatus;

	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<Address> addressList = new ArrayList<>();
	
	private String localeValue;

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

	public List<Address> getAddressList() {
		return addressList;
	}

	public void addAddress(Address address) {
		addressList.add(address);
	}

	public void removeAddress(Address address) {
		addressList.remove(address);
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

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the localeValue
	 */
	public String getLocaleValue() {
		return localeValue;
	}

	/**
	 * @param localeValue the localeValue to set
	 */
	public void setLocaleValue(String localeValue) {
		this.localeValue = localeValue;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}
}
