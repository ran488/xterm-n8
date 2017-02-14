package com.ran488.dao.dto;

import lombok.*;

@ToString(callSuper = false, includeFieldNames = true)
public class User {

	@Getter
	private final String name;
	@Getter
	private final String notes;

	public User(String name, String notes) {
		super();
		this.name = name;
		this.notes = notes;
	}
}
