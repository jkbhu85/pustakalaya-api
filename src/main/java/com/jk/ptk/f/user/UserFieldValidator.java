package com.jk.ptk.f.user;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.jk.ptk.app.response.ResponseCode;
import com.jk.ptk.f.country.CountryService;
import com.jk.ptk.util.DateUtils;
import com.jk.ptk.util.LocaleUtil;
import com.jk.ptk.util.PatternStore;
import com.jk.ptk.validation.DataValidator;
import com.jk.ptk.validation.FormField;
import com.jk.ptk.validation.OperationNotSupportedException;
import com.jk.ptk.validation.ValidationException;

/**
 * This class is used to validate the fields of the type
 * {@link UserV}.
 *
 * @author Jitendra
 */
@Component("UserFieldValidator")
public class UserFieldValidator implements DataValidator<UserV> {
	private static final int FIRST_NAME_MAX_LEN = 30;
	private static final int LAST_NAME_MAX_LEN = 30;
	private static final int PASSWORD_MIN_LEN = 6;
	private static final int PASSWORD_MAX_LEN = 12;
	private static final int SECURITY_QUESTION_MIN_LEN = 15;
	private static final int SECURITY_QUESTION_MAX_LEN = 100;
	private static final int SECURITY_ANSWER_MIN_LEN = 3;
	private static final int SECURITY_ANSWER_MAX_LEN = 50;
	private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
	private static final String[] GENDERS = { "M", "F", "O" };

	private UserService userService;
	private CountryService countryService;

	public UserFieldValidator(UserService userService, CountryService countryService) {
		this.userService = userService;
		this.countryService = countryService;
	}

	@Override
	public void validate(FormField formField) throws ValidationException, OperationNotSupportedException {
		ResponseCode errorCode;
		Map<String, ResponseCode> errorMap = new HashMap<>();
		Map<String, String> crossFieldMap = null;

		if (formField.getCrossFieldList() != null && formField.getCrossFieldList().size() != 0) {
			crossFieldMap = new HashMap<>();
			for (FormField f : formField.getCrossFieldList()) {
				if (f == null || f.getFieldName() == null)
					continue;
				crossFieldMap.put(f.getFieldName(), f.getValue());
			}
		}

		errorCode = validateField(formField.getValue(), formField.getFieldName(), formField.isMandatory(),
				crossFieldMap);
		if (errorCode != null) {
			errorMap.put(formField.getFieldName(), errorCode);
		}

		if (errorMap.size() > 0) {
			throw new ValidationException(errorMap);
		}
	}

	@Override
	public void validate(List<FormField> listFormField) throws ValidationException, OperationNotSupportedException {
		ResponseCode errorCode;
		Map<String, ResponseCode> errorMap = new HashMap<>();

		for (FormField ff : listFormField) {
			if (ff == null || ff.getFieldName() == null)
				continue;
			Map<String, String> crossFieldMap = null;

			if (ff.getCrossFieldList() != null && ff.getCrossFieldList().size() != 0) {
				crossFieldMap = new HashMap<>();
				for (FormField f : ff.getCrossFieldList()) {
					if (f == null || f.getFieldName() == null)
						continue;
					crossFieldMap.put(f.getFieldName(), f.getValue());
				}
			}

			errorCode = validateField(ff.getValue(), ff.getFieldName(), ff.isMandatory(), crossFieldMap);
			if (errorCode != null) {
				errorMap.put(ff.getFieldName(), errorCode);
			}
		}

		if (errorMap.size() > 0) {
			throw new ValidationException(errorMap);
		}
	}

	private ResponseCode validateField(String value, String fieldName, boolean mandatory,
			Map<String, String> crossFieldMap) {
		switch (fieldName) {
		case UserV.FIELD_FIRST_NAME:
			return validateFirstName(value, mandatory);
		case UserV.FIELD_LAST_NAME:
			return validateLastName(value, mandatory);
		case UserV.FIELD_MOBILE:
			return validateMobile(value, mandatory);
		case UserV.FIELD_ISD_CODE:
			return validateIsdCode(value, mandatory);
		case UserV.FIELD_GENDER:
			return validateGender(value, mandatory);
		case UserV.FIELD_DATE_OF_BIRTH:
			return validateDateOfBirth(value, mandatory);
		case UserV.FIELD_LOCALE:
			return validateLocaleStr(value, mandatory);
		case UserV.FIELD_PASSWORD:
			return validatePassword(value, mandatory);
		case UserV.FIELD_CONFIRM_PASSWORD:
			return validateConfirmPassword(value, crossFieldMap, mandatory);
		case UserV.FIELD_SECURITY_QUESTION:
			return validateSecurityQuestion(value, mandatory);
		case UserV.FIELD_SECURITY_ANSWER:
			return validateSecurityAnswer(value, mandatory);
		}

		return null;
	}

