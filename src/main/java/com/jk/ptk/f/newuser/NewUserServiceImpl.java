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
import com.jk.ptk.f.user.UserRole;
import com.jk.ptk.util.LocaleUtil;
import com.jk.ptk.util.MailConsts;
import com.jk.ptk.util.UuidUtils;
import com.jk.ptk.util.mail.MailModel;
import com.jk.ptk.util.mail.MailTemplateService;
import com.jk.ptk.validation.DataValidator;
import com.jk.ptk.validation.ValidationException;

/**
 * An implementation of the {@code NewUserService} type.
 *
 * @author Jitendra
 *
 */
@Service
public class NewUserServiceImpl implements NewUserService {
	private static final Logger log = LoggerFactory.getLogger(NewUserServiceImpl.class);

	@Autowired
	private NewUserRepository repository;

	@Autowired
	private MailTemplateService mailService;

	@Autowired
	@Qualifier("NewUserValidator")
	DataValidator<NewUser> validator;

	@Override
	@Transactional
	public void addNewUser(NewUser newUser) throws ValidationException {
		// validate fields
		if (validator != null)
			validator.validate(newUser);

		// set required fields
		newUser.setId(UuidUtils.generate());
		newUser.setRole(UserRole.DEFAULT_USER_ROLE);

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() + App.registrationLinkExpireDuration() * 60 * 60 * 1000L);
		LocalDateTime expiresOn
			= LocalDateTime.of(
					cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH) + 1,
					cal.get(Calendar.DAY_OF_MONTH),
					cal.get(Calendar.HOUR_OF_DAY),
					cal.get(Calendar.MINUTE),
					cal.get(Calendar.SECOND),
					cal.get(Calendar.MILLISECOND)
					);

		newUser.setExpiresOn(expiresOn);

		// delete information if it was saved before
		repository.removeNewUser(newUser.getEmail());

		// store the information
		repository.saveNewUser(newUser);

		// notify user
		try {
			sendMail(newUser);
		} catch (Throwable e) {
			repository.removeNewUser(newUser.getEmail());
			log.debug("Error in sending mail to user. Deleting user info of {}", newUser.getEmail());
			throw e;
		}
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
	public NewUser getNewUser(String id) throws ValidationException {
		NewUser user = repository.findNewUser(id);

		if (user == null || LocalDateTime.now().isAfter(user.getExpiresOn())) {
			Map<String, ResponseCode> errorMap = new HashMap<>();
			errorMap.put(NewUser.FIELD_REGISTRATION_ID, ResponseCode.RESOURCE_DOES_NOT_EXIST);

			if (user != null)
				repository.remove(user);

			throw new ValidationException(errorMap);
		}

		return user;
	}

	@Override
	public NewUser findNewUser(String email) {
		return repository.findNewUserByEmail(email);
	}

	@Override
	@Transactional
	public void remove(NewUser newUser) {
		repository.remove(newUser);
	}

}