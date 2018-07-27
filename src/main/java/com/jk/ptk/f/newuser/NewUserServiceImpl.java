package com.jk.ptk.f.newuser;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jk.ptk.app.App;
import com.jk.ptk.app.DataValidation;
import com.jk.ptk.app.ValidationException;
import com.jk.ptk.f.user.UserRole;
import com.jk.ptk.util.MailConsts;
import com.jk.ptk.util.UuidUtils;
import com.jk.ptk.util.mail.MailModel;
import com.jk.ptk.util.mail.MailTemplateService;

/**
 * An implementation of the {@code NewUserService} type.
 *
 * @author Jitendra
 *
 */
@Service
public class NewUserServiceImpl implements NewUserService {
	@Autowired
	private NewUserRepository repository;

	@Autowired
	private MailTemplateService mailService;
	
	@Autowired
	@Qualifier("NewUserValidator")
	DataValidation<NewUser> validator;

	@Override
	public void addNewUser(NewUser newUser) throws ValidationException {
		// validate fields
		if (validator != null)
			validator.validate(newUser);

		// set required fields
		newUser.setId(UuidUtils.generate());
		newUser.setRole(UserRole.DEFAULT_USER_ROLE);

		// delete information if it was saved before
		repository.removeNewUser(newUser.getEmail());

		// store the information
		//repository.saveNewUser(newUser);

		// notify user
		sendMail(newUser);
	}

	private void sendMail(NewUser newUser) {
		String registrationUri = App.getUrl("/user/register?id=" + newUser.getId());
		Map<String, Object> params = new HashMap<>();
		MailModel model = new MailModel();
		
		model.setParamMap(params);
		model.setRecipient(newUser.getEmail());
		model.setRecipientName(newUser.getFirstName());
		model.setTemplateName(MailConsts.TEMPLATE_COMPLETE_REGISTRAION);
		model.setSubjectPropName(MailConsts.PROP_SUBJECT_COMPLETE_REGISTRAION);
		model.setLocale(new Locale(newUser.getLocaleStr()));

		params.put(MailConsts.PARAM_COMPLETE_REGISRATION_USER_NAME, newUser.getFirstName());
		params.put(MailConsts.PARAM_COMPLETE_REGISTRATION_EXPIRE, App.registrationLinkExpireDuration());
		params.put(MailConsts.PARAM_COMPLETE_REGISTRATION_LINK, registrationUri);

		mailService.sendMail(model);
	}

	@Override
	public NewUser getNewUser(String id) {
		return repository.findNewUser(id);
	}

}