	@Override
	public void validate(UserV user) throws ValidationException {
		ResponseCode errorCode;
		Map<String, ResponseCode> errorMap = new HashMap<>();
		Map<String, String> crossFieldMap = new HashMap<>();

		errorCode = validateFirstName(user.getFirstName(), true);
		if (errorCode != null) {
			errorMap.put(UserV.FIELD_FIRST_NAME, errorCode);
		}

		errorCode = validateLastName(user.getLastName(), true);
		if (errorCode != null) {
			errorMap.put(UserV.FIELD_LAST_NAME, errorCode);
		}

		errorCode = validateLocaleStr(user.getLocale(), true);
		if (errorCode != null) {
			errorMap.put(UserV.FIELD_LOCALE, errorCode);
		}

		errorCode = validateGender(user.getGender(), true);
		if (errorCode != null) {
			errorMap.put(UserV.FIELD_GENDER, errorCode);
		}

		errorCode = validateDateOfBirth(user.getDateOfBirth(), true);
		if (errorCode != null) {
			errorMap.put(UserV.FIELD_DATE_OF_BIRTH, errorCode);
		}

		errorCode = validateMobile(user.getMobile(), true);
		if (errorCode != null) {
			errorMap.put(UserV.FIELD_MOBILE, errorCode);
		}

		errorCode = validateIsdCode(user.getIsdCode(), true);
		if (errorCode != null) {
			errorMap.put(UserV.FIELD_ISD_CODE, errorCode);
		}

		errorCode = validatePassword(user.getPassword(), true);
		if (errorCode != null) {
			errorMap.put(UserV.FIELD_PASSWORD, errorCode);
		}

		crossFieldMap.put(UserV.FIELD_PASSWORD, user.getPassword());
		errorCode = validateConfirmPassword(user.getConfirmPassword(), crossFieldMap, true);
		if (errorCode != null) {
			errorMap.put(UserV.FIELD_CONFIRM_PASSWORD, errorCode);
		}
		crossFieldMap.clear();

		errorCode = validateSecurityQuestion(user.getSecurityQuestion(), true);
		if (errorCode != null) {
			errorMap.put(UserV.FIELD_SECURITY_QUESTION, errorCode);
		}

		errorCode = validateSecurityAnswer(user.getSecurityAnswer(), true);
		if (errorCode != null) {
			errorMap.put(UserV.FIELD_SECURITY_ANSWER, errorCode);
		}

		// if there are errors then throw exception
		if (errorMap.size() > 0) {
			throw new ValidationException(errorMap);
		}
	}

	private ResponseCode validateFirstName(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		if (value.length() > FIRST_NAME_MAX_LEN)
			return ResponseCode.VALUE_TOO_LARGE;

		return null;
	}

	private ResponseCode validateLastName(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		if (value.length() > LAST_NAME_MAX_LEN)
			return ResponseCode.VALUE_TOO_LARGE;

		return null;
	}

	private ResponseCode validateGender(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		for (String gender : GENDERS) {
			if (gender.equals(value))
				return null;
		}

		return ResponseCode.UNSUPPORTED_VALUE;
	}

	private ResponseCode validateDateOfBirth(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		Matcher m = DATE_PATTERN.matcher(value);
		if (!m.matches())
			return ResponseCode.INVALID_FORMAT;

		LocalDate dateOfBirth = DateUtils.parseDate(value);

		if (dateOfBirth == null)
			return ResponseCode.INVALID_FORMAT;

		LocalDate today = LocalDate.now();
		LocalDate minDate = today.minusYears(100);
		LocalDate maxDate = today.minusYears(3);

		if (dateOfBirth.isBefore(minDate))
			return ResponseCode.VALUE_TOO_SMALL;
		if (dateOfBirth.isAfter(maxDate))
			return ResponseCode.VALUE_TOO_LARGE;

		return null;
	}

	private ResponseCode validateLocaleStr(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		Matcher m = PatternStore.LOCALE.matcher(value);
		if (!m.matches())
			return ResponseCode.INVALID_FORMAT;

		if (!LocaleUtil.isSupported(value))
			return ResponseCode.UNSUPPORTED_VALUE;

		return null;
	}

	private ResponseCode validateMobile(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		Matcher m = PatternStore.MOBILE.matcher(value);
		if (!m.matches())
			return ResponseCode.INVALID_FORMAT;

		if (userService.doesMobileExists(value))
			return ResponseCode.VALUE_ALREADY_EXIST;

		return null;
	}

	private ResponseCode validateIsdCode(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		try {
			long countryCode = Integer.parseInt(value);

			if (countryService.find(countryCode) != null)
				return null;
		} catch (NumberFormatException ignore) {
		}

		return ResponseCode.UNSUPPORTED_VALUE;
	}

	private ResponseCode validatePassword(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		if (value.length() < PASSWORD_MIN_LEN)
			return ResponseCode.VALUE_TOO_SMALL;

		if (value.length() > PASSWORD_MAX_LEN)
			return ResponseCode.VALUE_TOO_LARGE;

		return null;
	}

	private ResponseCode validateConfirmPassword(String value, Map<String, String> crossFields, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		String password = crossFields.get(UserV.FIELD_PASSWORD);

		if (password != null && password.equals(value))
			return null;

		return ResponseCode.DOES_NOT_MATCH;
	}

	private ResponseCode validateSecurityQuestion(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		if (value.length() < SECURITY_QUESTION_MIN_LEN)
			return ResponseCode.VALUE_TOO_SMALL;

		if (value.length() > SECURITY_QUESTION_MAX_LEN)
			return ResponseCode.VALUE_TOO_LARGE;

		return null;
	}

	private ResponseCode validateSecurityAnswer(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return ResponseCode.EMPTY_VALUE;
			else
				return null;
		}

		if (value.length() < SECURITY_ANSWER_MIN_LEN)
			return ResponseCode.VALUE_TOO_SMALL;

		if (value.length() > SECURITY_ANSWER_MAX_LEN)
			return ResponseCode.VALUE_TOO_LARGE;

		return null;
	}

}
