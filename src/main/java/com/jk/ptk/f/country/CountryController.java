package com.jk.ptk.f.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ptk/country")
public class CountryController {
	@Autowired
	private CountryService service;

	@GetMapping
	public List<Country> getCountries() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Country getCountry(@PathVariable("id") Long id) {
		return service.find(id);
	}
}
