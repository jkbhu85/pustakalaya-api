package com.jk.ptk.f.uash;

import java.util.List;

/**
 * Implementations of this interface interact with a storage system to
 * perform CRUD operations on the type {@link UserAccountStatusHistory}.
 * 
 * @author Jitendra
 */
public interface UserAccountStatusHistoryRepository {
	/**
	 * Saves the specified {@code uash} in the system.
	 * 
	 * @param uash
	 *             the specified entry to be saved
	 */
	void save(UserAccountStatusHistory uash);

	/**
	 * Returns a list of all entries of uesr's account status history associated
	 * with the specified {@code email}. NEVER returns {@code null}.
	 * 
	 * @param email
	 *              the specified email
	 * @return a list of all entries of uesr's account status history associated
	 *         with the specified {@code email}
	 */
	public List<UserAccountStatusHistory> getAll(String email);
}
