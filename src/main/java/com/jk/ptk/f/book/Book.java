package com.jk.ptk.f.book;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import com.jk.ptk.f.user.User;

/**
 * Represents a book.
 *
 * @author Jitendra
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "book_exists", query = "SELECT COUNT(*) FROM Book b WHERE b.id=:id"),
		@NamedQuery(name = "book_find_by_isbn", query = "SELECT b FROM Book b WHERE b.isbn=:isbn")
})
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String title;

	private String authors;

	private int edition;

	private String isbn;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryFk", nullable = false)
	private BookCategory bookCategory;

	@Column(nullable = false)
	private LocalDateTime addedOn;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "addedByFk", nullable = false)
	private User addedBy;

	public Book() {}

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

	public int getEdition() {
		return edition;
	}

	public void setEdition(int editon) {
		this.edition = editon;
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

	public LocalDateTime getAddedOn() {
		return addedOn;
	}

}
