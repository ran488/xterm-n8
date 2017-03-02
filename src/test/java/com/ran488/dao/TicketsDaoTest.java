package com.ran488.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

	@Test
	public void testGetTicketsByUserId_NoMatchesShouldReturnEmptyList() {
		List<Ticket> allTix = dao.getTicketsByUserID("butt");
		assertEquals(0, allTix.size());
	}

	@Test
	public void testGetTicketsByUserId_MatchShouldReturnList() {
		List<Ticket> allTix = dao.getTicketsByUserID("ran488");
		assertTrue(allTix.size() > 0);
		for (Ticket t : allTix) {
			log.info(t.toString());
		}
	}

	@Test
	public void testGetTicketsByTicketId_NoMatchesShouldReturnEmptyList() {
		List<Ticket> allTix = dao.getTicketsById(990099L);
		assertEquals(0, allTix.size());
	}

	@Test
	public void testGetTicketsByTicketId_MatchShouldListWithOneItem() {
		List<Ticket> allTix = dao.getTicketsByUserID("ran488");
		Long id = allTix.get(allTix.size()-1).getId();

		List<Ticket> oneTix = dao.getTicketsById(id);
		assertEquals(1, oneTix.size());
		assertEquals(id, oneTix.get(0).getId());
	}

	@Test
	public void testInsert() {
		// setup
		String uuid = UUID.randomUUID().toString();
		String description = "some description " + uuid;
		Ticket tik = new Ticket(description , "NEW", uuid);
		tik.setSystem("ITSRC");
		// method under test
		dao.insert(tik);
		
		// verify
		List<Ticket> allTix = dao.getTicketsByUserID(uuid);
		assertEquals(1, allTix.size());
		for (Ticket t : allTix) {
			log.info(t.toString());
		}
	}

}
