package com.jk.ptk.f.urh;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Concrete class of the interface {@link UserRoleHistoryRepository} that
 * uses an ORM framework.
 * 
 * @author Jitendra
 */
@Repository
public class UserRoleHistoryRepositoryOrm implements UserRoleHistoryRepository {
	private EntityManager em;

	@Autowired
	public UserRoleHistoryRepositoryOrm(EntityManager em) {
		this.em = em;
	}

	@Override
	public void save(UserRoleHistory urh) {
		if (urh == null)
			return;

		em.persist(urh);
	}

	@Override
	public List<UserRoleHistory> getAll(String email) {
		if (email == null)
			return new ArrayList<>();

		TypedQuery<UserRoleHistory> query = em.createNamedQuery("urh_find_all_by_email", UserRoleHistory.class);
		query.setParameter("email", email);

		return query.getResultList();
	}

}
