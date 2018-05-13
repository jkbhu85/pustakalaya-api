package com.jk.pustakalaya.f.country;

import java.util.List;

public interface CountryService {
	List<Country> getCountries();
	Country getCountry(Long id);
}
