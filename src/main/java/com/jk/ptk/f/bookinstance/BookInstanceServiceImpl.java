package com.jk.ptk.f.bookinstance;

import java.time.LocalDate;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.ptk.app.App;
import com.jk.ptk.app.response.ResponseCode;
import com.jk.ptk.f.bah.BahRepository;
import com.jk.ptk.f.bah.BookAssignmentHistory;
import com.jk.ptk.f.user.User;
import com.jk.ptk.f.user.UserAcStatus;
import com.jk.ptk.f.user.UserRepository;
import com.jk.ptk.validation.InvalidArgumentException;

@Service
public class BookInstanceServiceImpl implements BookInstanceService {
	private BookInstanceRepository repository;
	private UserRepository userRepository;
	private BahRepository bahRepository;

	@Autowired
	public BookInstanceServiceImpl(BookInstanceRepository repository, UserRepository userRepository,
			BahRepository bahRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
		this.bahRepository = bahRepository;
	}

	@Override
	public Map<String, String> findBookInstance(String bookInstanceId) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void assignBookInstance(String bookInstanceId, String email, String requester)
			throws InvalidArgumentException, Exception {
		BookInstance bookInstance = null;
		try {
			Long biId = null;
			biId = Long.parseLong(bookInstanceId);
			bookInstance = repository.find(biId);
		} catch (NumberFormatException ignore) {}

		if (bookInstance == null) {
			throw new InvalidArgumentException(ResponseCode.RESOURCE_DOES_NOT_EXIST, "bookInstanceId");
		}

		if (!bookInstance.getStatus().getId().equals(BookInstanceStatus.AVAILABLE.getId())) {
			throw new InvalidArgumentException(ResponseCode.RESOURCE_NOT_AVAILABLE, "bookInstanceId");
		}

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new InvalidArgumentException(ResponseCode.RESOURCE_DOES_NOT_EXIST, "email");
		}

		UserAcStatus accountStatus = user.getAccountStatus();
		if (UserAcStatus.CLOSED.equals(accountStatus)) {
			throw new InvalidArgumentException(ResponseCode.RESOURCE_DOES_NOT_EXIST, "email");
		}

		if (UserAcStatus.REVOKED.equals(accountStatus)) {
			throw new InvalidArgumentException(ResponseCode.ACCOUNT_ACCESS_REVOKED, "email");
		}

		if (user.getBookQuota() == user.getBookAssignCount()) {
			throw new InvalidArgumentException(ResponseCode.LIMIT_EXCEEDED, "email");
		}

		User requesterUser = userRepository.findByEmail(requester);

		if (requesterUser == null) throw new Exception("Requester with email id " + email + " was not found.");

		bookInstance.setStatus(BookInstanceStatus.ISSUED);
		user.setBookAssignCount(user.getBookAssignCount() + 1);

		BookAssignmentHistory bah = new BookAssignmentHistory();
		LocalDate expectedReturnDate = LocalDate.now().plusDays(App.getBookReturnPeriodDays());

		bah.setBookInstance(bookInstance);
		bah.setIssuedTo(user);
		bah.setIssuedBy(requesterUser);
		bah.setReturnDate(expectedReturnDate);

		repository.saveOrUpdate(bookInstance);
		userRepository.saveOrUpdate(user);
		bahRepository.saveOrUpdate(bah);
	}

	@Override
	public void unassignBookInstance(String bookInstanceId, String amountFined) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBookInstanceStatus(String bookInstanceId, String newStatus) {
		// TODO Auto-generated method stub

	}

}
