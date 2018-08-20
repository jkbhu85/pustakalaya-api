package com.jk.ptk.f.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository for the type {@link User}.
 * 
 * @author Jitendra
 *
 */
@Repository
public class UserRepository {
	@Autowired
	private EntityManager em;

	@Transactional(rollbackOn=Exception.class)
	public void addUser(User user) {
		em.persist(user);
	}

	public User findUser(String email) {
		if (email == null || email.isEmpty())
			return null;

		TypedQuery<User> query = em.createNamedQuery("user_find_by_email", User.class);
		query.setParameter("email", email);
		List<User> list = query.getResultList();

		return (list.size() == 0 ? null : list.get(0));
	}

	public boolean userExists(String email) {
		Query query = em.createNamedQuery("user_exist_by_email");
		query.setParameter("email", email);
		final int result = ((Number) query.getSingleResult()).intValue();

		return result == 1;
	}

	public boolean mobileExists(String mobile) {
		Query query = em.createNamedQuery("user_mobile_exists");
		query.setParameter("mobile", mobile);
		final int result = ((Number) query.getSingleResult()).intValue();

		return result == 1;
	}

	public void updatePassword(String email, String passwordHash, String passwordSalt, Integer passwordVersion) {
		Query query = em.createNamedQuery("user_update_password");
		query.setParameter("email", email);
		query.setParameter("passwordHash", passwordHash);
		query.setParameter("passwordSalt", passwordSalt);
		query.setParameter("passwordVersion", passwordVersion);
	}

	public void updateSecurityQuestion(String email, String question, String answer) {
		Query query = em.createNamedQuery("user_update_security_question");
		query.setParameter("email", email);
	}

	public void updateUnsuccessfulTries(String email, Integer tries) {
		Query query = em.createNamedQuery("user_update_unsuccessful_tries");
		query.setParameter("email", email);
		query.setParameter("unsuccessfulTries", tries);
	}
}
