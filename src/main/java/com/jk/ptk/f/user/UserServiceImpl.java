package com.jk.ptk.f.user;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jk.ptk.app.App;
import com.jk.ptk.app.ResourceExpiredException;
import com.jk.ptk.f.newuser.NewUser;
import com.jk.ptk.security.login.CredentialsUtil;
import com.jk.ptk.security.login.InvalidCredentialsException;
import com.jk.ptk.util.DateUtils;
import com.jk.ptk.validation.DataValidator;
import com.jk.ptk.validation.FormField;
import com.jk.ptk.validation.ValidationException;

/**
 * An implementation of the {@code UserService} type. This class validates the
 * user information before saving it if a data validator is available.
 *
 * @author Jitendra
 */
@Service
class UserServiceImpl implements UserService {
	private UserRepository repository;
	private RoleAndAccountStatusManager roleAndAccountStatusManager;
	private UserServiceHelper userServiceHelper;

	private DataValidator<UserV> dataValidator;

	/**
	 * Creates an instance with specified dependencies.
	 * 
	 * @param repository
	 *                                    the user repository
	 * @param roleAndAccountStatusManager
	 *                                    the role and account manager instance
	 * @param userServiceHelper
	 *                                    instance of user service helper class
	 */
	@Autowired
	public UserServiceImpl(UserRepository repository, RoleAndAccountStatusManager roleAndAccountStatusManager,
			UserServiceHelper userServiceHelper) {
		this.repository = repository;
		this.roleAndAccountStatusManager = roleAndAccountStatusManager;
		this.userServiceHelper = userServiceHelper;
	}

	@Autowired
	@Qualifier("UserFieldValidator")
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
		NewUser newUser = userServiceHelper.findNewUser(userValues.getEmail());

		if (newUser == null || newUser.getExpiresOn().isBefore(LocalDateTime.now()))
			throw new ResourceExpiredException("Registration id has expired.");

		userValues.setEmail(newUser.getEmail());

		if (dataValidator != null)
			dataValidator.validate(userValues);

		User user = toUser(userValues, newUser);

		userServiceHelper.removePartialRegistration(newUser);
		repository.saveOrUpdate(user);
		roleAndAccountStatusManager.saveUserAccountStatusHistory(user, user.getAcCreatedBy(), "Account created");
		roleAndAccountStatusManager.saveUserRoleHistory(user, user.getAcCreatedBy(), "Account created");
		userServiceHelper.sendMailOnAccountComplete(user);
	}

	private User toUser(UserV userValues, NewUser newUser) {
		User user = new User();

		user.setFirstName(userValues.getFirstName());
		user.setLastName(userValues.getLastName());
		user.setGender(userValues.getGender());

		Integer countryCode = Integer.parseInt(userValues.getIsdCode());
		user.setCountry(userServiceHelper.findCountry(countryCode));
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

		Integer applicationPasswordVersion = App.getCurrentPasswordVersion();
		String newPasswordSalt = CredentialsUtil.getPasswordSalt();
		String newPasswordHash = CredentialsUtil.getPasswordHash(newPassword, newPasswordSalt, applicationPasswordVersion);

		// perform update
		user.setPasswordHash(newPasswordHash);
		user.setPasswordSalt(newPasswordSalt);
		user.setPasswordVersion(applicationPasswordVersion);
		repository.saveOrUpdate(user);
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

			user.setSecurityQuestion(question);
			user.setSecurityAnswer(answer);
			repository.saveOrUpdate(user);
		} else
			throw new InvalidCredentialsException("Password didn't match.");
	}

	@Override
	@Transactional
	public void updateUnsuccessfulTries(String email, Integer tries) {
		User user = repository.findByEmail(email);

		if (user != null) {
			user.setUnsuccessfulTries(tries);
			repository.saveOrUpdate(user);
		}
	}

	@Override
	public Map<String, String> getProfile(String email) {
		if (email != null && !email.isEmpty()) {
			User user = repository.findByEmail(email);
			return UserUtil.toProfile(user);
		}
		
		return null;
	}

}
