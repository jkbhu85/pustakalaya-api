package com.jk.ptk.f.newuser;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import com.jk.ptk.f.user.User;
import com.jk.ptk.f.user.UserRole;

/**
 * Represents a newly added user with partial details.
 *
 * @author Jitendra
 *
 */
@Entity
@NamedQuery(name="delete_by_email", query = "delete from NewUser u where u.email=:email")
public class NewUser {
	@Id
	private String id;

	@Column(name = "emailUk", nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(name = "locale", nullable = false)
	private String localeStr;

	@Column(nullable = false)
	private Date createdOn;

	@Column(nullable = false)
	private Date expiresOn;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleFk", nullable = false)
	private UserRole role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "acCreatedByFk", nullable = false)
	private User acCreatedBy;

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

	public User getAcCreatedBy() {
		return acCreatedBy;
	}

	public void setAcCreatedBy(User acCreatedBy) {
		this.acCreatedBy = acCreatedBy;
	}

}
