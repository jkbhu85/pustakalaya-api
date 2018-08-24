package com.jk.ptk.f.book;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.springframework.stereotype.Component;

import com.jk.ptk.app.response.ResponseCode;
import com.jk.ptk.currency.CurrencyRepository;
import com.jk.ptk.util.PatternStore;
import com.jk.ptk.validation.DataValidator;
import com.jk.ptk.validation.FormField;
import com.jk.ptk.validation.OperationNotSupportedException;
import com.jk.ptk.validation.ValidationException;

/**
 * Validates fields of the type {@code BookV}.
 * 
 * @author Jitendra
 *
 */
@Component("BookFieldValidator")
public class BookFieldValidator implements DataValidator<BookV> {
	private static final int TITLE_MAX_LENGTH = 150;
	private static final int NUMBER_OF_PAGES_MAX = 99999;
	private static final int AUTHORS_MAX_LENGTH = 250;
	private static final int PUBLICATION_MAX_LEN = 100;
	private static final int VOLUME_MAX = 999;
	private static final int NUMBER_OF_COPIES_MAX = 999;
	private static final double PRICE_MAX = 9999999.99;

	private final BookRepository repository;
	private final CurrencyRepository currencyRepository;

	public BookFieldValidator(BookRepository repository, CurrencyRepository currencyRepository) {
		this.repository = repository;
		this.currencyRepository = currencyRepository;
	}

	private ResponseCode validateField(String fieldName, String value, boolean mandatory,
			Map<String, String> crossFieldMap) {
		if (fieldName == null || fieldName.isEmpty())
			return null;

		switch (fieldName) {
		case BookV.FIELD_BOOK_ID:
			return validateId(value, mandatory);
		case BookV.FIELD_TITLE:
			return validateTitle(value, mandatory);
		case BookV.FIELD_BOOK_CATEGORY:
			return validateBookCategory(value, mandatory);
		case BookV.FIELD_PRICE:
			return validatePrice(value, mandatory);
		case BookV.FIELD_CURRENCY:
			return validateCurrency(value, mandatory);
		case BookV.FIELD_NUMBER_OF_PAGES:
			return validateNumberOfPages(value, mandatory);
		case BookV.FIELD_AUTHORS:
			return validateAuthors(value, mandatory);
		case BookV.FIELD_EDITION:
			return validateEdition(value, mandatory);
		case BookV.FIELD_ISBN:
			return validateIsbn(value, mandatory);
		case BookV.FIELD_PUBLICATION:
			return validatePublication(value, mandatory);
		case BookV.FIELD_VOLUME:
			return validateVolume(value, mandatory);
		case BookV.FIELD_NO_OF_COPIES:
			return validateNoOfCopies(value, mandatory);
		}

		return null;
	}

	@Override
	public void validate(FormField formField) throws ValidationException, OperationNotSupportedException {
		Map<String, String> crossFieldMap = null;

		if (formField.getCrossFieldList() != null || formField.getCrossFieldList().size() > 0) {
			crossFieldMap = new HashMap<>();
			for (FormField ff : formField.getCrossFieldList())
				crossFieldMap.put(ff.getFieldName(), ff.getValue());
		}

		ResponseCode errorCode = validateField(formField.getFieldName(), formField.getValue(), formField.isMandatory(),
				crossFieldMap);

		if (errorCode == null)
			return;

		Map<String, ResponseCode> errorMap = new HashMap<>();
		errorMap.put(formField.getFieldName(), errorCode);

		throw new ValidationException(errorMap);
	}

	@Override
	public void validate(List<FormField> formFieldList) throws ValidationException, OperationNotSupportedException {
		if (formFieldList == null || formFieldList.isEmpty())
			return;

		Map<String, ResponseCode> errorMap = new HashMap<>();

		for (FormField formField : formFieldList) {
			Map<String, String> crossFieldMap = null;

			if (formField.getCrossFieldList() != null || formField.getCrossFieldList().size() > 0) {
				crossFieldMap = new HashMap<>();
				for (FormField ff : formField.getCrossFieldList())
					crossFieldMap.put(ff.getFieldName(), ff.getValue());
			}

			ResponseCode errorCode = validateField(formField.getFieldName(), formField.getValue(),
					formField.isMandatory(), crossFieldMap);

			if (errorCode != null)
				errorMap.put(formField.getFieldName(), errorCode);
		}

		if (!errorMap.isEmpty())
			throw new ValidationException(errorMap);
	}

