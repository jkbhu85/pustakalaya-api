package com.jk.ptk.util.mail;

import java.util.Locale;
import java.util.Map;

public interface MailTemplateService {
	/**
	 * Returns {@code true} if mail was sent successfully, {@code false} otherwise.
	 *
	 * @param templateName
	 *            name of the template resource
	 * @param recipients
	 *            comma separated list of recipients
	 * @param subjectKey
	 *            property name for email subject
	 * @param params
	 *            map of parameters to be used in template
	 * @param locale
	 *            the specified locale
	 * @return {@code true} if mail was sent successfully, {@code false} otherwise
	 */
	public boolean sendMail(String templateName, String recipients, String subjectKey, Map<String, Object> params,
			Locale locale);
}
