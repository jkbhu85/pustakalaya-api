package com.jk.ptk.f.user;

/**
 * Implementations of this interface interact with a storage system to
 * perform CRUD operations on the type {@link User}.
 * 
 * @author Jitendra
 */
public interface UserRepository {

	/**
	 * Returns the user associated with the specified {@code email}.
	 *
	 * @param email
	 *              the specified email
	 * @return user associated with the specified {@code email}
	 */
	User findByEmail(String email);

	/**
	 * Returns {@code true} if the specified {@code email} is associated with an
	 * user, {@code false} otherwise.
	 * 
	 * @param email
	 *              the specified email
	 * @return {@code true} if the specified {@code email} is associated with an
	 *         user, {@code false} otherwise
	 */
	boolean doesEmailExists(String email);

	/**
	 * Returns {@code true} if the specified {@code mobile} is associated with an
	 * user, {@code false} otherwise.
	 * 
	 * @param mobile
	 *               the specified mobile
	 * @return {@code true} if the specified {@code mobile} is associated with an
	 *         user, {@code false} otherwise
	 */
	boolean doesMobileExists(String mobile);

	/**
	 * Saves or updates specified user in the storage system.
	 * 
	 * @param user
	 *             the specified user to save or update
	 */
	void saveOrUpdate(User user);
}
