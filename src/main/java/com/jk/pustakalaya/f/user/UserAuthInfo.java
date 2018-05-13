package com.jk.pustakalaya.f.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.jk.pustakalaya.model.UserAcStatus;
import com.jk.pustakalaya.model.UserRole;

@Entity
@Table(name="LibUser")
@NamedQuery(name="find_by_email", query="select u from UserAuthInfo u where u.id=?")
public class UserAuthInfo {
	@Id
	private Long id;

	@Column(name="emailUk")
	private String email;

	@Column(name="passwordHash")
	private String passwordHash;

	@Column(name="passwordSalt")
	private String passwordSalt;

	@Column(name="passwordVersion")
	private int passwordVersion;

	@Column(name="securityQuestion")
	private String securityQuestion;

	@Column(name="securityAnswer")
	private String securityAnswer;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="roleFk")
	private UserRole role;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="accountStatusFk")
	private UserAcStatus accountStatus;


	public UserAuthInfo() {}


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


	public String getAccountStatus() {
		return accountStatus.getName();
	}


	public void setAccountStatus(UserAcStatus accountStatus) {
		this.accountStatus = accountStatus;
	}


	public Long getId() {
		return id;
	}


	@Override
	public String toString() {
		return "UserAuthInfo [id=" + id + ", email=" + email + ", passwordHash=" + passwordHash + ", passwordSalt="
				+ passwordSalt + ", passwordVersion=" + passwordVersion + ", securityQuestion=" + securityQuestion
				+ ", securityAnswer=" + securityAnswer + ", role=" + role.getName() + ", accountStatus=" + accountStatus.getName() + "]";
	}

}
