package com.jk.pustakalaya.util;

/**
 * Parameter names, template names and property names used to send mails.
 * 
 * @author Jitendra
 *
 */
public final class MailConsts {
	// prevent instantiation
	private MailConsts() {
	}

	/**
	 * An parameters must be associated as an array.
	 */
	public static final String SUBJECT_PARAMETERS = "subjectParameters";

	public static final String PARAM_COMPLETE_REGISTRATION_LINK = "registerationLink";
	public static final String PARAM_COMPLETE_REGISTRATION_EXPIRE = "registrationExpires";
	public static final String PARAM_COMPLETE_REGISRATION_USER_NAME = "userName";

	public static final String PROP_SUBJECT_COMPLETE_REGISTRAION = "registration.subject";

	public static final String TEMPLATE_COMPLETE_REGISTRAION = "completeRegistration";
}
