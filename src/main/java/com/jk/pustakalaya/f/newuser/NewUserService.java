package com.jk.pustakalaya.f.newuser;

/**
 * Service class for entity {@link NewUser}.
 * 
 * @author Jitendra
 *
 */
public interface NewUserService {
	/**
	 * Add the {@code newUser} to database.
	 * 
	 * @param newUser
	 *            the specfied user to add
	 */
	void addNewUser(NewUser newUser);

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
