package com.jk.pustakalaya.f.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressController {
	@Autowired
	private AddressService service;
	
	@GetMapping("/{userId}")
	public List<Address> getAddresses(@PathVariable("userId") Long userId) {
		return service.getAddresses(userId);
	}
	
	@PostMapping
	public void addAddress(@RequestBody Address address) {
		service.addAddress(address.getUser().getId(), address);
	}
	
	@DeleteMapping
	public void removeAddress(@RequestBody Address address) {
		service.removeAddress(address);
	}
}
