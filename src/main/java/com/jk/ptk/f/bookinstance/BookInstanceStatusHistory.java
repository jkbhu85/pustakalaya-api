package com.jk.ptk.f.bookinstance;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.jk.ptk.f.user.User;

/**
 * Represents an entry of book instance status history.
 *
 * @author Jitendra
 *
 */
@Entity
public class BookInstanceStatusHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="instanceFk", nullable=false)
	private BookInstance bookInstance;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="statusChangedByFk", nullable=false)
	private User statusChangedBy;

	private LocalDateTime statusChangedOn;

	@Column(nullable=false)
	private String comments;

	public BookInstanceStatusHistory() {}

	public BookInstance getBookInstance() {
		return bookInstance;
	}

	public void setBookInstance(BookInstance bookInstance) {
		this.bookInstance = bookInstance;
	}

	public User getStatusChangedBy() {
		return statusChangedBy;
	}

	public void setStatusChangedBy(User statusChangedBy) {
		this.statusChangedBy = statusChangedBy;
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

	public LocalDateTime getStatusChangedOn() {
		return statusChangedOn;
	}


}
