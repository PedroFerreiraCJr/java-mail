package br.com.dotofcodex.mail.service;

import static java.util.Objects.isNull;

import java.util.Date;
import java.util.Properties;

import br.com.dotofcodex.mail.model.MailInfo;
import jakarta.mail.Authenticator;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class SimpleMessage {

	public static final String CHARSET_UTF8 = "charset=utf-8";

	private final Properties file;
	private Session session;
	private MessagingException error;

	public SimpleMessage(Properties file) {
		super();
		this.file = file;
	}

	public void authenticate(final String username, final String password) {
		if (isNull(username) || isNull(password)) {
			throw new RuntimeException("Username and Password must not be null");
		}

		session = getSession(username, password);
	}

	public void send(MailInfo info) {
		if (isNull(session)) {
			throw new RuntimeException("Authentication is needed. Please, call authenticate first.");
		}

		final MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(info.getFrom()));
			message.setSubject(info.getSubject());
			message.setRecipients(RecipientType.TO, InternetAddress.parse(info.getTo()));
			message.setText(info.getMessage(), CHARSET_UTF8);
			message.setSentDate(new Date());

			Transport.send(message);
		} catch (MessagingException e) {
			error = e;
			e.printStackTrace();
		}
	}

	private Session getSession(final String username, final String password) {
		return Session.getInstance(this.file, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	public MessagingException getError() {
		return error;
	}
}
