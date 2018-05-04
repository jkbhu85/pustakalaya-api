package com.jk.pustakalaya.security.login;

/**
 * Provieds methods to get user's login information.
 *
 * @author Jitendra
 *
 */
public interface LoginDao {

	/**
	 * Returns {@code null} if no information for the email exists, associated information otherwise.
	 *
	 * @param email the email for which information will be fetched

	 * @return {@code null} if no information for the email exists, associated information otherwise.
	 */
	LoginCredentials getLoginCredentialsByEmail(String email);
}
