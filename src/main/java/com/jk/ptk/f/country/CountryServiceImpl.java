package com.jk.ptk.f.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A concrete class of interface {@link CountryService}.
 * 
 * @author Jitendra
 *
 */
@Component
public class CountryServiceImpl implements CountryService {
	@Autowired
	private CountryRepository repository;

	@Override
	public Country find(Long id) {
		return repository.find(id);
	}

	@Override
	public List<Country> getAll() {
		return repository.getAll();
	}

}
