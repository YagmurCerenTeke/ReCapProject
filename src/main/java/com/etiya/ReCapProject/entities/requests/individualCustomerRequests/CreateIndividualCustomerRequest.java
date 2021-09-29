package com.etiya.ReCapProject.entities.requests.individualCustomerRequests;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.etiya.ReCapProject.business.constants.Messages;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String firstName;

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String lastName;
	
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	@Size(min=11, max=11)
	private String nationalIdentityNumber;
	
	@NotNull
	private int userId;
}