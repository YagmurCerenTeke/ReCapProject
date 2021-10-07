package com.etiya.ReCapProject.entities.requests.individualCustomerRequests;

import javax.validation.constraints.Size;

import com.etiya.ReCapProject.entities.dto.ApplicationUserDto;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {

	@NotNull
	private int individualCustomerId;
	
	@NotNull
	private String firstName;

	@NotNull
	private String lastName;
	
	@NotNull	
	@Size(min=11, max=11)
	private String nationalIdentityNumber;
	
	@NotNull
	private ApplicationUserDto applicationUser;
}