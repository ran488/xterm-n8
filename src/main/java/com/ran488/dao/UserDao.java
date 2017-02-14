package com.ran488.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ran488.dao.dto.User;

public class UserDao {

	private static final String MAIN_QUERY = "SELECT * FROM LEADSADMIN.USERS";

	private static final String MAIN_QUERY_BY_NAME = "SELECT * FROM LEADSADMIN.USERS WHERE NAME like '%'||?||'%'";

	private static final Logger log = LoggerFactory.getLogger(UserDao.class);

	private JdbcTemplate template;

	public UserDao(JdbcTemplate template) {
		super();
		this.template = template;
	}

	/**
	 * Return parent and children as one result set
	 * 
	 * @return
	 */
	public List<User> getUsers() {
		log.info("Retrieving users from embedded table....");
		List<User> rows = template.query(MAIN_QUERY, new UserRowMapper());
		log.info(String.format("Found/Returning %s rows from XML Log table.", rows.size()));
		return rows;
	}

	public List<User> getUsers(final String name) {
		log.info("Retrieving users from embedded table...." + name);
		Object[] params = new Object[] {name}; 
		List<User> rows = template.query(MAIN_QUERY_BY_NAME, params, new UserRowMapper());
		log.info(String.format("Found/Returning %s rows from XML Log table.", rows.size()));
		return rows;
	}
	
	
	private static final class UserRowMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new User(rs.getString("name"), rs.getString("notes"));
		}
	}
}
