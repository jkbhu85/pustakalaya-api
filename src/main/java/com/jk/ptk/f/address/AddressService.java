package com.jk.ptk.f.address;

import java.util.List;

public interface AddressService {
	List<Address> getAddresses(Long userId);
	void addAddress(Long userId, Address address);
	void removeAddress(Address address);
}
