package com.jk.ptk.f.address;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository to perform CRUD operations for the instances of type
 * {@link Address}.
 *
 * @author Jitendra
 *
 */

@Repository
@Transactional
public class AddressRepository {
	@Autowired
	private EntityManager em;

	public Address getAddress(Long addressId) {
		return em.find(Address.class, addressId);
	}

	public List<Address> getAddresses(Long userId) {
		TypedQuery<Address> query = em.createNamedQuery("address_by_user_id", Address.class);
		query.setParameter(0, userId);

		return query.getResultList();
	}

	public void addAddress(Address address) {
		if (address == null)
			return;

		em.persist(address);
	}

	public void removeAddress(Address address) {
		if (address == null)
			return;

		em.remove(address);
	}

	public void removeAddress(Long addressId) {
		Address addr = em.find(Address.class, addressId);

		removeAddress(addr);
	}
}
