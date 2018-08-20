package com.jk.ptk.f.newuser;

import com.jk.ptk.validation.ValidationException;

/**
 * Implementations of this interface manipulate instances of type
 * {@link NewUser}.
 *
 * @author Jitendra
 */
public interface NewUserService {
	/**
	 * Saves the specified user in the system.
	 *
	 * @param userValues
	 *                   the specified user to add
	 * @throws ValidationException
	 *                             if the specified user data fails validation
	 */
	void save(NewUserV userValues) throws ValidationException;

	/**
	 * Returns the new user identified by the specified id.
	 * 
	 * @param id
	 *           the specified id
	 * @return the new user identified by the specified id
	 * @throws ValidationException
	 *                             if a user with the specified id was not found
	 */
	NewUser find(String id) throws ValidationException;

	/**
	 * Returns the new user associated with the specified email, returns
	 * {@code null} if no user was found.
	 * 
	 * @param email
	 *              the specified email
	 * @return new user associated with the specified email,
	 *         {@code null} if no user was found
	 */
	NewUser findByEmail(String email);

	/**
	 * Removes the specified new user form the system.
	 * 
	 * @param newUser
	 *                the specified new user to be removed
	 */
	void remove(NewUser newUser);
}
