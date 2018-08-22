package com.jk.ptk.currency;

import java.util.List;

/**
 * Implementations of this interface interact with a storage system to
 * perform CRUD operations on the type {@link Currency}.
 * 
 * @author Jitendra
 */
public interface CurrencyRepository {

	/**
	 * Returns the currency identified by the specified id.
	 * 
	 * @param id
	 *           the specified id
	 * @return the currency identified by the specified {@code id}
	 */
	Currency find(Integer id);

	/**
	 * Returns a list of all currencies in the system.
	 * 
	 * @return a list of all currencies in the system
	 */
	List<Currency> getAll();

	/**
	 * Returns {@code true} if a currency with specified {@code id} exists,
	 * {@code false} otherwise.
	 * 
	 * @param id
	 *           the specified id
	 * @return {@code true} if a currency with specified {@code id} exists,
	 *         {@code false} otherwise
	 */
	boolean doesCurrencyExist(Integer id);
}
