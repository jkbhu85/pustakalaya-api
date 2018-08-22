package com.jk.ptk.f.country;

import java.util.List;

/**
 * Implementations of this interface interact with a storage system to
 * perform CRUD operations on the type {@link Country}.
 * 
 * @author Jitendra
 */
public interface CountryRepository {
	/**
	 * Returns a country associated with the specified {@code id} if it exists
	 * otherwise {@code null}.
	 * 
	 * @param id
	 *           the specified id
	 * @return a country associated with the specified {@code id} if it exists
	 *         otherwise {@code null}
	 */
	Country find(Integer id);

	/**
	 * Returns a list of all countries.
	 * 
	 * @return a list of all countries
	 */
	List<Country> getAll();

	/**
	 * Returns {@code true} if a country with the specified {@code id} exists,
	 * {@code false} otherwise.
	 * 
	 * @param id
	 *           the specified country id
	 * @return {@code true} if a country with the specified {@code id} exists,
	 *         {@code false} otherwise
	 */
	boolean doesCountryExist(Integer id);
}
