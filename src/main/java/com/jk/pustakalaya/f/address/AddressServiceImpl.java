package com.jk.pustakalaya.f.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressRepository repository;

	@Override
	public List<Address> getAddresses(Long userId) {
		return repository.getAddresses(userId);
	}

	@Override
	public void addAddress(Long userId, Address address) {
		repository.addAddress(userId, address);
	}

	@Override
	public void removeAddress(Address address) {
		repository.removeAddress(address);
	}

}
