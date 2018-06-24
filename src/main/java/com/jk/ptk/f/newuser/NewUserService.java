package com.jk.ptk.f.newuser;


import com.jk.ptk.app.ValidationFailedException;

/**
 * Service class for entity {@link NewUser}.
 * 
 * @author Jitendra
 *
 */
public interface NewUserService {
	/**
	 * Add the specified user to the system.
	 * 
	 * @param newUser
	 *            the specfied user to add
	 *            
	 * @throws ValidationException if the specified user data fails validation
	 */
	void addNewUser(NewUser newUser) throws ValidationFailedException;

	/**
	 * Returns the new user identified by {@code id}. Returns {@code null} if no
	 * user associated with {@code id} exists.
	 * 
	 * @param id
	 *            the specified id
	 * @return the new user identified by {@code id} or returns {@code null} if no
	 *         user associated with {@code id} exists.
	 */
	NewUser getNewUser(String id);
}
