package com.jk.ptk.f.uash;

import java.util.List;

/**
 * Implementations of this interface serves to manipulate instances of type
 * {@link UserAccountStatusHistory}.
 *
 * @author Jitendra
 *
 */
public interface UserAccountStatusHistoryService {
	/**
	 * Adds the specified entry of account status history to the system.
	 * 
	 * @param uash
	 *            the specified entry of account status history
	 */
	void addAccountStatusHistory(UserAccountStatusHistory uash);

	/**
	 * Returns a list of all entries of account status history that are associated
	 * with the specified email. NEVER returns {@code null}.
	 * 
	 * @param email
	 *              the specified email
	 * @return list of all entries of account status history that are associated
	 *         with the specified email, NEVER returns {@code null}
	 */
	List<UserAccountStatusHistory> getAccountHistory(String email);
}
