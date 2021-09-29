package com.etiya.ReCapProject.entities.requests.authenticationRequests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.etiya.ReCapProject.business.constants.Messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateLoginRequest {

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	@Email
	private String email;
	
	@Size(min=6, max=20)
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String password;
}
