package com.jk.ptk.f.urh;

import java.util.List;

/**
 * Implementations of this interface serves to manipulate instances of type
 * {@link UserRoleHistory}.
 *
 * @author Jitendra
 */
public interface UserRoleHistoryService {
	/**
	 * Adds an entry of user role history to the system.
	 * 
	 * @param urh
	 *            the specified entry of user role history
	 */
	void save(UserRoleHistory urh);

	/**
	 * Returns a list of all entries of user role history associated with the
	 * specified email. NEVER returns {@code null}.
	 * 
	 * @param email
	 *              the specified email
	 * @return a list of all entries of user role history associated with the
	 *         specified email, NEVER returns {@code null}
	 */
	List<UserRoleHistory> getAll(String email);
}
