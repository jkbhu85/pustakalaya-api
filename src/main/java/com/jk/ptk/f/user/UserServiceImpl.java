package com.jk.ptk.f.user;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jk.ptk.app.App;
import com.jk.ptk.app.ResourceExpiredException;
import com.jk.ptk.f.country.CountryRepository;
import com.jk.ptk.f.newuser.NewUser;
import com.jk.ptk.f.newuser.NewUserRepository;
import com.jk.ptk.f.uash.UserAccountStatusHistory;
import com.jk.ptk.f.uash.UserAccountStatusHistoryRepository;
import com.jk.ptk.f.urh.UserRoleHistory;
import com.jk.ptk.f.urh.UserRoleHistoryRepository;
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

/**
 * An implementation of the {@code UserService} type.
 *
 * @author Jitendra
 */
@Component
public class UserServiceImpl implements UserService {
	private UserRepository repository;
	private CountryRepository countryRepository;
	private NewUserRepository newUserRepository;
	private UserRoleHistoryRepository roleRepository;
	private UserAccountStatusHistoryRepository acStatusRepository;
	private MailTemplateService mailService;

	@Autowired
	@Qualifier("UserFieldValidator")
	private DataValidator<UserV> dataValidator;

	@Autowired
	public UserServiceImpl(UserRepository repository, CountryRepository countryRepository,
			NewUserRepository newUserRepository, UserRoleHistoryRepository roleRepository,
			UserAccountStatusHistoryRepository acStatusRepository, MailTemplateService mailService) {
		this.repository = repository;
		this.countryRepository = countryRepository;
		this.newUserRepository = newUserRepository;
		this.roleRepository = roleRepository;
		this.acStatusRepository = acStatusRepository;
		this.mailService = mailService;
	}

	public void setDataValidator(DataValidator<UserV> dataValidator) {
		this.dataValidator = dataValidator;
	}

	@Override
	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(UserV userValues) throws ValidationException, ResourceExpiredException {
		NewUser newUser = newUserRepository.find(userValues.getRegistrationId());

		if (newUser == null || newUser.getExpiresOn().isBefore(LocalDateTime.now()))
			throw new ResourceExpiredException("Registration id has expired.");

		userValues.setEmail(newUser.getEmail());

		if (dataValidator != null)
			dataValidator.validate(userValues);

		User user = toUser(userValues, newUser);
		UserRoleHistory urh = userRoleHistoryFrom(user);
		UserAccountStatusHistory uash = accountStatusHistoryFrom(user);

		newUserRepository.remove(newUser);
		repository.save(user);
		roleRepository.save(urh);
		acStatusRepository.save(uash);
		sendMail(user);
	}

	private UserRoleHistory userRoleHistoryFrom(User user) {
		UserRoleHistory urh = new UserRoleHistory();

		urh.setUser(user);
		urh.setAssignedBy(user.getAcCreatedBy());
		urh.setRole(user.getRole());
		urh.setComments("Account created");

		return urh;
	}

	private UserAccountStatusHistory accountStatusHistoryFrom(User user) {
		UserAccountStatusHistory uash = new UserAccountStatusHistory();

		uash.setAccountStatus(user.getAccountStatus());
		uash.setUser(user);
		uash.setComments("Account created");

		return uash;
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

		String loginUrl = App.getUrl("/login");
		params.put(MailConsts.PARAM_LOGIN_PAGE, loginUrl);

		mailService.sendMail(model);
	}

	private User toUser(UserV userValues, NewUser newUser) {
		User user = new User();

		user.setFirstName(userValues.getFirstName());
		user.setLastName(userValues.getLastName());
		user.setGender(userValues.getGender());

		long countryCode = Integer.parseInt(userValues.getIsdCode());
		user.setCountry(countryRepository.find(countryCode));
		user.setEmail(userValues.getEmail());
		user.setMobile(userValues.getMobile());

		user.setLocaleValue(userValues.getLocale());
		user.setDateOfBirth(DateUtils.parseDate(userValues.getDateOfBirth()));
		user.setRole(newUser.getRole());
		user.setAccountStatus(UserAcStatus.ACTIVE);

		user.setBookQuota(getBookQuotaForRole(newUser.getRole()));
		user.setCreatedOn(newUser.getCreatedOn());
		user.setAcCreatedBy(newUser.getAcCreatedBy());

		user.setSecurityQuestion(userValues.getSecurityQuestion());
		user.setSecurityAnswer(userValues.getSecurityAnswer());

		String passwordSalt = CredentialsUtil.getPasswordSalt();
		int passwordVersion = App.getCurrentPasswordVersion();
		String passwordHash = CredentialsUtil.getPasswordHash(userValues.getPassword(), passwordSalt, passwordVersion);

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
	public boolean doesUserExist(String email) {
		return repository.doesEmailExists(email);
	}

	@Override
	public boolean doesMobileExists(String mobile) {
		return repository.doesMobileExists(mobile);
	}

	@Override
	@Transactional
	public void updatePassword(String email, String currentPassword, String newPassword, String confirmNewPassword)
			throws InvalidCredentialsException, ValidationException {
		User user = repository.findByEmail(email);

		if (user == null)
			throw new RuntimeException("Invalid user");

		if (!isPasswordValid(currentPassword, user))
			throw new InvalidCredentialsException("Old password didn't match.");

		FormField pwd = new FormField(newPassword, UserV.FIELD_PASSWORD, true);
		FormField cpwd = new FormField(confirmNewPassword, UserV.FIELD_CONFIRM_PASSWORD, true, Arrays.asList(pwd));

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
	@Transactional
	public void updateSecurityQuestionAndAnswer(String email, String password, String question, String answer)
			throws InvalidCredentialsException, ValidationException {
		User user = repository.findByEmail(email);

		if (isPasswordValid(password, user)) {
			FormField questionField = new FormField(question, UserV.FIELD_SECURITY_QUESTION, true);
			FormField answerField = new FormField(answer, UserV.FIELD_SECURITY_ANSWER, true);

			if (dataValidator != null) {
				dataValidator.validate(Arrays.asList(questionField, answerField));
			}

			repository.updateSecurityQuestionAndAnswer(user.getEmail(), question, answer);
		} else
			throw new InvalidCredentialsException("Password didn't match.");
	}

	@Override
	@Transactional
	public void updateUnsuccessfulTries(String email, Integer tries) {
		repository.updateUnsuccessfulTries(email, tries);
	}

}