	@Override
	public void validate(BookV bookValues) throws ValidationException {
		Map<String, ResponseCode> errorMap = new LinkedHashMap<>();
		ResponseCode errorCode;

		errorCode = validateTitle(bookValues.getTitle(), true);
		if (errorCode != null)
			errorMap.put(BookV.FIELD_TITLE, errorCode);

		errorCode = validateBookCategory(bookValues.getBookCategory(), true);
		if (errorCode != null)
			errorMap.put(BookV.FIELD_BOOK_CATEGORY, errorCode);

		errorCode = validatePrice(bookValues.getPrice(), true);
		if (errorCode != null)
			errorMap.put(BookV.FIELD_PRICE, errorCode);

		errorCode = validateCurrency(bookValues.getCurrency(), true);
		if (errorCode != null)
			errorMap.put(BookV.FIELD_CURRENCY, errorCode);

		errorCode = validateNumberOfPages(bookValues.getNumberOfPages(), true);
		if (errorCode != null)
			errorMap.put(BookV.FIELD_NUMBER_OF_PAGES, errorCode);

		errorCode = validateAuthors(bookValues.getAuthors(), false);
		if (errorCode != null)
			errorMap.put(BookV.FIELD_AUTHORS, errorCode);

		errorCode = validateEdition(bookValues.getEdition(), false);
		if (errorCode != null)
			errorMap.put(BookV.FIELD_EDITION, errorCode);

		errorCode = validateIsbn(bookValues.getIsbn(), false);
		if (errorCode != null)
			errorMap.put(BookV.FIELD_ISBN, errorCode);

		errorCode = validatePublication(bookValues.getPublication(), false);
		if (errorCode != null)
			errorMap.put(BookV.FIELD_PUBLICATION, errorCode);

		errorCode = validateVolume(bookValues.getVolume(), false);
		if (errorCode != null)
			errorMap.put(BookV.FIELD_VOLUME, errorCode);

		errorCode = validateNoOfCopies(bookValues.getNoOfCopies(), true);
		if (errorCode != null)
			errorMap.put(BookV.FIELD_NO_OF_COPIES, errorCode);

		if (errorMap.size() > 0)
			throw new ValidationException(errorMap);
	}

	private ResponseCode validateId(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		Long id = null;
		try {
			id = Long.parseLong(value);
		} catch (NumberFormatException ignore) {
		}

		if (id == null || !repository.doesBookExist(id))
			return ResponseCode.UNSUPPORTED_VALUE;

		return null;
	}

	private ResponseCode validateTitle(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		if (value.length() > TITLE_MAX_LENGTH)
			return ResponseCode.VALUE_TOO_LARGE;

		return null;
	}

	private ResponseCode validateBookCategory(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		Integer cId = null;
		try {
			cId = Integer.parseInt(value);
		} catch (NumberFormatException ignore) {
		}

		if (cId == null || BookCategory.fromId(cId) == null)
			return ResponseCode.UNSUPPORTED_VALUE;

		return null;
	}

	private ResponseCode validatePrice(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		Matcher m = PatternStore.PRICE.matcher(value);
		if (!m.matches())
			return ResponseCode.INVALID_FORMAT;
		
		try {
			double price = Double.parseDouble(value);
			
			if (price > PRICE_MAX) return ResponseCode.VALUE_TOO_LARGE;
			
			return null;
		} catch (NumberFormatException ignore) {}

		return ResponseCode.INVALID_FORMAT;
	}

	private ResponseCode validateCurrency(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		Integer cId = null;
		try {
			cId = Integer.parseInt(value);
		} catch (NumberFormatException ignore) {
		}

		if (cId == null || !currencyRepository.doesCurrencyExist(cId))
			return ResponseCode.UNSUPPORTED_VALUE;

		return null;
	}

