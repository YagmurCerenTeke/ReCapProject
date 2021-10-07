package com.etiya.ReCapProject.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualCustomerDto {

	private int individualCustomerId;
	
	private String firstName;

	private String lastName;
	
	private String identityNumber;
	
	private ApplicationUserDto applicationUser;
}
