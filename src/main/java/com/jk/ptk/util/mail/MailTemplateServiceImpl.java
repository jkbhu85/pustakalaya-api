package com.jk.ptk.util.mail;

import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
class MailTemplateServiceImpl implements MailTemplateService {
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	@Qualifier("MailServiceImpl")
	private MailService mailService;

	@Autowired
	private AbstractResourceBasedMessageSource bundle;

	private static final String TEMPLATE_PREFIX = "html/";

	@Override
	public void sendMail(MailModel model) {
		final Context context = new Context(model.getLocale());
		final String subject = bundle.getMessage(model.getSubjectPropName(), model.getSubjectParameters(), model.getLocale());
		final StringWriter writer = new StringWriter();
		final String template = TEMPLATE_PREFIX + model.getTemplateName();

		context.setVariables(model.getParamMap());
		templateEngine.process(template, context, writer);

		final String msgBody = writer.toString();

		mailService.sendMail(model.getRecipient(), subject, msgBody);
	}
}
