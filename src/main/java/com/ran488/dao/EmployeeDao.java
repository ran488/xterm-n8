package com.ran488.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ran488.dao.dto.Employee;
import com.ran488.dao.dto.Ticket;

public class EmployeeDao {

	private static final String MAIN_QUERY = "SELECT * FROM LEADSADMIN.EMPLOYEES";

	private static final String MAIN_QUERY_BY_USER = "SELECT * FROM LEADSADMIN.EMPLOYEES WHERE USERID = upper(?)";

	private static final Logger log = LoggerFactory.getLogger(EmployeeDao.class);

	private JdbcTemplate template;

	public EmployeeDao(JdbcTemplate template) {
		super();
		this.template = template;
	}

	public List<Employee> getAllEmployees() {
		log.info("Retrieving all employees from embedded DB....");
		List<Employee> rows = template.query(MAIN_QUERY, new EmployeeRowMapper());
		log.info(String.format("Found/Returning %s rows from employee table.", rows.size()));
		return rows;
	}

	public Employee getTicketsByUserID(final String userId) {
		log.info(String.format("Retrieving all employees for %s from embedded DB....", userId));
		Object[] params = new Object[] { userId };
		List<Employee> rows = template.query(MAIN_QUERY_BY_USER, params, new EmployeeRowMapper());
		log.info(String.format("Found/Returning %s rows from ticket table.", rows.size()));
		if (rows.size() > 0)
			return rows.get(0);
		else
			return null;
	}

	private static final class EmployeeRowMapper implements RowMapper<Employee> {
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee emp = new Employee();
			emp.setDepartment(rs.getString("dept"));
			emp.setName(rs.getString("name"));
			emp.setEmail(rs.getString("email"));
			emp.setPhone(rs.getString("phone"));
			emp.setUserId(rs.getString("userid"));
			emp.setLocation(rs.getString("location")); 
			return emp;
		}
	}

}
