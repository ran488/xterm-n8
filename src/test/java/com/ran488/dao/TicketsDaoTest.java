package com.ran488.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ran488.ApplicationTests;
import com.ran488.dao.dto.Ticket;

public class TicketsDaoTest extends ApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(TicketsDaoTest.class);
	
	@Autowired
	TicketsDao dao;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAllTickets() {
		List<Ticket> allTix = dao.getAllTickets();
		assertTrue(allTix.size() > 0);
		for (Ticket t : allTix) {
			log.info(t.toString());
		}
	}

}
