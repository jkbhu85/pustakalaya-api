package com.jk.ptk.f.address;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jk.ptk.f.user.User;

@Repository
@Transactional
public class AddressRepository {
	@Autowired
	private EntityManager em;
	
	public List<Address> getAddresses(Long userId) {
		User user = em.find(User.class, userId);
		
		if (user == null) return null;
		
		return user.getAddressList();
	}
	
	
	public void addAddress(Long userId, Address address) {
		User user = em.find(User.class, userId);
		address.setUser(user);
		user.addAddress(address);
		
		em.persist(address);
	}
	
	public void removeAddress(Address address) {
		User user = address.getUser();
		user.removeAddress(address);
		
		em.remove(address);
	}
}
