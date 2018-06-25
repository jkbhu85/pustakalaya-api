package com.jk.ptk.f.country;

import java.util.List;

public interface CountryService {
	List<Country> getCountries();
	Country getCountry(Long id);
}
