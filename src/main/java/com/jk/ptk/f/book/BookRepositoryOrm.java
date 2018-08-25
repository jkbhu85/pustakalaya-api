package com.jk.ptk.f.book;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

/**
 * Concrete class of the interface {@link BookRepository} that uses an ORM
 * framework.
 * 
 * @author Jitendra
 */
@Repository
public class BookRepositoryOrm implements BookRepository {
	private final EntityManager em;

	/**
	 * Creates an instance with the specified entity manager {@code em}.
	 * 
	 * @param em
	 *           the specified entity manager
	 */
	public BookRepositoryOrm(EntityManager em) {
		this.em = em;
	}

	@Override
	public void saveOrUpdate(Book book) {
		if (book.getId() == null) em.persist(book);
		else em.merge(book);
		
		em.flush();
	}

	@Override
	public Book find(Long id) {
		return em.find(Book.class, id);
	}

	@Override
	public Book findByIsbn(String isbn) {
		TypedQuery<Book> query = em.createNamedQuery("book_find_by_isbn", Book.class);
		query.setParameter("isbn", isbn);
		List<Book> list = query.getResultList();
		return (list.size() == 0 ? null : list.get(0));
	}

	@Override
	public boolean doesBookExist(Long id) {
		TypedQuery<Long> query = em.createNamedQuery("book_exists", Long.class);
		query.setParameter("id", id);
		return query.getResultList().size() == 1;
	}

}
