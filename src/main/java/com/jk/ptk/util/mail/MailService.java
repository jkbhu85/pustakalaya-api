package com.jk.ptk.util.mail;

import java.io.File;
import java.util.List;

/**
 * A class should implement this interface that provides mail sending
 * functionality.
 *
 * @author Jitendra
 *
 */

public interface MailService {

	/**
	 * Sends email to the specified {@code recipients} with specified
	 * {@code subject} and {@code message}. Returns {@code true} if mail was sent
	 * successfully {@code false} otherwise.
	 *
	 * @param recipients
	 *            comma separated email addresses of the recipients
	 * @param subject
	 *            subject of the email
	 * @param message
	 *            body of the email
	 *
	 * @return {@code true} if mail sent successfully, {@code false} otherwise
	 */
	boolean sendMail(String recipients, String subject, String message);

	/**
	 * Sends email to the specified {@code recipient} with specified
	 * {@code subject}, {@code message} and {@code attachments}. Returns
	 * {@code true} if mail was sent successfully {@code false} otherwise.
	 *
	 * If {@code attachments} contains more than one file then the files will be
	 * zipped and the resulting zip file will be attached.
	 *
	 * @param recipients
	 *            comma separated email addresses of the recipients
	 * @param subject
	 *            subject of the email
	 * @param message
	 *            body of the email
	 * @param attachments list of files to be attached
	 * @return {@code true} if mail sent successfully, {@code false} otherwise
	 */
	boolean sendMail(String recipients, String subject, String message, List<File> attachments);
}
