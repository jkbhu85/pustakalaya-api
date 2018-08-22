package com.jk.ptk.f.newuser;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Concrete class of the interface {@link NewUserRepository} that uses an ORM
 * framework.
 * 
 * @author Jitendra
 */
@Repository
class NewUserRepositoryOrm implements NewUserRepository {
	private EntityManager em;
	
	@Autowired
	public NewUserRepositoryOrm(EntityManager em) {
		this.em = em;
	}

	@Override
	public NewUser find(String id) {
		if (id == null) return null;
		
		return em.find(NewUser.class, id);
	}

	@Override
	public NewUser findByEmail(String email) {
		if (email == null) return null;
		
		TypedQuery<NewUser> query = em.createNamedQuery("newUser_find_by_email", NewUser.class);
		query.setParameter("email", email);
		List<NewUser> list = query.getResultList();

		return (list.size() == 0 ? null : list.get(0));
	}
	
	@Override
	public void save(NewUser newUser) {
		em.persist(newUser);
	}

	@Override
	public void remove(NewUser newUser) {
		em.remove(newUser);
	}

	@Override
	public void removeByEmail(String email) {
		Query query = em.createNamedQuery("newUser_delete_by_email");
		query.setParameter("email", email);
		query.executeUpdate();
	}
}
