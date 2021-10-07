package com.etiya.ReCapProject.entities.requests.corporateCustomerRequests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.entities.dto.ApplicationUserDto;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	
	@NotNull
	private int corporateCustomerId;

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String companyName;

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	@Size(min=10, max=10)
	private String taxNumber;

	@NotNull
	private ApplicationUserDto applicationUser;
}