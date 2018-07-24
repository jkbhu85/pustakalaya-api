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

import com.jk.ptk.f.book.Book;
import com.jk.ptk.f.book.Currency;
import com.jk.ptk.f.user.User;

/**
 * Represents an instance of book.
 *
 * @author Jitendra
 *
 */
@Entity
public class BookInstance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, precision = 9, scale = 2)
	private Double price;

	private int numOfPages;

	private String publication;

	private int volume;

	private LocalDate addedOn;

	private LocalDate removedOn;

	private String comments;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookFk", nullable = false)
	private Book book;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "currencyFk", nullable = false)
	private Currency currency;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "statusFk", nullable = false)
	private BookInstanceStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "addedByFk", nullable = false)
	private User addedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "removedByFk")
	private User removedBy;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getNumOfPages() {
		return numOfPages;
	}

	public void setNumOfPages(int numOfPages) {
		this.numOfPages = numOfPages;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public LocalDate getRemovedOn() {
		return removedOn;
	}

	public void setRemovedOn(LocalDate removedOn) {
		this.removedOn = removedOn;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BookInstanceStatus getStatus() {
		return status;
	}

	public void setStatus(BookInstanceStatus status) {
		this.status = status;
	}

	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}

	public User getRemovedBy() {
		return removedBy;
	}

	public void setRemovedBy(User removedBy) {
		this.removedBy = removedBy;
	}

	public Integer getId() {
		return id;
	}

	public LocalDate getAddedOn() {
		return addedOn;
	}

}
