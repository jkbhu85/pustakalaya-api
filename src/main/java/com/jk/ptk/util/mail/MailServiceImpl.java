package com.jk.ptk.util.mail;

import java.io.BufferedInputStream;
import java.io.Closeable;
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
import org.springframework.stereotype.Service;

import com.jk.ptk.app.AppProps;
import com.jk.ptk.util.AutoClose;
import com.jk.ptk.util.CryptUtils;
import com.jk.ptk.util.FileUtils;

/**
 * A basic implementation of {@link MailService} interface.
 *
 * @author Jitendra
 *
 */
@Service("MailServiceImpl")
public class MailServiceImpl implements MailService, Closeable {
	private static Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
	private Session session;
	private Transport transport;
	private String zipFilePath;

	public MailServiceImpl() throws Exception {
		this.init();
	}

	private void init() throws Exception {
		String smtpAuth = AppProps.valueOf("mail.smtp.auth");
		Properties mailProps = getMailProps();

		if ("true".equalsIgnoreCase(smtpAuth)) {
			final String username = AppProps.valueOf("ptk.mail.username");
			final String iv = AppProps.valueOf("ptk.mail.encryption.iv");
			final String key = AppProps.valueOf("ptk.mail.encryption.key");
			final String base64Pwd = AppProps.valueOf("ptk.mail.password");
			final String password = CryptUtils.decryptFromBase64(base64Pwd, iv, key);

			Authenticator authenticator = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			};

			this.session = Session.getInstance(mailProps, authenticator);
		} else {
			this.session = Session.getInstance(mailProps);
		}

		//transport = session.getTransport();

		AutoClose.register(this);
		log.debug("Mailer was initialized successfully.");
	}

	private Properties getMailProps() throws Exception {
		String fileName = AppProps.valueOf("ptk.mail.properties.file");
		File file = new File(AppProps.CONFIG_DIR_PATH + File.separator + fileName);

		InputStream in = new BufferedInputStream(new FileInputStream(file));
		Properties props = new Properties();
		props.load(in);

		in.close();
		return props;
	}

	private void addAttachments(Multipart multipart, List<File> attachments) throws Exception {
		if (attachments == null || attachments.isEmpty())
			return;

		BodyPart filePart = new MimeBodyPart();
		File file;

		if (attachments.size() == 1) {
			file = attachments.get(0);
		} else {
			file = FileUtils.zipFiles(attachments);
			log.debug("Attachments were zipped with zip file name: {}", file.getName());
		}

		DataSource source = new FileDataSource(file);

		filePart.setDataHandler(new DataHandler(source));
		filePart.setFileName(file.getName());
		multipart.addBodyPart(filePart);
	}

	private void cleanUp() {
		if (zipFilePath != null) {
			boolean status = FileUtils.deleteFile(zipFilePath);

			if (status) {
				log.debug("Zip file was deleted. Path: {}", zipFilePath);
			} else {
				log.error("Zip file could not be deleted. Path: {}", zipFilePath);
			}
		}
	}

	@Override
	public boolean sendMail(String recipients, String subject, String message, List<File> attachments) {
		MimeMessage msg = new MimeMessage(session);

		try {
			msg.addHeader("Content-Type", "text/html; charset=UTF-8");
			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));

			Multipart multipart = new MimeMultipart();

			// add text
			BodyPart textPart = new MimeBodyPart();
			textPart.setText(message);
			textPart.setContent(message, "text/html;charset=UTF-8");
			multipart.addBodyPart(textPart);

			// add file attachments
			addAttachments(multipart, attachments);
			msg.setContent(multipart);

			log.debug("Mail prepared, about to send to: {}", recipients);

			/*
			log.debug("Connecting transport...");
			transport = session.getTransport();
			transport.connect();

			log.debug("Transport connected");

			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();*/

			Transport.send(msg);

			log.debug("Mail was sent successfully to {}", recipients);
			return true;
		} catch (Exception e) {
			log.error("Exception in sending mail to: {}\nException: {}", recipients, e);
		} finally {
			cleanUp();
		}

		return false;
	}

	@Override
	public boolean sendMail(String recipients, String subject, String message) {
		return sendMail(recipients, subject, message, null);
	}

	@Override
	public void close() {
		if (transport != null) {
			try {
				transport.close();
			} catch (Exception e) {
				log.error("Error while closing mail transport.{}", e);
			}
		}
	}

}
