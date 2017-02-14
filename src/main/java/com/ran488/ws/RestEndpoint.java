/**
 * 
 */
package com.ran488.ws;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ran488.dao.UserDao;
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
	private final UserDao dao;

	/**
	 * Create an instance of the REST endpoint handler, injecting a CRM lead
	 * gateway instance.
	 * 
	 * @param crmLeadGateway
	 */
	public RestEndpoint(final CrmLeadGateway<String> crmLeadGateway, final UserDao dao) {
		this.crmLeadGateway = crmLeadGateway;
		this.dao = dao;
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

	@RequestMapping("/api/loglist")
	public List<com.ran488.dao.dto.User> getLogEntries(@RequestParam(name = "bodid", required=false) String bodid, 
			@RequestParam(name = "first", required=false) String firstName, 
			@RequestParam(name = "last", required=false) String lastName, 
			@RequestParam(name = "country", required=false) String countryCode) {
		log.info(String.format("Retrieving the list of log entries BOD ID [%s] First [%s] Last [%s] Country [%s]...",bodid, firstName, lastName, countryCode));
		if (firstName != null && !"".equals(firstName))
			return dao.getUsers(firstName);
		else
			return dao.getUsers();
	}

}
