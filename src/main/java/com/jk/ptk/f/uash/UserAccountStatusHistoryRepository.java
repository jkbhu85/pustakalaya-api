package com.jk.ptk.f.uash;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountStatusHistoryRepository {
	
	private EntityManager em;
	
	@Autowired
	public UserAccountStatusHistoryRepository(EntityManager em) {
		this.em = em;
	}
	
	public void addAccountStatus(UserAccountStatusHistory uas) {
		em.persist(uas);
	}

	public List<UserAccountStatusHistory> getAccountHistory(String email) {
		TypedQuery<UserAccountStatusHistory> query = em.createNamedQuery("uash_find_all_by_email", UserAccountStatusHistory.class);
		query.setParameter("email", email);
		
		return query.getResultList();
	}
}
