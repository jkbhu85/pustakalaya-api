package com.jk.ptk.f.user;

/**
 * Implementations of this interface interact with a storage system to
 * perform CRUD operations on the type {@link User}.
 * 
 * @author Jitendra
 */
public interface UserRepository {

	/**
	 * Saves the specified {@code user} in the system.
	 * 
	 * @param user
	 *             the specified user to be saved
	 */
	void save(User user);

	/**
	 * Returns the user associated with the specified {@code email}.
	 *
	 * @param email
	 *              the specified email
	 * @return user user associated with the specified {@code email}
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
	 * Updates password of the user associated with the specified {@code email} if
	 * the user exists.
	 * 
	 * @param email
	 *                        the specified email
	 * @param passwordHash
	 *                        the password hash to save
	 * @param passwordSalt
	 *                        the password salt to save
	 * @param passwordVersion
	 *                        the password version to save
	 */
	void updatePassword(String email, String passwordHash, String passwordSalt, Integer passwordVersion);

	/**
	 * Updates security question and answer of the user associated with the
	 * specified {@code email} if the user exists.
	 * 
	 * @param email
	 *                 the specified email
	 * @param question
	 *                 the question to save
	 * @param answer
	 *                 the answer to save
	 */
	void updateSecurityQuestionAndAnswer(String email, String question, String answer);

	/**
	 * Updates number of unsuccessful login tries of the user associated with the
	 * specified {@code email} if the user exists.
	 * 
	 * @param email
	 *              the specified email
	 * @param tries
	 *              number of unsuccessful login tries to save
	 */
	void updateUnsuccessfulTries(String email, Integer tries);
}
