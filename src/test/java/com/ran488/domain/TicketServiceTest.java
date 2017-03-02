package com.ran488.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ran488.ApplicationTests;

public class TicketServiceTest extends ApplicationTests {

	@Autowired
	private TicketService ticketService;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test @Ignore
	public void testNotifyOriginator() {
		ticketService.notifyOriginator("ran488@gmail.com", "Testing from JUnit test case");
	}

}
