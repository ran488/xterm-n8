package com.ran488.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public class GenericSvcActivator<T> {

	private static final Logger log = LoggerFactory.getLogger(GenericSvcActivator.class);

	@SuppressWarnings("unchecked")
	public Message<T> onMessage(final Message<T> incoming) {
		log.info(String.format("Service Activator recieved a message. [%s]", incoming.toString()));
		Message<T> outgoing = (Message<T>) MessageBuilder.fromMessage(incoming).withPayload(incoming.getPayload().toString().toUpperCase()).build();
		return outgoing;
	}
}
