package com.jk.ptk.f.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jk.ptk.app.ValidationException;

/**
 * API endpoint to manipulate instances of type {@link Address}.
 *
 * @author Jitendra
 *
 */

@RestController
@RequestMapping("/ptk/address")
public class AddressController {
	@Autowired
	private AddressService service;

	/**
	 * Returns the address instance that has the specified {@code addressId} or
	 * {@code null} if no address with the specified {@code addressId} exists.
	 *
	 * @param addressId
	 *            the specified address id
	 * @return the address instance that has the specified {@code addressId} or
	 *         {@code null} if no address with the specified {@code addressId}
	 *         exists
	 */
	@GetMapping("{id}")
	public Address getAddress(@PathVariable("id") Long addressId) {
		return service.getAddress(addressId);
	}

	/**
	 * Returns a list of addresses associated with the specified {@code userId} or
	 * {@code null} if no address is associated with the specified {@code userId}.
	 *
	 * @param userId
	 *            the specified user id
	 * @return list of addresses associated with the specified {@code userId} or
	 *         {@code null} if no address is associated with the specified
	 *         {@code userId}
	 */
	@GetMapping("/user/{userId}")
	public List<Address> getAddresses(@PathVariable("userId") Long userId) {
		return service.getAddresses(userId);
	}

	/**
	 * Stores the specified address instance to the storage.
	 *
	 * @param address
	 *            the specified address instance to be stored
	 */
	@PostMapping
	public void addAddress(@RequestBody Address address) {
		try {
			service.addAddress(address);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Removes the specified address instance from the storage.
	 *
	 * @param address
	 *            the specified address instance to be removed
	 */
	@DeleteMapping
	public void removeAddress(@RequestBody Address address) {
		service.removeAddress(address);
	}

	/**
	 * Removes the address instance having the specified address id.
	 *
	 * @param addressId
	 *            the specified address id
	 */
	@DeleteMapping("/{id}")
	public void removeAddress(@PathVariable("id") Long addressId) {
		service.removeAddress(addressId);
	}
}
