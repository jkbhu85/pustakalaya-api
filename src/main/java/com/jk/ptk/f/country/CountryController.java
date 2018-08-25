package com.jk.ptk.f.country;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API end point for the type {@link Country}.
 * 
 * @author Jitendra
 */
@RestController
@RequestMapping("/ptk/country")
public class CountryController {
	private static final Logger log = LoggerFactory.getLogger(CountryController.class);
	
	@Autowired
	private CountryService service;

	@GetMapping("/{id}")
	public Country getCountry(@PathVariable("id") String id) {
		try {
			return service.find(id);
		} catch (Exception e) {
			log.error("Error while fetching country with id {}.{}", id , e);
		}
		
		return null;
	}

	@GetMapping
	public List<Country> getCountries() {
		return service.getAll();
	}
}
