package com.jk.ptk.f.country;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CountryRepository {
	@Autowired
	private EntityManager em;
	
	public List<Country> getCountries() {
		TypedQuery<Country> q = em.createNamedQuery("get_all_countries", Country.class);
		return q.getResultList();
	}
	
	public Country getCountry(Long id) {
		return em.find(Country.class, id);
	}
}
