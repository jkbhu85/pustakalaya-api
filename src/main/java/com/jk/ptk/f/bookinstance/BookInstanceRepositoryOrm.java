package com.jk.ptk.f.bookinstance;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Concrete class of the interface {@link BookInstanceRepository} that uses an
 * ORM framework.
 * 
 * @author Jitendra
 */
@Repository
public class BookInstanceRepositoryOrm implements BookInstanceRepository {
	private final EntityManager em;

	@Autowired
	public BookInstanceRepositoryOrm(EntityManager em) {
		this.em = em;
	}

	@Override
	public void saveOrUpdate(BookInstance bookInstance) {
		if (bookInstance.getId() == null) {
			em.persist(bookInstance);
		} else {
			em.merge(bookInstance);
		}
	}

	@Override
	public BookInstance find(Long id) {
		return em.find(BookInstance.class, id);
	}

}
