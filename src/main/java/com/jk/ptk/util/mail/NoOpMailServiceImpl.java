package com.jk.ptk.util.mail;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * A no op implementation of {@link MailService}. It just prints all the
 * arguments to console.
 *
 * @author Jitendra
 *
 */
@Service("NoOpMailServiceImpl")
public class NoOpMailServiceImpl implements MailService {

	@Override
	public boolean sendMail(String recipients, String subject, String message) {
		return sendMail(recipients, subject, message, null);
	}

	@Override
	public boolean sendMail(String recipients, String subject, String message, List<File> attachments) {
		System.out.println("----------------------------------------------");
		System.out.println("             * Mail contents *");
		System.out.println("----------------------------------------------");
		System.out.println("Recipients: " + recipients);
		System.out.println("Subject: " + subject);
		System.out.println("Message: \n" + message);
		if (attachments != null && attachments.size() > 0) {
			System.out.println("Attachments: ");
			for (File f : attachments) {
				System.out.println(f.getName());
			}
		}
		System.out.println("----------------------------------------------");
		System.out.println();

		return true;
	}

}
