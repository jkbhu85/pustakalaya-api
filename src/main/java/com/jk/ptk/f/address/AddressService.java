package com.jk.ptk.f.address;

import java.util.List;

import com.jk.ptk.validation.ValidationException;

/**
 * Implementations of this interface serves to manipulate instances of type
 * {@link Address}.
 *
 * @author Jitendra
 *
 */
public interface AddressService {
	/**
	 * Returns address if an address with the specified {@code addressId} exists.
	 *
	 * @param addressId
	 *            the specified address id
	 * @return address if an address with the specified {@code addressId} exists
	 */
	Address getAddress(Long addressId);

	/**
	 * Returns a list of address associated with the specified {@code userId} if at
	 * least on address is associated with the specified {@code userId}. Otherwise
	 * returns {@code null}.
	 *
	 * @param userId
	 *            the specified user id
	 * @return a list of address associated with the specified {@code userId} if at
	 *         least on address is associated with the specified {@code userId} or
	 *         returns {@code null}
	 */
	List<Address> getAddresses(Long userId);

	/**
	 * Stores the specified instance in the storage.
	 *
	 * @param address
	 *            the specified instance to be stored
	 * @throws ValidationException
	 *             if any of the field fails the validation
	 */
	void addAddress(Address address) throws ValidationException;

	/**
	 * Removes the specified instance from the storage.
	 *
	 * @param address
	 *            the specified instance to be removed
	 */
	void removeAddress(Address address);

	/**
	 * Removes the address instance having the specified {@code addressId} from the
	 * storage.
	 *
	 * @param addressId
	 *            the id of address to be removed from the storage
	 */
	void removeAddress(Long addressId);
}
