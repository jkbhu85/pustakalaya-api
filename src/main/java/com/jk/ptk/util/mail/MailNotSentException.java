package com.jk.ptk.util.mail;

public class MailNotSentException extends RuntimeException {

	private static final long serialVersionUID = 4139834453089593933L;

	public MailNotSentException() {
		super("Mail was not sent.");
	}

}
