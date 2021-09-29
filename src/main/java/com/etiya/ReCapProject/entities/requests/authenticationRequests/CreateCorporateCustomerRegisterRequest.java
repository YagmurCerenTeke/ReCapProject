package com.etiya.ReCapProject.entities.requests.authenticationRequests;

import javax.validation.constraints.Email;
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
public class CreateCorporateCustomerRegisterRequest {
	
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String companyName;
	
	@Size(min=10, max=10)
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String taxNumber;
	
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	@Email
	private String email;
	
	@Size(min=6, max=20)
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String password;
	
	@Size(min=6, max=20)
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String passwordConfirm;
}
