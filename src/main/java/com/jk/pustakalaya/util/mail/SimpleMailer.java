package com.jk.pustakalaya.util.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jk.pustakalaya.config.App;
import com.jk.pustakalaya.util.CryptUtil;

/**
 * A basic implementation of {@link Mailer} interface.
 *
 * @author Jitendra
 *
 */

@Component
public class SimpleMailer implements Mailer {
	private static Logger LOG = LoggerFactory.getLogger(SimpleMailer.class);

	@Value("mail.props.file")
	private String mailPropsFileName = "mail.properties";

	private Session session;

	public SimpleMailer() throws Exception {
		this.init();
	}

	public static void main(String[] args) throws Exception {
		SimpleMailer mailer = new SimpleMailer();

		mailer.sendMail("jk.bhu85@gmail.com", "Test Subject", "Hi Jitendra\n\nThis is some test text.\n\nRegards\nPustakalaya\n" + new Date());
	}

	private void init() throws Exception {
		Properties mailProps = new Properties();
		String mailPropsFilePath = System.getenv(App.NAME_ENV_VAR_CONFIG) + File.separator + mailPropsFileName;
		InputStream in = new FileInputStream(mailPropsFilePath);
		mailProps.load(in);

		String smtpAuth = mailProps.getProperty("mail.smtp.auth");

		if (smtpAuth != null && "true".equalsIgnoreCase(smtpAuth)) {
			final String username = mailProps.getProperty("mail.username");
			final String password = CryptUtil.decryptFromFile(mailProps.getProperty("mail.password.file.name"), false);

			Authenticator authenticator = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			};

			this.session = Session.getDefaultInstance(mailProps, authenticator);
		} else {
			this.session = Session.getDefaultInstance(mailProps);
		}

		LOG.debug("Mailer initialized successfully.");
	}

	@Override
	public boolean sendMail(String recipients, String subject, String message, List<File> attachments) {
		return false;
	}

	@Override
	public boolean sendMail(String recipients, String subject, String message) {
		MimeMessage mailMsg = new MimeMessage(session);

		try {
			mailMsg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
			mailMsg.setSubject(subject);
			mailMsg.setText(message);
			mailMsg.setSentDate(new Date());

			LOG.debug("Mail prepared, about to send to: {}", recipients);
			Transport.send(mailMsg);
			LOG.info("Mail was sent successfully to {}.", recipients);

			return true;
		} catch (Exception e) {
			LOG.error("Exception in sending mail to: {}\nexception: {}", recipients, e);
		}

		return false;
	}

}
