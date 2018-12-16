package com.jk.ptk.util.mail;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jk.ptk.app.App;
import com.jk.ptk.f.newuser.NewUser;
import com.jk.ptk.f.user.User;
import com.jk.ptk.mail.BookAssignedMail;
import com.jk.ptk.util.LocaleUtil;

@Component
class MailHelperImpl implements MailHelper {
	private static Logger log = LogManager.getLogger(MailHelperImpl.class);
	private MailTemplateService mailTemplateService;

	public MailHelperImpl(MailTemplateService mailTemplateService) {
		this.mailTemplateService = mailTemplateService;
	}

	@Override
	public void sendMailOnBookAssigned(BookAssignedMail obj) {
		MailModel model = new MailModel();
		Map<String, Object> paramMap = new HashMap<>();
		model.setParamMap(paramMap);

		try {
			model.setLocale(LocaleUtil.from(obj.getLocale()));
			model.setTo(obj.getEmail());
			model.setRecipientName(obj.getFirstName());

			model.setSubjectPropName(MailConsts.SUBJECT_BOOK_ASSIGNED);
			model.setTemplateName(MailConsts.TEMPLATE_BOOK_ASSIGNED);

			paramMap.put(MailConsts.TPARAM_BOOK_ASSIGNED_MAIL, obj);

			this.mailTemplateService.sendMail(model);
		} catch (Exception e) {
			log.error("While sending mail on book assigned: {}", obj.getEmail());
		}
	}

	@Override
	public void sendMailOnAccountCreation(NewUser newUser) {
		MailModel model = new MailModel();
		Map<String, Object> paramMap = new HashMap<>();
		model.setParamMap(paramMap);

		try {
			model.setLocale(LocaleUtil.from(newUser.getLocaleStr()));
			model.setTo(newUser.getEmail());
			model.setRecipientName(newUser.getFirstName());
			
			model.setSubjectPropName(MailConsts.SUBJECT_COMPLETE_REGISTRATION);
			model.setTemplateName(MailConsts.TEMPLATE_COMPLETE_REGISTRATION);
			
			paramMap.put(MailConsts.PARAM_NAME_OF_USER, newUser.getFirstName());
			paramMap.put(MailConsts.TPARAM_COMPLETE_REGISTRATION_EXPIRE, Integer.toString(App.registrationLinkExpireDuration()));
			
			String registrationUri = App.getUrl("/user/register?id=" + newUser.getId());
			paramMap.put(MailConsts.TPARAM_COMPLETE_REGISTRATION_LINK, registrationUri);
			this.mailTemplateService.sendMail(model);
		} catch (Exception e) {
			log.error("While sending mail on account creation: {}", newUser.getEmail());
		}

	}

	@Override
	public void sendMailOnAccountCreationCompletion(User user) {
		MailModel model = new MailModel();
		Map<String, Object> paramMap = new HashMap<>();
		model.setParamMap(paramMap);

		try {
			model.setLocale(LocaleUtil.from(user.getLocaleValue()));
			model.setTo(user.getEmail());
			model.setRecipientName(user.getFirstName());
			
			model.setSubjectPropName(MailConsts.SUBJECT_ACCOUNT_CREATION_COMPLETE);
			model.setTemplateName(MailConsts.TEMPLATE_ACCOUNT_CREATION_COMPLETE);

			paramMap.put(MailConsts.PARAM_NAME_OF_USER, user.getFirstName());
			paramMap.put(MailConsts.PARAM_EMAIL_OF_USER, user.getEmail());

			String loginUrl = App.getUrl("/login");
			paramMap.put(MailConsts.PARAM_LOGIN_PAGE, loginUrl);
			mailTemplateService.sendMail(model);
		} catch (Exception e) {
			log.error("While sending mail account creation completion: ", user.getEmail());
		}
	}

}
