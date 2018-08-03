package com.jk.ptk;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class Test {

    public static void main(String args[]) throws Exception {

        Properties props = System.getProperties();
        props.put("mail.smtps.host", "smtp.mailgun.org");
        props.put("mail.smtps.auth", "true");

        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("postmaster@sandboxfa8c80651ffa4f7fb43723255f13985f.mailgun.org"));

        InternetAddress[] addrs = InternetAddress.parse("jitenk29@gmail.com", false);
        msg.setRecipients(Message.RecipientType.TO, addrs);

        msg.setSubject("Hello");
        msg.setText("Sending email");
        msg.setSentDate(new Date());

        SMTPTransport t =
            (SMTPTransport) session.getTransport("smtps");
        t.connect("smtp.mailgun.com", "postmaster@sandboxfa8c80651ffa4f7fb43723255f13985f.mailgun.org", "0a3341a6722d556c6478bfb8e7e5678b-a5d1a068-79eec708");
        t.sendMessage(msg, msg.getAllRecipients());

        System.out.println("Response: " + t.getLastServerResponse());

        t.close();
    }
}
