package com.jk.pustakalaya.util.mail;

import java.sql.Date;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jk.pustakalaya.util.DateUtils;

@Component
public class MailTemplateServiceImpl implements MailTemplateService {
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private MailService mailService;

	@Autowired
	ResourceBundleMessageSource bundle;

	@Override
	public boolean sendMail(String templateName, String recipients, String subjectKey, Map<String, Object> params, Locale locale) {
		final Context context = new Context(locale);

		setParamsToContext(context, params);

		final String msgBody = templateEngine.process(templateName, context);
		final String subject = bundle.getMessage(subjectKey, null, locale);

		return mailService.sendMail(recipients, subject, msgBody);
	}

	private void setParamsToContext(Context ctx, Map<String, Object> map) {
		if (ctx == null || map == null) return;

		for (Map.Entry<String, Object> entry: map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (value == null) {
				ctx.setVariable(key, value);
				continue; // no need to go further
			}

			// transform date into string
			if (Date.class.isAssignableFrom(value.getClass())) {
				value = DateUtils.dateString((Date) value);
			}

			// transform array into list
			if (value.getClass().isArray()) {
				value = Arrays.asList(value);
			}

			ctx.setVariable(key, value);
		}
	}
}
