package com.jk.ptk.f.country;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Concrete class of the interface {@link CountryRepository} that uses an ORM
 * framework.
 * 
 * @author Jitendra
 */
@Repository
public class CountryRepositoryOrm implements CountryRepository {
	@Autowired
	private EntityManager em;

	@Override
	public Country find(Integer id) {
		return em.find(Country.class, id);
	}

	@Override
	public List<Country> getAll() {
		TypedQuery<Country> q = em.createNamedQuery("country_get_all", Country.class);
		return q.getResultList();
	}

	@Override
	public boolean doesCountryExist(Integer id) {
		TypedQuery<Long> query = em.createNamedQuery("country_exists", Long.class);
		query.setParameter("id", id);
		
		return query.getSingleResult() == 1;
	}
}
