package com.jk.ptk.f.urh;

import java.util.List;

/**
 * Implementations of this interface interact with a storage system to
 * perform CRUD operations on the type {@link UserRoleHistory}.
 * 
 * @author Jitendra
 */
public interface UserRoleHistoryRepository {
	/**
	 * Saves the specified {@code urh} in the system.
	 * 
	 * @param urh
	 *             the specified entry to be saved
	 */
	void save(UserRoleHistory urh);

	/**
	 * Returns a list of all entries of uesr's role history associated
	 * with the specified {@code email}. NEVER returns {@code null}.
	 * 
	 * @param email
	 *              the specified email
	 * @return a list of all entries of uesr's role history associated
	 *         with the specified {@code email}
	 */
	List<UserRoleHistory> getAll(String email);
}
