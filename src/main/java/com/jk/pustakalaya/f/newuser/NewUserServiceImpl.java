package com.jk.pustakalaya.f.newuser;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.pustakalaya.config.App;
import com.jk.pustakalaya.model.UserRole;
import com.jk.pustakalaya.util.MailConsts;
import com.jk.pustakalaya.util.UuidUtils;
import com.jk.pustakalaya.util.mail.MailTemplateService;

/**
 * Uses ORM framework to store and retrieve the object. An mail is sent to
 * user's email address with a link to complete registration process.
 * 
 * @author Jitendra
 *
 */
@Service
public class NewUserServiceImpl implements NewUserService {
	@Autowired
	private NewUserRepository repo;
	
	@Autowired
	private MailTemplateService mailService;

	@Override
	public void addNewUser(NewUser newUser) {
		newUser.setId(UuidUtils.generate());
		newUser.getRole().setId(UserRole.DEFAULT_USER_ROLE);
		repo.saveNewUser(newUser);
		
		sendMail(newUser);
	}
	
	private void sendMail(NewUser newUser) {
		String registrationUri = App.getUrl("/api/newUser/" + newUser.getId());
		Map<String, Object> params = new HashMap<>();
		
		params.put(MailConsts.PARAM_COMPLETE_REGISRATION_USER_NAME, newUser.getFirstName());
		params.put(MailConsts.PARAM_COMPLETE_REGISTRATION_EXPIRE, App.registrationLinkExpireDuration());
		params.put(MailConsts.PARAM_COMPLETE_REGISTRATION_LINK, registrationUri);
		
		mailService.sendMail(
				MailConsts.TEMPLATE_COMPLETE_REGISTRAION,
				newUser.getEmail(),
				MailConsts.PROP_SUBJECT_COMPLETE_REGISTRAION,
				params,
				new Locale(newUser.getLocaleStr())
				);
	}

	@Override
	public NewUser getNewUser(String id) {
		return repo.findNewUser(id);
	}

}
