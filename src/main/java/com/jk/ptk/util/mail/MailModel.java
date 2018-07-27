package com.jk.ptk.util.mail;

import java.util.Locale;
import java.util.Map;

/**
 * Base class for all objects sent to mail template engine.
 * 
 * @author Jitendra
 *
 */
public class MailModel {
	private String recipient;
	private String recipientName;
	private Locale locale;
	private String subjectPropName;
	private String templateName;
	private String[] subjectParameters;
	private Map<String, Object> paramMap; 

	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}

	/**
	 * @param recipient
	 *                  the recipient to set
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	/**
	 * @return the recipientName
	 */
	public String getRecipientName() {
		return recipientName;
	}

	/**
	 * @param recipientName
	 *                      the recipientName to set
	 */
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *               the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @return the subjectPropName
	 */
	public String getSubjectPropName() {
		return subjectPropName;
	}

	/**
	 * @param subjectPropName
	 *                        the subjectPropName to set
	 */
	public void setSubjectPropName(String subjectPropName) {
		this.subjectPropName = subjectPropName;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName
	 *                     the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * @return the subjectParameters
	 */
	public String[] getSubjectParameters() {
		return subjectParameters;
	}

	/**
	 * @param subjectParameters
	 *                          the subjectParameters to set
	 */
	public void setSubjectParameters(String[] subjectParameters) {
		this.subjectParameters = subjectParameters;
	}

	/**
	 * @return the paramMap
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	/**
	 * @param paramMap the paramMap to set
	 */
	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
	

}
