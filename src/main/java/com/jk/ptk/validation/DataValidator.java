package com.jk.ptk.validation;

import java.util.List;

/**
 * Implementation of this interface validates the fields of the specified type.
 *
 * @author Jitendra
 *
 * @param <T>
 *            the specified type
 */
public interface DataValidator<T> {
	/**
	 * Validates the data of the specified type.
	 *
	 * @param t
	 *            the object to validate
	 * @throws ValidationException
	 *             if one more fields fail validation
	 */
	void validate(T t) throws ValidationException;

	/**
	 * Validates the specified form field only if the field name is not
	 * {@code null}. Default implementation throws the exception
	 * {@link OperationNotSupportedException}.
	 *
	 * @param formField
	 *            the specified form field to validate
	 * @throws ValidationException
	 *             if the value fails validation
	 * @throws OperationNotSupportedException
	 *             if this method is not implemented by the implementing class
	 * @throws NullPointerException
	 *             if the specified form field is {@code null}
	 */
	default void validate(FormField formField) throws ValidationException, OperationNotSupportedException {
		throw new OperationNotSupportedException("This operation is not supported.");
	}

	/**
	 * Validates the specified list of form field. A form field is validated only if
	 * the field name is not {@code null}. Default implementation throws the
	 * exception {@link OperationNotSupportedException}.
	 *
	 * @param formFieldList
	 *            list of form fields to validate
	 * @throws ValidationException
	 *             if the value fails validation
	 * @throws OperationNotSupportedException
	 *             if this method is not implemented by the implementing class
	 * @throws NullPointerException
	 *             if the specified list form field is {@code null}
	 */
	default void validate(List<FormField> formFieldList) throws ValidationException, OperationNotSupportedException {
		throw new OperationNotSupportedException("This operation is not supported.");
	}
}
