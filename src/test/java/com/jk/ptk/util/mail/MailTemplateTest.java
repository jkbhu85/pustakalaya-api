package com.jk.ptk.util.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jk.ptk.util.MailConsts;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTemplateTest {

	@Autowired
	private MailTemplateService mailTemplateService;

	@Test
	public void createAccount() {
		String recipients = "def@mail.com";
		String subjectKey = MailConsts.SUBJECT_COMPLETE_REGISTRATION;
		Map<String, Object> paramMap = new HashMap<>();
		Locale locale = Locale.US;

		paramMap.put(MailConsts.PARAM_COMPLETE_REGISTRATION_LINK, "https://www.abc.com/completeRegistration");

		MailModel m = new MailModel();
		m.setRecipient(recipients);
		m.setRecipientName("Uttam");
		m.setSubjectPropName(subjectKey);
		m.setLocale(locale);
		m.setParamMap(paramMap);

		mailTemplateService.sendMail(m);
	}
}
