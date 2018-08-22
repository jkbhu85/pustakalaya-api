package com.jk.ptk.f.book;

/**
 * Implementations of this interface interact with a storage system to
 * perform CRUD operations on the type {@link Book}.
 * 
 * @author Jitendra
 */
public interface BookRepository {
	/**
	 * Saves or updates specified book in the storage system.
	 * 
	 * @param book
	 *             the specified book to save or update
	 */
	void saveOrUpdate(Book book);

	/**
	 * Returns the book identified by the specified {@code id}.
	 * 
	 * @param id
	 *           the specified id
	 * @return the book identified by the specified {@code id}
	 */
	Book find(Long id);

	/**
	 * Returns the book associated with the specified {@code isbn}.
	 *
	 * @param isbn
	 *             the specified ISBN
	 * @return the book associated with the specified {@code isbn}
	 */
	Book findByIsbn(String isbn);

	/**
	 * Returns {@code true} if a book is identified with the specified {@code id},
	 * {@code false} otherwise.
	 * 
	 * @param id
	 *           the specified id
	 * @return {@code true} if a book is identified with the specified {@code id},
	 *         {@code false} otherwise
	 */
	boolean doesBookExist(Long id);
}
