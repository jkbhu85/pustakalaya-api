package com.jk.ptk.f.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jk.ptk.f.uash.UserAccountStatusHistory;
import com.jk.ptk.f.uash.UserAccountStatusHistoryRepository;
import com.jk.ptk.f.urh.UserRoleHistory;
import com.jk.ptk.f.urh.UserRoleHistoryRepository;

@Component
class RoleAndAccountStatusManager {
	private UserAccountStatusHistoryRepository acStatusHistoryRepository;
	private UserRoleHistoryRepository roleHistoryRepository;

	/**
	 * Creates an instance with the specified dependencies.
	 * 
	 * @param acStatusHistoryRepository
	 *                                  instance of user account status history
	 *                                  repository
	 * @param userRoleHistoryRepository
	 *                                  instance of user role history repository
	 */
	@Autowired
	public RoleAndAccountStatusManager(UserAccountStatusHistoryRepository acStatusHistoryRepository,
			UserRoleHistoryRepository userRoleHistoryRepository) {
		this.acStatusHistoryRepository = acStatusHistoryRepository;
		this.roleHistoryRepository = userRoleHistoryRepository;
	}

	/**
	 * Saves user role history for the specified user with specified details.
	 * 
	 * @param user
	 *                   the specified user
	 * @param assignedBy
	 *                   the user who assigned who assigned the role
	 * @param comments
	 *                   the specified comments
	 * @throws NullPointerException
	 *                              if any of the {@code user}, {@code assignedBy}
	 *                              or {@code comments} is {@code null}.
	 */
	void saveUserRoleHistory(User user, User assignedBy, String comments) throws NullPointerException {
		String message = null;

		if (user == null)
			message = "user is null";
		else if (assignedBy == null)
			message = "assignedBy is null";
		else if (comments == null)
			message = "comments is null";

		if (message != null)
			throw new NullPointerException(message);

		UserRoleHistory urh = userRoleHistoryFrom(user, assignedBy, comments);
		roleHistoryRepository.save(urh);
	}

	/**
	 * Saves user account status history for the specified user with specified
	 * details.
	 * 
	 * @param user
	 *                   the specified user
	 * @param assignedBy
	 *                   the user who assigned who assigned the role, can be
	 *                   {@code null}
	 * @param comments
	 *                   the specified comments
	 * @throws NullPointerException
	 *                              if any of the {@code user} or {@code comments}
	 *                              is {@code null}.
	 */
	void saveUserAccountStatusHistory(User user, User assignedBy, String comments) throws NullPointerException {
		UserAccountStatusHistory uash = accountStatusHistoryFrom(user, assignedBy, comments);
		acStatusHistoryRepository.save(uash);
	}

	private UserRoleHistory userRoleHistoryFrom(User user, User assignedBy, String comments) {
		UserRoleHistory urh = new UserRoleHistory();

		urh.setUser(user);
		urh.setAssignedBy(assignedBy);
		urh.setRole(user.getRole());

		urh.setComments(comments);

		return urh;
	}

	private UserAccountStatusHistory accountStatusHistoryFrom(User user, User assignedBy, String comments) {
		UserAccountStatusHistory uash = new UserAccountStatusHistory();

		uash.setAccountStatus(user.getAccountStatus());
		uash.setUser(user);

		if (assignedBy != null)
			uash.setComments(assignedBy.getEmail() + ": " + comments);
		else
			uash.setComments(comments);

		return uash;
	}

}
