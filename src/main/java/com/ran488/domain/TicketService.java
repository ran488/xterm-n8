package com.ran488.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.ran488.ws.RestEndpoint;

/**
 * User-submitted ticket representing a request sent to another group for
 * fulfillment, e.g. an issue to be resolved, a request for access,
 * installation, etc.
 * 
 */
public class TicketService {

	private static final Logger log = LoggerFactory.getLogger(TicketService.class);

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
		log.info("Sending email to: " + emailAddress);
		log.info("Content: " + content);
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setTo(emailAddress);
		msg.setText(content);
		try {
			this.mailSender.send(msg);
		} catch (MailException e) {
			log.error("email send failed", e);
			throw e;
		} catch (Exception e) {
			log.error("email send failed", e);
			throw e;
		}
		log.info("Sent");
	}

}
