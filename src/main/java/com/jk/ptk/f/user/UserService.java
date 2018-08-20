package com.jk.ptk.f.user;

import com.jk.ptk.app.ResourceExpiredException;
import com.jk.ptk.security.login.InvalidCredentialsException;
import com.jk.ptk.validation.ValidationException;

/**
 * Implementations of this interface serves to manipulate instances of type
 * {@link User}.
 *
 * @author Jitendra
 *
 */
public interface UserService {
	/**
	 * Returns {@code true} if an user is already registered with the specified
	 * email, {@code false} otherwise.
	 *
	 * @param email
	 *            the specified email to check
	 * @return {@code true} if an user is already registered with the specified
	 *         email, {@code false} otherwise
	 */
	boolean userExists(String email);

	/**
	 * Returns {@code true} if the specified mobile is associated with an existing
	 * user, {@code false} otherwise.
	 *
	 * @param mobile
	 *            the specified mobile to check
	 * @return {@code true} if the specified mobile is associated with an existing
	 *         user, {@code false} otherwise
	 */
	boolean mobileExists(String mobile);

	/**
	 * Adds an user with specified data to the system.
	 *
	 * @param user
	 *            the object the specified data
	 * @throws ValidationException
	 *             if a data validator is provided and one or more fields fail the
	 *             validation
	 * @throws ResourceExpiredException
	 *             if the partial registration of the user has expired
	 */
	void addUser(UserFormValues user) throws ValidationException, ResourceExpiredException;

	/**
	 * Returns an instance of the user associated with the specified email,
	 * {@code null} otherwise.
	 *
	 * @param email
	 *            the specified email
	 * @return an instance of the user associated with the specified email,
	 *         {@code null} otherwise
	 */
	User findUser(String email);

	/**
	 * Updates the password of the user associated with the specified email if
	 * current password matches with stored password and new password and confirm
	 * new password the validation (if a data validator is defined).
	 *
	 * @param email
	 *            the specified email
	 * @param currentPassword
	 *            current password of the user
	 * @param newPassword
	 *            new password to change the existing password with
	 * @param confirmNewPassword
	 *            confirm new password
	 * @throws InvalidCredentialsException
	 *             if current password does not match with user's stored password
	 * @throws ValidationException
	 *             if a data validator is defined and either of the new password or
	 *             confirm new password fields fail validation
	 */
	void updatePassword(String email, String currentPassword, String newPassword, String confirmNewPassword)
			throws InvalidCredentialsException, ValidationException;

	/**
	 * Updates the security question and answer of the user associated with the
	 * specified email if password matches with user's stored password and question
	 * and answer pass the validation (if a data validator is defined).
	 *
	 * @param email
	 *            the specified email
	 * @param password
	 *            current password of the user
	 * @param question
	 *            new security question of the user
	 * @param answer
	 *            new security of the user
	 * @throws InvalidCredentialsException
	 *             if current password does not match with user's stored password
	 * @throws ValidationException
	 *             if a data validator is defined and either of the question or
	 *             answer fields fail validation
	 */
	void updateSecurityQuestion(String email, String password, String question, String answer)
			throws InvalidCredentialsException, ValidationException;

	/**
	 * This method is used within system to update the number of unsuccessful tries
	 * of the user associated with the specified email.
	 *
	 * @param email
	 *            the specified email
	 * @param tries
	 *            number of unsuccessful tries
	 */
	void updateUnsuccessfulTries(String email, Integer tries);
}