	private ResponseCode validateNumberOfPages(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		try {
			int num = Integer.parseInt(value);
			if (num < 1)
				return ResponseCode.UNSUPPORTED_VALUE;
			if (num > NUMBER_OF_PAGES_MAX)
				return ResponseCode.VALUE_TOO_LARGE;
		} catch (NumberFormatException ignore) {
			return ResponseCode.UNSUPPORTED_VALUE;
		}

		return null;
	}

	private ResponseCode validateAuthors(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		if (value.length() > AUTHORS_MAX_LENGTH)
			return ResponseCode.VALUE_TOO_LARGE;

		return null;
	}

	private ResponseCode validateEdition(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		try {
			int num = Integer.parseInt(value);
			if (num < 1)
				return ResponseCode.VALUE_TOO_SMALL;
		} catch (NumberFormatException ignore) {
			return ResponseCode.UNSUPPORTED_VALUE;
		}

		return null;
	}

	private ResponseCode validateIsbn(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		if (!isIsbnValid(value))
			return ResponseCode.INVALID_FORMAT;

		return null;
	}

	private static boolean isIsbnValid(String isbn) {
		if (isbn == null || isbn.isEmpty())
			return false;

		isbn = isbn.replace("-", "");

		if (isbn.length() != 13 && isbn.length() != 10)
			return false;

		if (isbn.length() == 10) {
			try {
				int tot = 0;//calcIsbn10Sum(isbn);
				
				for (int i = 0; i < 9; i++) {
					int digit = Integer.parseInt(isbn.substring(i, i + 1));
					tot += ((10 - i) * digit);
				}

				String checksum = Integer.toString((11 - (tot % 11)) % 11);
				if ("10".equals(checksum)) {
					checksum = "X";
				}

				return checksum.equals(isbn.substring(9));
			} catch (NumberFormatException ignore) {
				return false;
			}
		} else {
			try {
				int tot = 0;//calcIsbn13Sum(isbn);
				
				for (int i = 0; i < 12; i++) {
					int digit = Integer.parseInt(isbn.substring(i, i + 1));
					tot += (i % 2 == 0) ? digit * 1 : digit * 3;
				}

				//checksum must be 0-9. If calculated as 10 then = 0
				int checksum = 10 - (tot % 10);
				if (checksum == 10) {
					checksum = 0;
				}

				return checksum == Integer.parseInt(isbn.substring(12));
			} catch (NumberFormatException ignore) {
				return false;
			}
		}
	}
	
	private static int calcIsbn10Sum(String isbnStr) {
		int num = Integer.parseInt(isbnStr.substring(0, 9));
		int tot = 0;
		int divisor = 10 ^ 8;
		
		for (int i = 0; i < 9; i++) {
			int digit = num / divisor;
			tot += ((10 - i) * digit);
			num %= divisor;
			divisor /= 10;
		}
		
		return tot;
	}
	
	private static int calcIsbn13Sum(String isbnStr) {
		int num = Integer.parseInt(isbnStr.substring(0, 12));
		int tot = 0;
		
		for (int i = 0; i < 12; i++) {
			int digit = num % 10;
			tot += ((i & 1) == 0) ? digit : digit * 3;
			num /= 10;
		}
		
		return tot;
	}

	private ResponseCode validatePublication(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		if (value.length() > PUBLICATION_MAX_LEN)
			return ResponseCode.VALUE_TOO_LARGE;

		return null;
	}

	private ResponseCode validateVolume(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		try {
			int num = Integer.parseInt(value);
			if (num < 0)
				return ResponseCode.UNSUPPORTED_VALUE;
			if (num > VOLUME_MAX)
				return ResponseCode.VALUE_TOO_LARGE;
		} catch (NumberFormatException ignore) {
			return ResponseCode.UNSUPPORTED_VALUE;
		}

		return null;
	}

	private ResponseCode validateNoOfCopies(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		try {
			int num = Integer.parseInt(value);
			if (num < 1)
				return ResponseCode.UNSUPPORTED_VALUE;
			if (num > NUMBER_OF_COPIES_MAX)
				return ResponseCode.VALUE_TOO_LARGE;
		} catch (NumberFormatException ignore) {
			return ResponseCode.UNSUPPORTED_VALUE;
		}

		return null;
	}
}
