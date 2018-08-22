package com.jk.ptk.f.bookinstance;

/**
 * Implementations of this interface interact with a storage system to
 * perform CRUD operations on the type {@link BookInstance}.
 * 
 * @author Jitendra
 */
public interface BookInstanceRepository {
	/**
	 * Saves or updates the specified book in the storage system.
	 * 
	 * @param book
	 *             the specified book to save or update
	 */
	void saveOrUpdate(BookInstance bookInstance);

	/**
	 * Returns an instance of a book that is identified by the specified id.
	 * 
	 * @param id
	 *           the specified id of the instance of the book to look for
	 * @return an instance of a book that is identified by the specified id
	 */
	BookInstance find(Long id);
}
