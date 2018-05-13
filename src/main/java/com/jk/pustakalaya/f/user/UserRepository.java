package com.jk.pustakalaya.f.user;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserRepository {
	@Autowired
	private EntityManager em;

	public User findUser(Long id) {
		return em.find(User.class, id);
	}

	public UserAuthInfo findUserAuthInfo(Long id) {
		return em.find(UserAuthInfo.class, id);
	}

	public UserAuthInfo findUserAuthInfo(String email) {
		TypedQuery<UserAuthInfo> q = em.createNamedQuery("get_by_email", UserAuthInfo.class);
		q.setParameter(1, email);

		return q.getSingleResult();
	}

	public void udpateUserAuthInfo(UserAuthInfo userAuthInfo) {
		em.merge(userAuthInfo);
	}
}
