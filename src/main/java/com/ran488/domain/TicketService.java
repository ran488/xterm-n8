package com.ran488.domain;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * User-submitted ticket representing a request sent to another group for
 * fulfillment, e.g. an issue to be resolved, a request for access,
 * installation, etc.
 * 
 */
public class TicketService {

	private final MailSender mailSender;
	private SimpleMailMessage templateMessage;

	public TicketService(final MailSender mailSender, final SimpleMailMessage templateMessage) {
		super();
		this.mailSender = mailSender;
		this.templateMessage = templateMessage;
	}
	
	/**
	 * Notify originator of the ticket/request with status of the ticket
	 * submission.
	 * 
	 */
	public void notifyOriginator(final String emailAddress, final String content) throws MailException {
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setTo(emailAddress);
		msg.setText(content);
		this.mailSender.send(msg);
	}

}
