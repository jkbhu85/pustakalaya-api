package com.jk.ptk.f.uash;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Concrete class of the interface {@link UserAccountStatusHistoryRepository} that
 * uses an ORM framework.
 * 
 * @author Jitendra
 */
@Repository
public class UserAccountStatusHistoryRepositoryOrm implements UserAccountStatusHistoryRepository {

	private EntityManager em;

	@Autowired
	public UserAccountStatusHistoryRepositoryOrm(EntityManager em) {
		this.em = em;
	}

	@Override
	public void save(UserAccountStatusHistory uash) {
		if (uash == null)
			return;
		em.persist(uash);
	}

	@Override
	public List<UserAccountStatusHistory> getAll(String email) {
		if (email == null)
			return new ArrayList<>();

		TypedQuery<UserAccountStatusHistory> query = em.createNamedQuery("uash_find_all_by_email",
				UserAccountStatusHistory.class);
		query.setParameter("email", email);

		return query.getResultList();
	}
}
