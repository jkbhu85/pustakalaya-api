package com.jk.ptk.f.newuser;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jk.ptk.app.App;
import com.jk.ptk.app.response.ResponseCode;
import com.jk.ptk.f.user.User;
import com.jk.ptk.f.user.UserRole;
import com.jk.ptk.f.user.UserService;
import com.jk.ptk.util.LocaleUtil;
import com.jk.ptk.util.MailConsts;
import com.jk.ptk.util.UserUtil;
import com.jk.ptk.util.UuidUtils;
import com.jk.ptk.util.mail.MailModel;
import com.jk.ptk.util.mail.MailTemplateService;
import com.jk.ptk.validation.DataValidator;
import com.jk.ptk.validation.ValidationException;

/**
 * An implementation of the {@code NewUserService} type.
 *
 * @author Jitendra
 */
@Service
public class NewUserServiceImpl implements NewUserService {
	private static final Logger log = LoggerFactory.getLogger(NewUserServiceImpl.class);

	private NewUserRepository repository;
	private MailTemplateService mailService;
	private UserService userService;

	@Autowired
	@Qualifier("NewUserValidator")
	DataValidator<NewUserV> validator;

	@Autowired
	public NewUserServiceImpl(NewUserRepository repository, UserService userService, MailTemplateService mailService) {
		this.repository = repository;
		this.userService = userService;
		this.mailService = mailService;
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
		try {
			sendMail(newUser);
		} catch (Throwable e) {
			log.debug("Error in sending mail to user. Deleting user info of {}", newUser.getEmail());
			throw e;
		}
	}

	private NewUser newUserFrom(NewUserV userValues) {
		NewUser user = new NewUser();

		user.setFirstName(userValues.getFirstName());
		user.setLastName(userValues.getLastName());
		user.setEmail(userValues.getEmail());
		user.setLocaleStr(userValues.getLocale());

		return user;
	}

	private void sendMail(NewUser newUser) {
		String registrationUri = App.getUrl("/user/register?id=" + newUser.getId());
		Map<String, Object> params = new HashMap<>();
		MailModel model = new MailModel();

		model.setParamMap(params);
		model.setRecipient(newUser.getEmail());
		model.setRecipientName(newUser.getFirstName());
		model.setTemplateName(MailConsts.TEMPLATE_COMPLETE_REGISTRATION);
		model.setSubjectPropName(MailConsts.SUBJECT_COMPLETE_REGISTRATION);
		model.setLocale(LocaleUtil.from(newUser.getLocaleStr()));

		params.put(MailConsts.PARAM_NAME_OF_USER, newUser.getFirstName());
		params.put(MailConsts.PARAM_COMPLETE_REGISTRATION_EXPIRE, App.registrationLinkExpireDuration() + "");
		params.put(MailConsts.PARAM_COMPLETE_REGISTRATION_LINK, registrationUri);

		mailService.sendMail(model);
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
