package com.jk.ptk.currency;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Concrete class implementing {@code CurrencyRepository} interface that uses an
 * ORM framework to perform CRUD operations on the type {@code Currency}.
 * 
 * @author Jitendra
 */
@Repository
public class CurrencyRepositoryOrm implements CurrencyRepository {
	private final EntityManager em;

	/**
	 * Creates an instance with the specified entity manager.
	 * 
	 * @param em
	 *           an entity manager
	 */
	@Autowired
	public CurrencyRepositoryOrm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Currency find(Integer id) {
		return em.find(Currency.class, id);
	}

	@Override
	public List<Currency> getAll() {
		TypedQuery<Currency> query = em.createNamedQuery("currency_select_all", Currency.class);
		return query.getResultList();
	}

	@Override
	public boolean doesCurrencyExist(Integer id) {
		TypedQuery<Long> query = em.createNamedQuery("currency_exists", Long.class);
		query.setParameter("id", id);
		return query.getSingleResult() == 1;
	}

}
