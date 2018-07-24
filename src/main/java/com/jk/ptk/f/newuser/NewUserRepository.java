package com.jk.ptk.f.newuser;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository class for {@link NewUser}.
 *
 * @author Jitendra
 *
 */
@Repository
@Transactional
public class NewUserRepository {
	@Autowired
	private EntityManager em;

	/**
	 * Returns user associated with {@code id}.
	 *
	 * @param id
	 *            the specified id
	 * @return user associated with {@code id}
	 */
	public NewUser findNewUser(String id) {
		return em.find(NewUser.class, id);
	}

	/**
	 * Saves {@code newUser} to database.
	 *
	 * @param newUser
	 *            the specified new user to save
	 */
	public void saveNewUser(NewUser newUser) {
		em.persist(newUser);
	}

	public void removeNewUser(String email) {
		TypedQuery<NewUser> query = em.createNamedQuery("delete_by_email", NewUser.class);
		query.setParameter(0, email);
		query.executeUpdate();
	}
}
