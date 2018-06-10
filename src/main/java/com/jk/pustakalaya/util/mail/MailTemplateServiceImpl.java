package com.jk.pustakalaya.util.mail;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jk.pustakalaya.util.MailConsts;

@Service
public class MailTemplateServiceImpl implements MailTemplateService {
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private MailService mailService;

	@Autowired
	private AbstractResourceBasedMessageSource bundle;

	private static final String TEMPLATE_PREFIX = "html/";

	@Override
	public boolean sendMail(String templateName, String recipients, String subjectKey, Map<String, Object> paramMap, Locale locale) {
		final Context context = new Context(locale);
		Object[] subjectParams = null;

		if (paramMap != null) {
			setParamsToContext(context, paramMap);
			subjectParams = (Object[]) paramMap.get(MailConsts.SUBJECT_PARAMETERS);
		}

		final String subject = bundle.getMessage(subjectKey, subjectParams, locale);
		final StringWriter writer = new StringWriter();
		String template = TEMPLATE_PREFIX + templateName;

		templateEngine.process(template, context, writer);

		final String msgBody = writer.toString();

		return mailService.sendMail(recipients, subject, msgBody);
	}

	private void setParamsToContext(Context ctx, Map<String, Object> map) {
		if (ctx == null || map == null) return;

		for (Map.Entry<String, Object> entry: map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			ctx.setVariable(key, value);
		}
	}
}
