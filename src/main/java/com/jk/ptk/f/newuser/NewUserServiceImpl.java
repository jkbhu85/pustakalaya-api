package com.jk.ptk.f.newuser;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.ptk.app.App;
import com.jk.ptk.app.DataValidation;
import com.jk.ptk.app.ValidationException;
import com.jk.ptk.f.user.UserRole;
import com.jk.ptk.util.MailConsts;
import com.jk.ptk.util.UuidUtils;
import com.jk.ptk.util.mail.MailTemplateService;

/**
 * An implementation of the {@code NewUserService} type.
 *
 * @author Jitendra
 *
 */
@Service
public class NewUserServiceImpl implements NewUserService, DataValidation<NewUser> {
	@Autowired
	private NewUserRepository repository;

	@Autowired
	private MailTemplateService mailService;

	@Override
	public void addNewUser(NewUser newUser) throws ValidationException {
		// validate fields
		validate(newUser);

		// set required fields
		newUser.setId(UuidUtils.generate());
		newUser.setRole(UserRole.DEFAULT_USER_ROLE);

		// store into db
		//repository.saveNewUser(newUser);

		// notify user
		sendMail(newUser);
	}

	private boolean sendMail(NewUser newUser) {
		String registrationUri = App.getUrl("/ptk/newUser/" + newUser.getId());
		Map<String, Object> params = new HashMap<>();

		params.put(MailConsts.PARAM_COMPLETE_REGISRATION_USER_NAME, newUser.getFirstName());
		params.put(MailConsts.PARAM_COMPLETE_REGISTRATION_EXPIRE, App.registrationLinkExpireDuration());
		params.put(MailConsts.PARAM_COMPLETE_REGISTRATION_LINK, registrationUri);

		return mailService.sendMail(MailConsts.TEMPLATE_COMPLETE_REGISTRAION, newUser.getEmail(), MailConsts.PROP_SUBJECT_COMPLETE_REGISTRAION,
				params, new Locale(newUser.getLocaleStr()));
	}

	@Override
	public NewUser getNewUser(String id) {
		return repository.findNewUser(id);
	}

	@Override
	public void validate(NewUser newUser) throws ValidationException {
		// TODO Auto-generated method stub
	}

}
