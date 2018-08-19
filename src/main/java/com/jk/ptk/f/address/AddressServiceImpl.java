package com.jk.ptk.f.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.ptk.validation.DataValidator;
import com.jk.ptk.validation.ValidationException;

/**
 * An implementation of the {@code AddressService} type.
 *
 * @author Jitendra
 *
 */
@Service
public class AddressServiceImpl implements AddressService, DataValidator<Address> {
	@Autowired
	private AddressRepository repository;

	@Override
	public List<Address> getAddresses(Long userId) {
		return repository.getAddresses(userId);
	}

	@Override
	public void addAddress(Address address) throws ValidationException {
		validate(address);

		repository.addAddress(address);
	}

	@Override
	public void removeAddress(Address address) {
		repository.removeAddress(address);
	}

	@Override
	public Address getAddress(Long addressId) {
		return repository.getAddress(addressId);
	}

	@Override
	public void removeAddress(Long addressId) {
		repository.removeAddress(addressId);
	}

	@Override
	public void validate(Address obj) throws ValidationException {
		// TODO Auto-generated method stub

	}

}
