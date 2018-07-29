package com.jk.ptk.validation;

/**
 * Implementation of this interface validates the fields of the specified type.
 * 
 * @author Jitendra
 *
 * @param <T>
 *        the specified type
 */
public interface DataValidation<T> {
	/**
	 * Validates the data of the specified type.
	 * 
	 * @param t
	 *          the object to validate
	 * @throws ValidationException
	 *                             if one more fields fail validation
	 */
	void validate(T t) throws ValidationException;
}
