package com.ran488.dao.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = false, includeFieldNames = true)
public class Employee {

	@Getter @Setter private String name;
	@Getter @Setter private String userId;
	@Getter @Setter private String email;
	@Getter @Setter private String phone;
	@Getter @Setter private String department;
	@Getter @Setter private String location;
	
}
