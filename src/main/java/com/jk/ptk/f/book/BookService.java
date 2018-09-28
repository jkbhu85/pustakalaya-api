package com.jk.ptk.f.book;

import com.jk.ptk.validation.ValidationException;

/**
 * Implementations of this interface manipulate instances of type {@link Book}.
 *
 * @author Jitendra
 */
public interface BookService {
	/**
	 * Saves the specified book in the system.
	 * 
	 * @param bookValues the specified book to save
	 * @return primary key of the book
	 * @throws ValidationException if any of the field is not valid
	 */
	Long save(BookV bookValues) throws ValidationException;

	/**
	 * Returns a book identified by the specified id.
	 * 
	 * @param bookId the specified id
	 * @return a book identified by the specified id
	 */
	Book find(String bookId);

	/**
	 * Returns a book associated with the specified {@code isbn}, {@code null}
	 * otherwise.
	 * 
	 * @param isbn the specified ISBN
	 * @return a book associated with the specified {@code isbn}, {@code null}
	 *         otherwise
	 */
	Book findByIsbn(String isbn);

	/**
	 * Returns {@code true} if a book is identified with the specified id,
	 * {@code false} otherwise.
	 * 
	 * @param bookId the specified id
	 * @return {@code true} if a book is identified with the specified id,
	 *         {@code false} otherwise
	 */
	boolean doesBookExist(String bookId);

	/**
	 * Changes the category of the book identified by id with the specified category
	 * id.
	 * 
	 * @param bookId     the specified id of the book
	 * @param categoryId the specified category id
	 * @throws ValidationException if book id or category id are not valid
	 */
	void changeCategory(String bookId, String categoryId) throws ValidationException;
}
