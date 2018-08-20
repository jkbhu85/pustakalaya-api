package com.jk.ptk.f.newuser;

/**
 * 
 * Implementations of this interface interact with a storage system to
 * perform CRUD operations on the type {@link NewUser}.
 * 
 * @author Jitendra
 *
 */
public interface NewUserRepository {
	/**
	 * Saves the specified {@code newUser} in the system.
	 * 
	 * @param newUser
	 *                the specified new user to be saved
	 */
	void save(NewUser newUser);

	/**
	 * Returns the user identified by the specified {@code id}.
	 *
	 * @param id
	 *           the specified id
	 * @return user identified by the specified {@code id}
	 */
	NewUser find(String id);

	/**
	 * Returns the user associated with the specified {@code email}.
	 *
	 * @param email
	 *              the specified email
	 * @return user associated with the specified {@code email}
	 */
	NewUser findByEmail(String email);

	/**
	 * Removes the specified {@code newUser} from the system.
	 * 
	 * @param newUser
	 *                the specified new user to be removed
	 */
	void remove(NewUser newUser);

	/**
	 * Removes the new user associated with the specified {@code email}.
	 * 
	 * @param email
	 *              the specified email
	 */
	void removeByEmail(String email);
}
