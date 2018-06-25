package com.jk.ptk.f.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryServiceImpl implements CountryService {
	@Autowired
	private CountryRepository repository;

	@Override
	public List<Country> getCountries() {
		return repository.getCountries();
	}

	@Override
	public Country getCountry(Long id) {
		return repository.getCountry(id);
	}

}
