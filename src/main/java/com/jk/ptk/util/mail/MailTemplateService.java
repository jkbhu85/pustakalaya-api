package com.jk.ptk.util.mail;

/**
 * Implementation of this class sends a mail using the provided information.
 * 
 * @author Jitendra
 */
public interface MailTemplateService {
	/**
	 * Sends mail using the values specified in the model.
	 * 
	 * @param model
	 *              the specified model.
	 * @throws Exception
	 *                   if an error occurs while sending mail.
	 */
	public void sendMail(MailModel model) throws Exception;
}
