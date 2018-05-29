package com.jk.pustakalaya.util.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jk.pustakalaya.config.App;
import com.jk.pustakalaya.util.CryptUtil;
import com.jk.pustakalaya.util.FileUtil;

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
	private String zipFilePath;

	public SimpleMailer() throws Exception {
		this.init();
	}

	private void init() throws Exception {
		Properties mailProps = new Properties();
		String mailPropsFilePath = System.getenv(App.NAME_ENV_VAR_CONFIG) + File.separator + mailPropsFileName;
		InputStream in = new FileInputStream(mailPropsFilePath);
		mailProps.load(in);

		String smtpAuth = mailProps.getProperty("mail.smtp.auth");

		if ("true".equalsIgnoreCase(smtpAuth)) {
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

	private void addAttachments(Multipart multipart, List<File> attachments) throws Exception {
		if (attachments == null || attachments.isEmpty())
			return;

		BodyPart filePart = new MimeBodyPart();
		File file;

		if (attachments.size() == 1) {
			file = attachments.get(0);
		} else {
			zipFilePath = FileUtil.zipFiles(attachments);
			file = new File(zipFilePath);
		}

		DataSource source = new FileDataSource(file);

		filePart.setDataHandler(new DataHandler(source));
		filePart.setFileName(file.getName());
		multipart.addBodyPart(filePart);
	}

	private void cleanUp() {
		if (zipFilePath != null)
			FileUtil.deleteFile(zipFilePath);
	}

	@Override
	public boolean sendMail(String recipients, String subject, String message, List<File> attachments) {
		MimeMessage msg = new MimeMessage(session);

		try {
			msg.addHeader("Contentp-type", "text/html; charset=UTF-8");
			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));

			Multipart multipart = new MimeMultipart();

			// add text
			BodyPart textPart = new MimeBodyPart();
			textPart.setText(message);
			multipart.addBodyPart(textPart);

			// add file attachments
			addAttachments(multipart, attachments);
			msg.setContent(multipart);

			LOG.debug("Mail prepared, about to send to: {}", recipients);
			Transport.send(msg);
			LOG.info("Mail was sent successfully to {}.", recipients);
			return true;
		} catch (Exception e) {
			LOG.error("Exception in sending mail to: {}\nException: {}", recipients, e);
		} finally {
			cleanUp();
		}

		return false;
	}

	@Override
	public boolean sendMail(String recipients, String subject, String message) {
		return sendMail(recipients, subject, message, null);
	}

}
