package com.ran488.dao.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Wrapper for multiple ticket objects b/c IIRC the MS libs don't like the the
 * JSON that gets generated from a raw list.
 * 
 * @author QZ0FY5
 *
 */
@ToString(callSuper = false, includeFieldNames = true)
public class Tickets {

	@Getter
	@Setter
	private List<Ticket> tickets;

}
