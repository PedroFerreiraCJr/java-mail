package br.com.dotofcodex.mail.service;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;

public class MailConnection {

	public static final String SMTP_TRANSPORT = "smtp";

	private final Properties file;
	private MessagingException error;

	public MailConnection(Properties file) {
		super();
		this.file = file;
	}

	public boolean connect(final String username, final String password) {
		final Session session = getSession(username, password);

		try (final Transport transport = session.getTransport(SMTP_TRANSPORT)) {
			transport.connect();
			transport.close();
			return true;
		} catch (final MessagingException e) {
			error = e;
		}

		return false;
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
