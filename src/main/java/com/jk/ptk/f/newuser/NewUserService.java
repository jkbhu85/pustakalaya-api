package com.jk.ptk.f.newuser;

import com.jk.ptk.validation.ValidationException;

/**
 * Implementations of this interface serves to manipulate instances of type
 * {@link NewUser}.
 *
 * @author Jitendra
 *
 */
public interface NewUserService {
	/**
	 * Add the specified user to the system.
	 *
	 * @param newUser
	 *            the specified user to add
	 *
	 * @throws ValidationException
	 *             if the specified user data fails validation
	 */
	void addNewUser(NewUser newUser) throws ValidationException;

	/**
	 * Returns the new user identified by {@code id}.
	 *
	 * @param id
	 *            the specified id
	 * @return the new user identified by {@code id}.
	 * @throws ValidationException
	 *             if no new user was found or id has expired
	 */
	NewUser getNewUser(String id) throws ValidationException;
}
