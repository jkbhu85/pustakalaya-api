package com.jk.ptk.f.uash;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.jk.ptk.f.user.User;
import com.jk.ptk.f.user.UserAcStatus;

/**
 * Represents one entry of user status history.
 *
 * @author Jitendra
 *
 */
@Entity
public class UserAccountStatusHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userAccountStatusFk", nullable = false)
	private UserAcStatus accountStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userFk", nullable = false)
	private User user;

	private LocalDate createdOn;

	@Column(nullable = false)
	private String comments;

	public UserAccountStatusHistory() {
	}

	public UserAcStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(UserAcStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}
}
