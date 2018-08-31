package com.jk.ptk.f.user;

import com.jk.ptk.app.ResourceExpiredException;
import com.jk.ptk.security.login.InvalidCredentialsException;
import com.jk.ptk.validation.ValidationException;

/**
 * Implementations of this interface manipulate instances of type
 * {@link User}.
 *
 * @author Jitendra
 */
public interface UserService {
	/**
	 * Saves the user with the specified {@code userValues} in the system.
	 *
	 * @param userValues
	 *                   the specified user
	 * @throws ValidationException
	 *                                  if a data validator is provided and one or
	 *                                  more fields fail the validation
	 * @throws ResourceExpiredException
	 *                                  if the registration id of the user has
	 *                                  expired
	 */
	void save(UserV userValues) throws ValidationException, ResourceExpiredException;

	/**
	 * Returns an instance of the user associated with the specified {@code email},
	 * {@code null} otherwise.
	 *
	 * @param email
	 *              the specified email
	 * @return an instance of the user associated with the specified email,
	 *         {@code null} otherwise
	 */
	User findByEmail(String email);

	/**
	 * Returns {@code true} if an user is already registered with the specified
	 * {@code email}, {@code false} otherwise.
	 *
	 * @param email
	 *              the specified email to check
	 * @return {@code true} if an user is already registered with the specified
	 *         email, {@code false} otherwise
	 */
	boolean doesUserExist(String email);

	/**
	 * Returns {@code true} if the specified {@code mobile} is associated with an
	 * existing
	 * user, {@code false} otherwise.
	 *
	 * @param mobile
	 *               the specified mobile to check
	 * @return {@code true} if the specified mobile is associated with an existing
	 *         user, {@code false} otherwise
	 */
	boolean doesMobileExists(String mobile);

	/**
	 * Updates the password of the user associated with the specified {@code email}
	 * if the specified {@code password} matches with stored password and
	 * {@code newPassword} and {@code confirmNewPassword} pass the validation (if a
	 * data validator is defined).
	 *
	 * @param email
	 *                           the specified email
	 * @param currentPassword
	 *                           the specified password
	 * @param newPassword
	 *                           new password to save
	 * @param confirmNewPassword
	 *                           confirm new password to check new password
	 * @throws InvalidCredentialsException
	 *                                     if the specified password does not match
	 *                                     with user's stored password
	 * @throws ValidationException
	 *                                     if a data validator is defined and either
	 *                                     of the new password or confirm new
	 *                                     password fields fail validation
	 */
	void updatePassword(String email, String currentPassword, String newPassword, String confirmNewPassword)
			throws InvalidCredentialsException, ValidationException;

	/**
	 * Updates the security question and answer of the user associated with the
	 * specified {@code email} if the specified {@code password} matches with user's
	 * stored password and {@code question} and {@code answer} pass the validation
	 * (if a data validator is defined).
	 *
	 * @param email
	 *                 the specified email
	 * @param password
	 *                 the specified password
	 * @param question
	 *                 the specified security question to save
	 * @param answer
	 *                 the specified security answer to save
	 * @throws InvalidCredentialsException
	 *                                     if specified password does not match with
	 *                                     user's stored password
	 * @throws ValidationException
	 *                                     if a data validator is defined and either
	 *                                     of the {code question} or {@code answer}
	 *                                     or both fields fail validation
	 */
	void updateSecurityQuestionAndAnswer(String email, String password, String question, String answer)
			throws InvalidCredentialsException, ValidationException;

	/**
	 * Update the number of unsuccessful tries of the user associated with the
	 * specified {@code email}.
	 *
	 * @param email
	 *              the specified email
	 * @param tries
	 *              number of unsuccessful tries
	 */
	void updateUnsuccessfulTries(String email, Integer tries);

	/**
	 * Returns profile details of the user associated with the specified email.
	 * 
	 * @param email
	 *              the specified email
	 * @return profile details of the user associated with the specified email
	 */
	Profile getProfile(String email);
}
