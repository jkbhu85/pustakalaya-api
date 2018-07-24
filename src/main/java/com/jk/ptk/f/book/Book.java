package com.jk.ptk.f.book;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.jk.ptk.f.user.User;

/**
 * Represents a book in database.
 *
 * @author Jitendra
 *
 */
@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String title;

	private String authors;

	private int editon;

	private String isbn;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryFk", nullable = false)
	private BookCategory bookCategory;

	@Column(nullable = false)
	private LocalDate addedOn;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "addedByFk", nullable = false)
	private User addedBy;

	public Book() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public int getEditon() {
		return editon;
	}

	public void setEditon(int editon) {
		this.editon = editon;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public BookCategory getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(BookCategory bookCategory) {
		this.bookCategory = bookCategory;
	}

	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}

	public Integer getId() {
		return id;
	}

	public LocalDate getAddedOn() {
		return addedOn;
	}

}
