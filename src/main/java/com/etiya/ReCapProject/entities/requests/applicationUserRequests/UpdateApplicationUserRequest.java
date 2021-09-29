package com.etiya.ReCapProject.entities.requests.applicationUserRequests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.etiya.ReCapProject.business.constants.Messages;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApplicationUserRequest {
	
	@NotNull
	private int userId;

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	@Email
	private String email;

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String password;
	
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String passwordConfirm;
}