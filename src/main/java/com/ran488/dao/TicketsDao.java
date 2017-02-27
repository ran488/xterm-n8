package com.ran488.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ran488.dao.dto.Ticket;

public class TicketsDao {

	private static final String MAIN_QUERY = "SELECT * FROM LEADSADMIN.TICKETS";

	private static final String MAIN_QUERY_BY_USER = "SELECT * FROM LEADSADMIN.TICKETS WHERE USERID = ?";

	private static final String MAIN_QUERY_BY_TICKET_ID = "SELECT * FROM LEADSADMIN.TICKETS WHERE ID = ?";

	private static final Logger log = LoggerFactory.getLogger(TicketsDao.class);

	private JdbcTemplate template;

	public TicketsDao(JdbcTemplate template) {
		super();
		this.template = template;
	}

	public List<Ticket> getAllTickets() {
		log.info("Retrieving all tickets from embedded DB....");
		List<Ticket> rows = template.query(MAIN_QUERY, new TicketRowMapper());
		log.info(String.format("Found/Returning %s rows from ticket table.", rows.size()));
		return rows;
	}
	
	
	private static final class TicketRowMapper implements RowMapper<Ticket> {
		public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
			Ticket ticket = new Ticket();
			ticket.setId(rs.getLong("ID"));
			ticket.setUserId(rs.getString("USERID"));
			ticket.setDescription(rs.getString("DESCRIPTION"));
			ticket.setStatus(rs.getString("STATUS"));
			ticket.setCreated(rs.getTimestamp("CREATED"));
			ticket.setUpdated(rs.getTimestamp("UPDATED"));
			return ticket;
		}
	}

}
