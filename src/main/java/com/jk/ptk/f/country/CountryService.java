package com.jk.ptk.f.country;

import java.util.List;

/**
 * Implementations of this interface manipulates the instances of type
 * {@link Country}.
 * 
 * @author Jitendra
 *
 */
public interface CountryService {
	/**
	 * Returns a country associated with the specified {@code id} if it exists
	 * otherwise {@code null}.
	 * 
	 * @param id
	 *           the specified id
	 * @return a country associated with the specified {@code id} if it exists
	 *         otherwise {@code null}
	 */
	Country find(Long id);

	/**
	 * Returns a list of all countries from the storage system.
	 * 
	 * @return a list of all countries from the storage system
	 */
	List<Country> getAll();
}