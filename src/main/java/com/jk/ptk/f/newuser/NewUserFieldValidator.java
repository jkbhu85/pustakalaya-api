package com.jk.ptk.f.newuser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jk.ptk.app.DataValidation;
import com.jk.ptk.app.ErrorInfo;
import com.jk.ptk.app.ValidationException;
import com.jk.ptk.app.response.ResponseCode;
import com.jk.ptk.f.user.UserService;
import com.jk.ptk.util.PatternStore;

/**
 * Validates the fields of the type {@link NewUser}.
 * 
 * @author Jitendra
 *
 */
@Component("NewUserValidator")
public class NewUserFieldValidator implements DataValidation<NewUser> {

	@Autowired
	private UserService userService;

	private static final String FIELD_FIRST_NAME = "firstName";
	private static final String FIELD_LAST_NAME = "lastName";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_LOCALE = "locale";

	@Override
	public void validate(NewUser newUser) throws ValidationException {
		List<ErrorInfo> errList = new ArrayList<>();
		ErrorInfo error;

		error = validateFirstName(newUser.getFirstName(), true);
		if (error != null) {
			error.setFieldName(FIELD_FIRST_NAME);
			errList.add(error);
		}

		error = validateLastName(newUser.getLastName(), true);
		if (error != null) {
			error.setFieldName(FIELD_LAST_NAME);
			errList.add(error);
		}

		error = validateEmail(newUser.getEmail(), true);
		if (error != null) {
			error.setFieldName(FIELD_EMAIL);
			errList.add(error);
		}

		error = validateLocaleStr(newUser.getLocaleStr(), true);
		if (error != null) {
			error.setFieldName(FIELD_LOCALE);
			errList.add(error);
		}

		// if there are errors throw exception
		if (errList.size() > 0) {
			throw new ValidationException(errList);
		}
	}

	private ErrorInfo validateFirstName(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return new ErrorInfo(ResponseCode.VALUE_TOO_SMALL_OR_EMPTY);
			else
				return null;
		}

		if (value.length() > 30)
			return new ErrorInfo(ResponseCode.VALUE_TOO_LARGE);

		return null;
	}

	private ErrorInfo validateLastName(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return new ErrorInfo(ResponseCode.VALUE_TOO_SMALL_OR_EMPTY);
			else
				return null;
		}

		if (value.length() > 30)
			return new ErrorInfo(ResponseCode.VALUE_TOO_LARGE);

		return null;
	}

	private ErrorInfo validateLocaleStr(String value, boolean mandatory) {
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return new ErrorInfo(ResponseCode.VALUE_TOO_SMALL_OR_EMPTY);
			else
				return null;
		}

		Matcher m = PatternStore.LOCALE.matcher(value);
		if (!m.matches())
			return new ErrorInfo(ResponseCode.INVALID_FORMAT);

		return null;
	}

	private ErrorInfo validateEmail(String value, boolean mandatory) {
		// check if value is empty
		if (value == null || value.isEmpty()) {
			if (mandatory)
				return new ErrorInfo(ResponseCode.VALUE_TOO_SMALL_OR_EMPTY);
			else
				return null;
		}

		// match email with pattern
		Matcher m = PatternStore.EMAIL.matcher(value);
		if (!m.matches())
			return new ErrorInfo(ResponseCode.INVALID_FORMAT);

		// check whether it already exists
		if (userService.userExists(value))
			return new ErrorInfo(ResponseCode.VALUE_ALREADY_EXIST);

		return null;
	}

}
