package com.jk.ptk.f.urh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository for the type {@link UserRoleHistoryRepository}.
 * 
 * @author Jitendra
 *
 */
@Repository
public class UserRoleHistoryRepository {
	private EntityManager em;

	@Autowired
	public UserRoleHistoryRepository(EntityManager em) {
		this.em = em;
	}

	public void addRoleHistory(UserRoleHistory urh) {
		em.persist(urh);
	}

	public List<UserRoleHistory> getRoleHistory(String email) {
		TypedQuery<UserRoleHistory> query = em.createNamedQuery("urh_find_all_by_email", UserRoleHistory.class);
		query.setParameter("email", email);

		return query.getResultList();
	}

}
