package com.etiya.ReCapProject.entities.requests.corporateCustomerRequests;

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
public class CreateCorporateCustomerRequest {

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String companyName;

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	@Size(min=10, max=10)
	private String taxNumber;

	@NotNull
	private int userId;
}