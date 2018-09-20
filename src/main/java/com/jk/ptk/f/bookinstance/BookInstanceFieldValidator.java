package com.jk.ptk.f.bookinstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.springframework.stereotype.Component;

import com.jk.ptk.app.response.ResponseCode;
import com.jk.ptk.f.book.BookRepository;
import com.jk.ptk.util.PatternStore;
import com.jk.ptk.validation.DataValidator;
import com.jk.ptk.validation.FormField;
import com.jk.ptk.validation.OperationNotSupportedException;
import com.jk.ptk.validation.ValidationException;

/**
 * Validates fields of type {@code BookInstanceV}.
 * 
 * @author Jitendra
 */
@Component("BookInstanceFieldValidator")
public class BookInstanceFieldValidator implements DataValidator<BookInstanceV> {
	private static final int PUBLICATION_MAX_LEN = 100;
	private static final int VOLUME_MAX = 999;
	private static final int NUMBER_OF_COPIES_MAX = 999;
	private static final int NUMBER_OF_PAGES_MAX = 99999;

	private BookRepository bookRepository;

	public BookInstanceFieldValidator(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	private ResponseCode validateField(String fieldName, String value, boolean mandatory,
			Map<String, String> crossFieldMap) {
		if (fieldName == null || fieldName.isEmpty())
			return null;

		switch (fieldName) {
		case BookInstanceV.FIELD_PRICE:
			return validatePrice(value, mandatory);
		case BookInstanceV.FIELD_CURRENCY:
			return validateCurrency(value, mandatory);
		case BookInstanceV.FIELD_NUMBER_OF_PAGES:
			return validateNumberOfPages(value, mandatory);
		case BookInstanceV.FIELD_BOOK_ID:
			return validateId(value, mandatory);
		case BookInstanceV.FIELD_PUBLICATION:
			return validatePublication(value, mandatory);
		case BookInstanceV.FIELD_VOLUME:
			return validateVolume(value, mandatory);
		case BookInstanceV.FIELD_NO_OF_COPIES:
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
	public void validate(BookInstanceV bookValues) throws ValidationException {
		Map<String, ResponseCode> errorMap = new HashMap<>();
		ResponseCode errorCode;

		errorCode = validatePrice(bookValues.getPrice(), true);
		if (errorCode != null)
			errorMap.put(BookInstanceV.FIELD_PRICE, errorCode);

		errorCode = validateCurrency(bookValues.getCurrency(), true);
		if (errorCode != null)
			errorMap.put(BookInstanceV.FIELD_CURRENCY, errorCode);

		errorCode = validateCurrency(bookValues.getNumberOfPages(), true);
		if (errorCode != null)
			errorMap.put(BookInstanceV.FIELD_NUMBER_OF_PAGES, errorCode);

		errorCode = validateId(bookValues.getBookId(), false);
		if (errorCode != null)
			errorMap.put(BookInstanceV.FIELD_BOOK_ID, errorCode);

		errorCode = validatePublication(bookValues.getPublication(), false);
		if (errorCode != null)
			errorMap.put(BookInstanceV.FIELD_PUBLICATION, errorCode);

		errorCode = validateVolume(bookValues.getVolume(), false);
		if (errorCode != null)
			errorMap.put(BookInstanceV.FIELD_VOLUME, errorCode);

		errorCode = validateNoOfCopies(bookValues.getNoOfCopies(), false);
		if (errorCode != null)
			errorMap.put(BookInstanceV.FIELD_NO_OF_COPIES, errorCode);

		if (errorMap.size() > 0)
			throw new ValidationException(errorMap);
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

		return null;
	}

	private ResponseCode validateCurrency(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}
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

	private ResponseCode validateId(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		try {
			Long id = Long.parseLong(value);

			if (!bookRepository.doesBookExist(id))
				return ResponseCode.RESOURCE_DOES_NOT_EXIST;
		} catch (NumberFormatException ignore) {
			return ResponseCode.INVALID_FORMAT;
		}

		return null;
	}

	private ResponseCode validatePublication(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		if (value.length() > PUBLICATION_MAX_LEN)
			return ResponseCode.LENGTH_TOO_LONG;

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
			if (num < 1)
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
