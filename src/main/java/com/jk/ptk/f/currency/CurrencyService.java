package com.jk.ptk.f.currency;

import java.util.List;

/**
 * Implementations of this interface manipulate instances of type
 * {@link Currency}.
 *
 * @author Jitendra
 */
public interface CurrencyService {

	/**
	 * Returns a currency identified by the specified {@code id}.
	 * 
	 * @param id
	 *           the specified id
	 * @return a currency identified by the specified {@code id}
	 */
	Currency find(String id);

	/**
	 * Returns a list of all currencies available in the system.
	 * 
	 * @return a list of all currencies available in the system
	 */
	List<Currency> getAll();

	/**
	 * Returns {@code true} if a currency with the specified {@code id} exist,
	 * {@code false} otherwise.
	 * 
	 * @param id
	 *           the specified id
	 * @return {@code true} if a currency with the specified {@code id} exist,
	 *         {@code false} otherwise
	 */
	boolean doesCurrencyExist(String id);
}
