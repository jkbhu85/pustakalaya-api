package com.jk.ptk.f.user;

import org.springframework.stereotype.Component;

import com.jk.ptk.f.country.Country;
import com.jk.ptk.f.country.CountryRepository;
import com.jk.ptk.f.newuser.NewUser;
import com.jk.ptk.f.newuser.NewUserRepository;
import com.jk.ptk.util.mail.MailHelper;

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
	private MailHelper mailHelper;

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
			MailHelper mailHelper) {
		this.countryRepository = countryRepository;
		this.newUserRepository = newUserRepository;
		this.mailHelper = mailHelper;
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
		mailHelper.sendMailOnAccountCreationCompletion(user);
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
