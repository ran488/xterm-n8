/**
 * 
 */
package com.ran488.dao.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represent a ticket, issue, work order, etc.
 */
@ToString(callSuper = false, includeFieldNames = true)
public class Ticket {

	@Getter
	@Setter
	private Long id;
	@Getter
	@Setter
	private String description;
	@Getter
	@Setter
	private String status;
	@Getter
	@Setter
	private String userId;
	@Getter
	@Setter
	private java.sql.Timestamp created;
	@Getter
	@Setter
	private java.sql.Timestamp updated;

	public Ticket() {
		super();
	}

	public Ticket(String description, String status, String userId) {
		this();
		this.description = description;
		this.status = status;
		this.userId = userId;
	}

}
