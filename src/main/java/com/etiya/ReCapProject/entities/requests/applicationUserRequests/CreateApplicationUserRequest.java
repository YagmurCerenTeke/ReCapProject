package com.etiya.ReCapProject.entities.requests.applicationUserRequests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.etiya.ReCapProject.business.constants.Messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateApplicationUserRequest {

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	@Email
	private String email;

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String password;
}