package com.jk.pustakalaya.f.newuser;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jk.pustakalaya.model.UserRole;

/**
 * Represents a newly added user to the system. This user has only few details
 * about him/her and needs to complete registration process before he/she can
 * use the system.
 * 
 * @author Jitendra
 *
 */
@Entity
@Table(name = "NewUser")
public class NewUser {
	@Id
	private String id;

	private String email;
	private String firstName;
	private String lastName;
	private String localeStr;
	private Date createdOn;
	private Date expiresOn;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleFk")
	private UserRole role;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "acCreatedByFk")
	private Long acCreatedBy;

	/**
	 * Default constructor.
	 */
	public NewUser() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

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

	public String getLocaleStr() {
		return localeStr;
	}

	public void setLocaleStr(String localeStr) {
		this.localeStr = localeStr;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(Date expiresOn) {
		this.expiresOn = expiresOn;
	}

	public Long getAcCreatedBy() {
		return acCreatedBy;
	}

	public void setAcCreatedBy(Long acCreatedBy) {
		this.acCreatedBy = acCreatedBy;
	}

}
