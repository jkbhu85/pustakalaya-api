package com.jk.ptk.f.bookinstance;

import java.time.LocalDate;

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
import com.jk.ptk.mail.BookAssignedMail;
import com.jk.ptk.util.mail.MailHelper;
import com.jk.ptk.validation.InvalidArgumentException;

@Service
public class BookInstanceServiceImpl implements BookInstanceService {
	private BookInstanceRepository biRepository;
	private UserRepository userRepository;
	private BahRepository bahRepository;
	private MailHelper mailHelper;

	@Autowired
	public BookInstanceServiceImpl(BookInstanceRepository biRepository, UserRepository userRepository,
			BahRepository bahRepository, MailHelper mailHelper) {
		this.biRepository = biRepository;
		this.userRepository = userRepository;
		this.bahRepository = bahRepository;
		this.mailHelper = mailHelper;
	}

	@Override
	@Transactional
	public BookInstance findBookInstance(String bookInstanceId) {
		BookInstance bi = biRepository.find(Long.parseLong(bookInstanceId));
		bi.getBook();
		bi.getCurrency();
		return bi;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void assignBookInstance(String bookInstanceId, String email, String requester)
			throws InvalidArgumentException, Exception {
		BookInstance bookInstance = null;
		Long biId = null;

		try {
			biId = Long.parseLong(bookInstanceId);
			bookInstance = biRepository.find(biId);
		} catch (NumberFormatException ignore) {}

		if (bookInstance == null) {
			throw new InvalidArgumentException(ResponseCode.RESOURCE_DOES_NOT_EXIST, "bookInstanceId");
		}

		if (!bookInstance.getStatus().equals(BookInstanceStatus.AVAILABLE)) {
			throw new InvalidArgumentException(ResponseCode.RESOURCE_NOT_AVAILABLE, "bookInstanceId");
		}

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new InvalidArgumentException(ResponseCode.RESOURCE_DOES_NOT_EXIST, "username");
		}

		UserAcStatus accountStatus = user.getAccountStatus();
		if (accountStatus.equals(UserAcStatus.CLOSED) || accountStatus.equals(UserAcStatus.REVOKED)) {
			throw new InvalidArgumentException(ResponseCode.RESOURCE_NOT_AVAILABLE, "username");
		}

		if (user.getBookQuota() == user.getBookAssignCount()) {
			throw new InvalidArgumentException(ResponseCode.LIMIT_EXCEEDED, "username");
		}

		User requesterUser = userRepository.findByEmail(requester);
		if (requesterUser == null) {
			throw new Exception("Requester with email id " + email + " was not found.");
		}

		bookInstance.setStatus(BookInstanceStatus.ISSUED);
		user.setBookAssignCount(user.getBookAssignCount() + 1);

		LocalDate expectedReturnDate = LocalDate.now().plusDays(App.getBookReturnPeriodDays());
		BookAssignmentHistory bah = new BookAssignmentHistory();
		bah.setBookInstance(bookInstance);
		bah.setIssuedTo(user);
		bah.setIssuedBy(requesterUser);
		bah.setExpectedReturnDate(expectedReturnDate);

		biRepository.saveOrUpdate(bookInstance);
		userRepository.saveOrUpdate(user);
		bahRepository.saveOrUpdate(bah);
		sendMail(bookInstance, user, bah);
	}

	private void sendMail(BookInstance bi, User u, BookAssignmentHistory bah) {
		BookAssignedMail data = new BookAssignedMail();
		data.setFirstName(u.getFirstName());
		data.setLastName(u.getLastName());
		data.setEmail(u.getEmail());
		data.setBookTitle(bi.getBook().getTitle());
		data.setLocale(u.getLocaleValue());
		data.setAssignedOn(bah.getIssuedOn());
		data.setReturnOn(bah.getExpectedReturnDate());

		mailHelper.sendMailOnBookAssigned(data);
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
