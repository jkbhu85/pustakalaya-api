package com.jk.ptk.f.bah;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BahRepositoryOrm implements BahRepository {
	private EntityManager em;
	
	@Autowired
	public BahRepositoryOrm(EntityManager em) {
		this.em = em;
	}

	@Override
	public void saveOrUpdate(BookAssignmentHistory bah) {
		if (bah.getId() == null) {
			em.persist(bah);
		} else {
			em.merge(bah);
		}
	}

}
