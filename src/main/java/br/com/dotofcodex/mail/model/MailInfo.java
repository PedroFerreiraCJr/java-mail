package br.com.dotofcodex.mail.model;

public class MailInfo {
	private final String subject;
	private final String from;
	private final String to;
	private final String message;

	public MailInfo(String subject, String from, String to, String message) {
		super();
		this.subject = subject;
		this.from = from;
		this.to = to;
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getMessage() {
		return message;
	}
}
