package com.jk.ptk.f.user;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jk.ptk.app.App;
import com.jk.ptk.app.ResourceExpiredException;
import com.jk.ptk.f.country.CountryRepository;
import com.jk.ptk.f.newuser.NewUser;
import com.jk.ptk.f.newuser.NewUserRepository;
import com.jk.ptk.security.login.CredentialsUtil;
import com.jk.ptk.security.login.InvalidCredentialsException;
import com.jk.ptk.util.DateUtils;
import com.jk.ptk.util.LocaleUtil;
import com.jk.ptk.util.MailConsts;
import com.jk.ptk.util.mail.MailModel;
import com.jk.ptk.util.mail.MailTemplateService;
import com.jk.ptk.validation.DataValidator;
import com.jk.ptk.validation.FormField;
import com.jk.ptk.validation.ValidationException;

@Component
public class UserServiceImpl implements UserService {
	private UserRepository repository;
	private CountryRepository countryRepository;
	private NewUserRepository newUserRepository;
	private MailTemplateService mailService;

	@Autowired
	private DataValidator<UserFormValues> dataValidator;

	@Autowired
	public UserServiceImpl(UserRepository repository, CountryRepository countryRepository,
			NewUserRepository newUserRepository, MailTemplateService mailService) {
		this.repository = repository;
		this.countryRepository = countryRepository;
		this.newUserRepository = newUserRepository;
		this.mailService = mailService;
	}

	public void setDataValidator(DataValidator<UserFormValues> dataValidator) {
		this.dataValidator = dataValidator;
	}

	@Override
	public User findUser(String email) {
		return repository.findUser(email);
	}

	@Override
	public void addUser(UserFormValues userFormValues) throws ValidationException, ResourceExpiredException {
		NewUser newUser = newUserRepository.findNewUserByEmail(userFormValues.getEmail());

		if (newUser == null || newUser.getExpiresOn().isBefore(LocalDateTime.now()))
			throw new ResourceExpiredException("Registration id has expired.");

		if (dataValidator != null)
			dataValidator.validate(userFormValues);

		User user = toUser(userFormValues, newUser);

		newUserRepository.removeNewUser(newUser.getEmail());
		repository.addUser(user);
		sendMail(user);
	}

	private void sendMail(User user) {
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

		String loginUrl = App.getUrl("login");
		params.put(MailConsts.PARAM_LOGIN_PAGE, loginUrl);

		mailService.sendMail(model);
	}

	private User toUser(UserFormValues formValues, NewUser newUser) {
		User user = new User();

		user.setFirstName(formValues.getFirstName());
		user.setLastName(formValues.getLastName());
		user.setGender(formValues.getGender());

		long countryCode = Integer.parseInt(formValues.getIsdCode());
		user.setCountry(countryRepository.getCountry(countryCode));
		user.setEmail(formValues.getEmail());
		user.setMobile(formValues.getMobile());

		user.setLocaleValue(formValues.getLocale());
		user.setDateOfBirth(DateUtils.parseDate(formValues.getDateOfBirth()));
		user.setRole(newUser.getRole());
		user.setAccountStatus(UserAcStatus.ACTIVE);

		user.setBookQuota(getBookQuotaForRole(newUser.getRole()));
		user.setCreatedOn(newUser.getCreatedOn());
		user.setAcCreatedBy(newUser.getAcCreatedBy());

		user.setSecurityQuestion(formValues.getSecurityQuestion());
		user.setSecurityAnswer(formValues.getSecurityAnswer());

		String passwordSalt = CredentialsUtil.getPasswordSalt();
		int passwordVersion = App.getCurrentPasswordVersion();
		String passwordHash = CredentialsUtil.getPasswordHash(formValues.getPassword(), passwordSalt, passwordVersion);

		user.setPasswordHash(passwordHash);
		user.setPasswordSalt(passwordSalt);
		user.setPasswordVersion(passwordVersion);
		user.setUnsuccessfulTries(0);

		return user;
	}

	private int getBookQuotaForRole(UserRole role) {
		final int roleId = role.getId();

		if (roleId == UserRole.MEMBER.getId())
			return App.getMemberBookQuota();
		else if (roleId == UserRole.LIBRARIAN.getId())
			return App.getLibrarianBookQuota();
		else if (roleId == UserRole.ADMIN.getId())
			return App.getAdminBookQuota();
		else
			return App.getNoneBookQuota();
	}

	@Override
	public void updatePassword(String email, String currentPassword, String newPassword, String confirmNewPassword)
			throws InvalidCredentialsException, ValidationException {
		User user = repository.findUser(email);

		if (user == null)
			throw new RuntimeException("Invalid user");

		if (!isPasswordValid(currentPassword, user))
			throw new InvalidCredentialsException("Old password didn't match.");

		FormField pwd = new FormField(newPassword, UserFormValues.FIELD_PASSWORD, true);
		FormField cpwd = new FormField(confirmNewPassword, UserFormValues.FIELD_CONFIRM_PASSWORD, true,
				Arrays.asList(pwd));

		// validate data
		if (dataValidator != null) {
			dataValidator.validate(Arrays.asList(pwd, cpwd));
		}

		Integer currentPasswordVersion = App.getCurrentPasswordVersion();
		String newPasswordSalt = CredentialsUtil.getPasswordSalt();
		String newPasswordHash = CredentialsUtil.getPasswordHash(newPassword, newPasswordSalt, currentPasswordVersion);

		// perform update
		repository.updatePassword(email, newPasswordHash, newPasswordSalt, currentPasswordVersion);
	}

	private boolean isPasswordValid(String password, User user) {
		String passwordHash = CredentialsUtil.getPasswordHash(password, user.getPasswordSalt(),
				user.getPasswordVersion());

		return user.getPasswordHash().equals(passwordHash);
	}

	@Override
	public void updateSecurityQuestion(String email, String password, String question, String answer)
			throws InvalidCredentialsException, ValidationException {
		User user = repository.findUser(email);

		if (isPasswordValid(password, user)) {
			FormField questionField = new FormField(question, UserFormValues.FIELD_SECURITY_QUESTION, true);
			FormField answerField = new FormField(answer, UserFormValues.FIELD_SECURITY_ANSWER, true);

			if (dataValidator != null) {
				dataValidator.validate(Arrays.asList(questionField, answerField));
			}

			repository.updateSecurityQuestion(user.getEmail(), question, answer);
		} else
			throw new InvalidCredentialsException("Password didn't match.");
	}

	@Override
	public boolean userExists(String email) {
		return repository.userExists(email);
	}

	@Override
	public boolean mobileExists(String mobile) {
		return repository.mobileExists(mobile);
	}

	@Override
	public void updateUnsuccessfulTries(String email, Integer tries) {
		repository.updateUnsuccessfulTries(email, tries);
	}

}
