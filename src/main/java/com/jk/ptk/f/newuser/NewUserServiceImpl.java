package com.jk.ptk.f.newuser;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jk.ptk.app.App;
import com.jk.ptk.app.response.ResponseCode;
import com.jk.ptk.f.user.User;
import com.jk.ptk.f.user.UserRole;
import com.jk.ptk.f.user.UserService;
import com.jk.ptk.util.UserUtil;
import com.jk.ptk.util.UuidUtils;
import com.jk.ptk.util.mail.MailHelper;
import com.jk.ptk.validation.DataValidator;
import com.jk.ptk.validation.ValidationException;

/**
 * An implementation of the {@code NewUserService} type.
 *
 * @author Jitendra
 */
@Service
class NewUserServiceImpl implements NewUserService {
	private NewUserRepository repository;
	private MailHelper mailHelper;
	private UserService userService;

	@Autowired
	@Qualifier("NewUserValidator")
	DataValidator<NewUserV> validator;

	@Autowired
	public NewUserServiceImpl(NewUserRepository repository, UserService userService, MailHelper mailHelper) {
		this.repository = repository;
		this.userService = userService;
		this.mailHelper = mailHelper;
	}

	public void setDataValidator(DataValidator<NewUserV> validator) {
		this.validator = validator;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(NewUserV userValues) throws ValidationException {
		// validate fields
		if (validator != null)
			validator.validate(userValues);

		NewUser newUser = newUserFrom(userValues);

		User acCreatedBy = userService.findByEmail(UserUtil.getEmail());
		// set required fields
		newUser.setAcCreatedBy(acCreatedBy);
		newUser.setId(UuidUtils.generate());
		newUser.setRole(UserRole.DEFAULT_USER_ROLE);

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() + App.registrationLinkExpireDuration() * 60 * 60 * 1000L);
		LocalDateTime expiresOn = LocalDateTime.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));

		newUser.setExpiresOn(expiresOn);

		// delete information if it was saved before
		repository.removeByEmail(newUser.getEmail());

		// store the information
		repository.save(newUser);

		// notify user
		mailHelper.sendMailOnAccountCreation(newUser);
	}

	private NewUser newUserFrom(NewUserV userValues) {
		NewUser user = new NewUser();

		user.setFirstName(userValues.getFirstName());
		user.setLastName(userValues.getLastName());
		user.setEmail(userValues.getEmail());
		user.setLocaleStr(userValues.getLocale());

		return user;
	}

	@Override
	public NewUser find(String id) throws ValidationException {
		NewUser user = repository.find(id);

		if (user == null || LocalDateTime.now().isAfter(user.getExpiresOn())) {
			Map<String, ResponseCode> errorMap = new HashMap<>();
			errorMap.put(NewUserV.FIELD_REGISTRATION_ID, ResponseCode.RESOURCE_DOES_NOT_EXIST);

			if (user != null)
				repository.remove(user);

			throw new ValidationException(errorMap);
		}

		return user;
	}

	@Override
	public NewUser findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	@Transactional
	public void remove(NewUser newUser) {
		repository.remove(newUser);
	}

}
