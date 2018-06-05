package com.jk.pustakalaya.util.mail;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleMailTest {
	private static MailService mailer;

	@BeforeClass
	public static void beforeTest() throws Exception {
		mailer = new MailServiceImpl();
	}

	@Test
	public void sendMail() {
		boolean sent = mailer.sendMail("jk.bhu85@gmail.com", "Test Subject", "Hi Jitendra\n\nThis is some test text.\n\nRegards\nPustakalaya\n" + new Date());

		assertTrue(sent);
	}

	//@Test
	public void sendMailWithAttachments() {
		boolean sent = mailer.sendMail("jk.bhu85@gmail.com", "Test Subject", "Hi Jitendra\n\nThis is some test text.\n\nRegards\nPustakalaya\n" + new Date(), null);

		assertTrue(sent);
	}
}
