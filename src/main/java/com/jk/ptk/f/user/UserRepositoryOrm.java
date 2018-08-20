package com.jk.ptk.f.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
public class UserRepositoryOrm implements UserRepository {
	@Autowired
	private EntityManager em;

	@Override
	public void save(User user) {
		em.persist(user);
	}

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
		Query query = em.createNamedQuery("user_exist_by_email");
		query.setParameter("email", email);
		return query.getResultList().size() == 1;
	}

	@Override
	public boolean doesMobileExists(String mobile) {
		Query query = em.createNamedQuery("user_mobile_exists");
		query.setParameter("mobile", mobile);
		return query.getResultList().size() == 1;
	}

	@Override
	public void updatePassword(String email, String passwordHash, String passwordSalt, Integer passwordVersion) {
		Query query = em.createNamedQuery("user_update_password");
		query.setParameter("email", email);
		query.setParameter("passwordHash", passwordHash);
		query.setParameter("passwordSalt", passwordSalt);
		query.setParameter("passwordVersion", passwordVersion);
		query.executeUpdate();
	}

	@Override
	public void updateSecurityQuestionAndAnswer(String email, String question, String answer) {
		Query query = em.createNamedQuery("user_update_security_question");
		query.setParameter("email", email);
		query.executeUpdate();
	}

	@Override
	public void updateUnsuccessfulTries(String email, Integer tries) {
		Query query = em.createNamedQuery("user_update_unsuccessful_tries");
		query.setParameter("email", email);
		query.setParameter("unsuccessfulTries", tries);
		query.executeUpdate();
	}
}
