package com.jk.ptk.f.bah;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.jk.ptk.f.bookinstance.BookInstance;
import com.jk.ptk.f.currency.Currency;
import com.jk.ptk.f.user.User;

/**
 * Represents an entry of book assignment history.
 *
 * @author Jitendra
 *
 */
@Entity
public class BookAssignmentHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookInstanceFk", nullable = false)
	private BookInstance bookInstance;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "issuedToFk", nullable = false)
	private User issuedTo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "issuedByFk", nullable = false)
	private User issuedBy;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="releasedByFk")
	private User releasedBy;

	@CreationTimestamp
	@Column(nullable = false)
	private LocalDate issuedOn;

	private LocalDate expectedReturnDate;

	private LocalDate returnDate;

	@Column(precision = 9, scale = 2)
	private Double amountFined;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="currencyFk")
	private Currency currency;

	private String comments;

	public BookAssignmentHistory() {
	}

	public BookInstance getBookInstance() {
		return bookInstance;
	}

	public void setBookInstance(BookInstance bookInstance) {
		this.bookInstance = bookInstance;
	}

	public User getIssuedTo() {
		return issuedTo;
	}

	public void setIssuedTo(User issuedTo) {
		this.issuedTo = issuedTo;
	}

	public User getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(User issuedBy) {
		this.issuedBy = issuedBy;
	}

	public User getReleasedBy() {
		return releasedBy;
	}

	public LocalDate getExpectedReturnDate() {
		return expectedReturnDate;
	}

	public void setExpectedReturnDate(LocalDate expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public Double getAmountFined() {
		return amountFined;
	}

	public void setAmountFined(Double amountFined) {
		this.amountFined = amountFined;
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

	public LocalDate getIssuedOn() {
		return issuedOn;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
