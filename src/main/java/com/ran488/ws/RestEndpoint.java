/**
 * 
 */
package com.ran488.ws;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ran488.dao.EmployeeDao;
import com.ran488.dao.TicketsDao;
import com.ran488.dao.UserDao;
import com.ran488.dao.dto.Employee;
import com.ran488.dao.dto.Ticket;
import com.ran488.dao.dto.Tickets;
import com.ran488.domain.TicketService;
import com.ran488.exception.CrmLeadSubmissionException;
import com.ran488.integration.CrmLeadGateway;

/**
 * @author QZ0FY5
 *
 */
@RestController
public class RestEndpoint {

	private static final Logger log = LoggerFactory.getLogger(RestEndpoint.class);

	@Autowired
	private final CrmLeadGateway<String> crmLeadGateway;

	@Autowired
	private final TicketsDao dao;

	@Autowired
	final EmployeeDao empDao;

	@Autowired
	private final TicketService ticketSvc;

	/**
	 * Create an instance of the REST endpoint handler, injecting a CRM lead
	 * gateway instance.
	 * 
	 * @param crmLeadGateway
	 */
	public RestEndpoint(final CrmLeadGateway<String> crmLeadGateway, final TicketsDao dao, TicketService ticketSvc,
			EmployeeDao empDao) {
		this.crmLeadGateway = crmLeadGateway;
		this.ticketSvc = ticketSvc;
		this.dao = dao;
		this.empDao = empDao;
	}

	/**
	 * Sample endpoint that just takes a parameter and spits back a response.
	 * This utilizes a Spring Integration flow.
	 * 
	 * @return Response containing status and message.
	 */
	@RequestMapping("/leads/submit")
	public Response submitLeads(@RequestParam(name = "name", defaultValue = "World") String name) {
		log.info("REST Endpoint received a request.");
		Response response;
		try {
			String reply = crmLeadGateway.submit(String.format("Hello %s", name));
			response = new Response("Success", reply);
		} catch (CrmLeadSubmissionException e) {
			String errorMsg = "Your lead has not been submitted due to a system error. Please see the log for more details.";
			log.error(errorMsg, e);
			return new Response("Fail", errorMsg);
		}
		return response;
	}

	/**
	 * Class to hold the web service response object. Spring-Web/Jackson will
	 * serialize this object to a JSON payload for us. e.g.
	 * <code>{"status":"Success","statusMessage":"Your lead has been been submitted successfully."}</code>
	 */
	static public class Response {
		// This object will never live past sending response back, so no need
		// for it to live on its own or deal with all the extra getter/setter
		// crap. Just make sure nothing can change the state (final) once it is
		// created.
		public final String status;
		public final String statusMessage;

		public Response(final String status, final String statusMessage) {
			this.status = status;
			this.statusMessage = statusMessage;
		}

	}

	/**
	 * For the UI, taken from template. don't change too much here to avoid
	 * rework at last minute of display and search.
	 * 
	 * @param bodid
	 * @param firstName
	 * @param lastName
	 * @param countryCode
	 * @return
	 */
	@RequestMapping("/api/loglist")
	public List<Ticket> getTicketList(@RequestParam(name = "bodid", required = false) String bodid,
			@RequestParam(name = "first", required = false) String firstName,
			@RequestParam(name = "last", required = false) String lastName,
			@RequestParam(name = "country", required = false) String countryCode) {
		log.info(String.format("Retrieving the list of log entries BOD ID [%s] First [%s] Last [%s] Country [%s]...",
				bodid, firstName, lastName, countryCode));
		if (firstName != null && !"".equals(firstName))
			return dao.getTicketsByUserID(firstName);
		else
			return dao.getAllTickets();
	}

	/**
	 * to request from bot, can be whatever we want.
	 * 
	 * @param firstName
	 * @return
	 */
	@RequestMapping("/api/tickets/list")
	public Tickets getTickets(@RequestParam(name = "user", required = false) String userName) {
		log.info(String.format("Retrieving the list of tickets for user" + userName));
		Tickets tickets = new Tickets();
		if (userName != null && !"".equals(userName))
			tickets.setTickets(dao.getTicketsByUserID(userName));
		else
			tickets.setTickets(dao.getAllTickets());

		return tickets;
	}

	@RequestMapping("/api/tickets/submit")
	public void submitTickets(@RequestBody(required = true) Tickets tickets) {
		log.info(String.format("submitting tickets" + tickets.toString()));
		StringBuffer content = new StringBuffer();
		content.append("IT Buddy has submitted ").append(tickets.getTickets().size());
		content.append(" tickets on your behalf.\n");
		String emailAddress = tickets.getTickets().get(0).getEmail();
		String user = tickets.getTickets().get(0).getUserId();

		for (Ticket t : tickets.getTickets()) {
			log.info("Inserting " + t.toString());
			dao.insert(t);
			content.append(
					String.format("%nSubmitted to [%s] - %s (%s)%n", t.getSystem(), t.getDescription(), t.getStatus()));

		}
		content.append("\n\nCheck http://peaceful-fortress-82166.herokuapp.com?first=");
		content.append(user).append(" to check ticket status.");
		this.ticketSvc.notifyOriginator(emailAddress, content.toString());

	}

	/**
	 * to request from bot, can be whatever we want.
	 * 
	 * @param firstName
	 * @return
	 */
	@RequestMapping("/api/employee")
	public Employee getEmployee(@RequestParam(name = "user", required = false) String userName) {
		log.info(String.format("Retrieving the employee record for user" + userName));
		if (userName != null && !"".equals(userName))
			return empDao.getTicketsByUserID(userName);
		else
			return new Employee();
	}

}
