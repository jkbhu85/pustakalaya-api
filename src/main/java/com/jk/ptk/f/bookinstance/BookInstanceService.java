package com.jk.ptk.f.bookinstance;

import java.util.Map;

import com.jk.ptk.validation.InvalidArgumentException;

/**
 * Implementations of this interface manipulates the instances of type
 * {BookInstance}.
 * 
 * @author Jitendra
 */
public interface BookInstanceService {
	/**
	 * Returns details of the book instance identified by the specified book
	 * instance id.
	 * 
	 * @param bookInstanceId
	 *                       the specified book instance id
	 * @return details of the book instance identified with the specified book
	 *         instance id
	 */
	Map<String, String> findBookInstance(String bookInstanceId);

	/**
	 * Assigns the book instance identified by the specified book instance id to the
	 * user associated with the specified email.
	 * 
	 * @param bookInstanceId
	 *                       the specified book instance id
	 * @param email
	 *                       the specified email of the user
	 * @param requester
	 *                       the specified requester
	 * @throws InvalidArgumentException
	 *                                  if any of the argument does not satisfy the
	 *                                  criteria for the book assignment
	 * @throws Exception
	 *                                  if requester is not valid
	 */
	void assignBookInstance(String bookInstanceId, String email, String requester)
			throws InvalidArgumentException, Exception;

	/**
	 * Unassigns the book instance identified by the specified instance id.
	 * 
	 * @param bookInstanceId
	 *                       the specified book instance id
	 * @param amountFined
	 *                       amount fined to the user
	 */
	void unassignBookInstance(String bookInstanceId, String amountFined);

	/**
	 * Updates the book instance's status with the new status.
	 * 
	 * @param bookInstanceId
	 *                       the specified book instance id
	 * @param newStatus
	 *                       the new book instance status
	 */
	void updateBookInstanceStatus(String bookInstanceId, String newStatus);
}
