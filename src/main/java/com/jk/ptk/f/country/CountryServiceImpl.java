package com.jk.ptk.f.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * An implementation of the {@code CountryService} type.
 * 
 * @author Jitendra
 */
@Service
public class CountryServiceImpl implements CountryService {
	@Autowired
	private CountryRepository repository;

	@Override
	public Country find(String bookId) {
		Integer id = Integer.parseInt(bookId);
		return repository.find(id);
	}

	@Override
	public List<Country> getAll() {
		return repository.getAll();
	}

}
