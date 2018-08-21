package com.jk.ptk.f.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Concrete class of the interface {@link UserRepository} that uses an ORM
 * framework.
 * 
 * @author Jitendra
 */
@Repository
class UserRepositoryOrm implements UserRepository {
	@Autowired
	private EntityManager em;

	@Override
	public User findByEmail(String email) {
		if (email == null || email.isEmpty())
			return null;

		TypedQuery<User> query = em.createNamedQuery("user_find_by_email", User.class);
		query.setParameter("email", email);
		List<User> list = query.getResultList();
		return (list.size() == 0 ? null : list.get(0));
	}

	@Override
	public boolean doesEmailExists(String email) {
		TypedQuery<Long> query = em.createNamedQuery("user_exist_by_email", Long.class);
		query.setParameter("email", email);
		long count = query.getSingleResult();
		
		return count == 1;
	}

	@Override
	public boolean doesMobileExists(String mobile) {
		TypedQuery<Long> query = em.createNamedQuery("user_mobile_exists", Long.class);
		query.setParameter("mobile", mobile);
		long count = query.getSingleResult();
		
		return count == 1;
	}

	@Override
	public void saveOrUpdate(User user) {
		if (user.getId() != null) {
			em.merge(user);
		} else {
			em.persist(user);
		}
	}
	
}
