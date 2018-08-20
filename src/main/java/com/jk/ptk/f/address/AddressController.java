package com.jk.ptk.f.address;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jk.ptk.validation.ValidationException;

/**
 * API endpoint for type {@link Address}.
 *
 * @author Jitendra
 *
 */

@RestController
@RequestMapping("/ptk/address")
public class AddressController {
	
	private static final Logger log = LoggerFactory.getLogger(AddressController.class);
	
	@Autowired
	private AddressService service;

	@GetMapping("{id}")
	public Address getAddress(@PathVariable("id") Long addressId) {
		return service.getAddress(addressId);
	}

	@GetMapping("/user/{userId}")
	public List<Address> getAddresses(@PathVariable("userId") Long userId) {
		return service.getAddresses(userId);
	}

	@PostMapping
	public void addAddress(@RequestBody Address address) {
		try {
			service.addAddress(address);
		} catch (ValidationException e) {
			log.error("Error occurred while saving address.", e);
		}
	}
	
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
