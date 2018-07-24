package com.jk.ptk.f.bookinstance;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.jk.ptk.f.user.LightUser;
import com.jk.ptk.f.user.User;

/**
 * Represents an entry of book instance history.
 *
 * @author Jitendra
 *
 */
@Entity
public class BookInstanceHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="instanceFk", nullable=false)
	private BookInstance bookInstance;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="statusChangedByFk", nullable=false)
	private LightUser statusChangedBy;

	private LocalDate statusChangedOn;

	@Column(nullable=false)
	private String comments;

	public BookInstanceHistory() {}

	public BookInstance getBookInstance() {
		return bookInstance;
	}

	public void setBookInstance(BookInstance bookInstance) {
		this.bookInstance = bookInstance;
	}

	public LightUser getStatusChangedBy() {
		return statusChangedBy;
	}

	public void setStatusChangedBy(LightUser statusChangedBy) {
		this.statusChangedBy = statusChangedBy;
	}

	public void setStatusChangedBy(User statusChangedBy) {
		this.statusChangedBy = LightUser.fromUser(statusChangedBy);
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

	public LocalDate getStatusChangedOn() {
		return statusChangedOn;
	}


}
