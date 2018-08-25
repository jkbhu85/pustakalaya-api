package com.jk.ptk.f.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jk.ptk.f.bookinstance.BookInstance;
import com.jk.ptk.f.bookinstance.BookInstanceRepository;
import com.jk.ptk.f.bookinstance.BookInstanceStatus;
import com.jk.ptk.f.currency.Currency;
import com.jk.ptk.f.currency.CurrencyRepository;
import com.jk.ptk.f.user.User;
import com.jk.ptk.f.user.UserRepository;
import com.jk.ptk.util.UserUtil;
import com.jk.ptk.validation.DataValidator;
import com.jk.ptk.validation.FormField;
import com.jk.ptk.validation.ValidationException;

@Service
public class BookServiceImpl implements BookService {
	private final BookRepository repository;
	private final BookInstanceRepository biRepository;
	private final UserRepository userRepository;
	private final CurrencyRepository currencyRepository;
	private final DataValidator<BookV> validator;

	@Autowired
	public BookServiceImpl(BookRepository repository, BookInstanceRepository biRepository,
			CurrencyRepository currencyRepository,
			UserRepository userRepository, @Qualifier("BookFieldValidator") DataValidator<BookV> validator) {
		this.repository = repository;
		this.biRepository = biRepository;
		this.userRepository = userRepository;
		this.currencyRepository = currencyRepository;
		this.validator = validator;
	}

	@Override
	@Transactional
	public Long save(BookV bookValues) throws ValidationException {
		if (validator != null) validator.validate(bookValues);

		Book book = toBook(bookValues);
		repository.saveOrUpdate(book);
		Long bookId = book.getId();
		System.out.println(" --- --- --- bookId: " + bookId);
		
		List<BookInstance> biList = toBookInstance(bookValues, book);
		for (BookInstance bi : biList) {
			bi.setBook(book);
			biRepository.saveOrUpdate(bi);
		}
		
		return bookId;
	}

	private Book toBook(BookV bv) {
		Book book = new Book();

		Integer bookCategoryId = Integer.parseInt(bv.getBookCategory());
		BookCategory bookCategory = BookCategory.fromId(bookCategoryId);
		String email = UserUtil.getEmail();
		User addedBy = userRepository.findByEmail(email);

		book.setTitle(bv.getTitle());
		book.setAuthors(bv.getAuthors());
		book.setIsbn(bv.getIsbn());
		book.setBookCategory(bookCategory);
		book.setAddedBy(addedBy);
		
		String edition = bv.getEdition();
		if (edition != null && !edition.isEmpty()) {
			book.setEdition(Integer.parseInt(bv.getEdition()));
		}

		return book;
	}

	private List<BookInstance> toBookInstance(BookV bv, Book book) {
		List<BookInstance> biList = new ArrayList<>();
		final int noOfCopies = Integer.parseInt(bv.getNumberOfCopies());
		Integer currencyId = Integer.parseInt(bv.getCurrency());
		Currency currency = currencyRepository.find(currencyId);
		Double price = Double.parseDouble(bv.getPrice());
		int numOfPages = Integer.parseInt(bv.getNumberOfPages());
		int volume = (bv.getVolume() == null ? 0 : Integer.parseInt(bv.getVolume()));

		for (int i = 0; i < noOfCopies; i++) {
			BookInstance bi = new BookInstance();
			bi.setAddedBy(book.getAddedBy());
			bi.setCurrency(currency);
			bi.setPrice(price);
			bi.setNumOfPages(numOfPages);
			bi.setPublication(bv.getPublication());
			bi.setVolume(volume);
			bi.setStatus(BookInstanceStatus.DEFAULT_BOOK_INSTANCE_STATUS);
			
			biList.add(bi);
		}

		return biList;
	}

	@Override
	public Book find(String bookId) {
		try {
			Long id = Long.parseLong(bookId);
			return repository.find(id);
		} catch (NumberFormatException ignore) {}
		return null;
	}

	@Override
	public Book findByIsbn(String isbn) {
		return repository.findByIsbn(isbn);
	}

	@Override
	public boolean doesBookExist(String bookId) {
		try {
			Long id = Long.parseLong(bookId);
			return repository.doesBookExist(id);
		} catch (NumberFormatException ignore) {}

		return false;
	}

	@Override
	public void changeCategory(String bookId, String categoryId) throws ValidationException {
		FormField bookIdField = new FormField(bookId, BookV.FIELD_BOOK_ID, true);
		FormField categoryIdField = new FormField(categoryId, BookV.FIELD_BOOK_CATEGORY, true);

		if (validator != null) validator.validate(Arrays.asList(bookIdField, categoryIdField));

		Long bId = Long.parseLong(bookId);
		Integer cId = Integer.parseInt(categoryId);
		Book book = repository.find(bId);
		BookCategory bookCategory = BookCategory.fromId(cId);

		book.setBookCategory(bookCategory);
		repository.saveOrUpdate(book);
	}

}
