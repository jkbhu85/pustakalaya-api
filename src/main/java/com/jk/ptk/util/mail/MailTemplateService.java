package com.jk.ptk.util.mail;

/**
 * Implementation of this class sends a mail using the provided information.
 * 
 * @author Jitendra
 *
 */
public interface MailTemplateService {
	/**
	 * Sends mail using the values specified in the model.
	 * 
	 * @param model
	 *              the specified model.
	 */
	public void sendMail(MailModel model);
}
