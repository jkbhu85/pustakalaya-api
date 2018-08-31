package com.jk.ptk.f.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jk.ptk.app.App;
import com.jk.ptk.f.country.Country;
import com.jk.ptk.f.country.CountryRepository;
import com.jk.ptk.f.newuser.NewUser;
import com.jk.ptk.f.newuser.NewUserRepository;
import com.jk.ptk.util.LocaleUtil;
import com.jk.ptk.util.MailConsts;
import com.jk.ptk.util.mail.MailModel;
import com.jk.ptk.util.mail.MailTemplateService;

/**
 * This class helps implementations of {@link UserService} to do a few tasks as
 * specified by its methods.
 * 
 * @author Jitendra
 *
 */
@Component
class UserServiceHelper {
	private CountryRepository countryRepository;
	private NewUserRepository newUserRepository;
	private MailTemplateService mailTemplateService;

	/**
	 * Creates an instance with specified dependencies.
	 * 
	 * @param countryRepository
	 *                            country repository
	 * @param newUserRepository
	 *                            new user repository
	 * @param mailTemplateService
	 *                            mail template service
	 */
	UserServiceHelper(CountryRepository countryRepository, NewUserRepository newUserRepository,
			MailTemplateService mailTemplateService) {
		this.countryRepository = countryRepository;
		this.newUserRepository = newUserRepository;
		this.mailTemplateService = mailTemplateService;
	}

	/**
	 * Returns a country as identified by the specified {@code id}.
	 * 
	 * @param id
	 *           the specified id
	 * @return a country as identified by the specified {@code id}
	 */
	Country findCountry(Integer id) {
		return countryRepository.find(id);
	}

	/**
	 * Removes the specified {@code partialRegistration} of the user.
	 * 
	 * @param partialRegistration
	 *                            the specified partial registration
	 */
	void removePartialRegistration(NewUser partialRegistration) {
		newUserRepository.remove(partialRegistration);
	}

	/**
	 * Sends mail with specified user's details on completion of account creation.
	 * 
	 * @param user
	 *             the specified user
	 */
	void sendMailOnAccountComplete(User user) {
		Map<String, Object> params = new HashMap<>();
		MailModel model = new MailModel();

		model.setParamMap(params);
		model.setRecipient(user.getEmail());
		model.setRecipientName(user.getFirstName());
		model.setTemplateName(MailConsts.TEMPLATE_ACCOUNT_CREATION_COMPLETE);
		model.setSubjectPropName(MailConsts.SUBJECT_ACCOUNT_CREATION_COMPLETE);
		model.setLocale(LocaleUtil.from(user.getLocaleValue()));

		params.put(MailConsts.PARAM_NAME_OF_USER, user.getFirstName());
		params.put(MailConsts.PARAM_EMAIL_OF_USER, user.getEmail());

		String loginUrl = App.getUrl("/login");
		params.put(MailConsts.PARAM_LOGIN_PAGE, loginUrl);

		mailTemplateService.sendMail(model);
	}

	/**
	 * Returns the new user identified by the specified email.
	 * 
	 * @param email
	 *                       the specified email
	 * @return new user associated with the specified email
	 */
	NewUser findNewUser(String email) {
		return newUserRepository.findByEmail(email);
	}

}
