package com.jk.ptk.f.newuser;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository class for {@link NewUser}.
 *
 * @author Jitendra
 *
 */
@Repository
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

	public NewUser findNewUserByEmail(String email) {
		if (email == null) return null;
		
		TypedQuery<NewUser> query = em.createNamedQuery("newUser_find_by_email", NewUser.class);
		query.setParameter("email", email);
		List<NewUser> list = query.getResultList();

		return (list.size() == 0 ? null : list.get(0));
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

	public int removeNewUser(String email) {
		Query query = em.createNamedQuery("newUser_delete_by_email");
		query.setParameter("email", email);
		return query.executeUpdate();
	}

	public void remove(NewUser newUser) {
		em.remove(newUser);
	}
}
