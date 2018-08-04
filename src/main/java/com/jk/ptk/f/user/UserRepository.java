package com.jk.ptk.f.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
//@Transactional
public class UserRepository {
	@Autowired
	private EntityManager em;

	public UserAuthInfo findUserAuthInfo(Long id) {
		return em.find(UserAuthInfo.class, id);
	}

	public UserAuthInfo findUserAuthInfo(String email) {
		TypedQuery<UserAuthInfo> query = em.createNamedQuery("find_by_email", UserAuthInfo.class);
		query.setParameter("email", email);
		List<UserAuthInfo> list = query.getResultList();
		
		return (list.size() == 0 ? null : list.get(0));
	}

	@Transactional
	public void udpateUserAuthInfo(UserAuthInfo userAuthInfo) {
		em.merge(userAuthInfo);
		em.flush();
	}

	public User findUser(String email) {
		if (email == null || email.isEmpty()) return null;
		
		TypedQuery<User> query = em.createNamedQuery("find_by_email", User.class);
		List<User> list = query.getResultList();
		
		return (list.size() == 0 ? null : list.get(0));
	}

	public LightUser findLightUser(String email) {
		if (email == null || email.isEmpty()) return null;
		
		TypedQuery<LightUser> query = em.createNamedQuery("find_lightUser_by_email", LightUser.class);
		query.setParameter("email", email);
		List<LightUser> list = query.getResultList();
		
		return (list.size() == 0 ? null : list.get(0));
	}
	
	public boolean userExists(String email) {
		Query query = em.createNamedQuery("if_user_exist_by_email");
		query.setParameter("email", email);
		final int result = ((Number)query.getSingleResult()).intValue();
		
		return result == 1;
	}
}
