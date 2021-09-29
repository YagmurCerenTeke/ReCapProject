package com.etiya.ReCapProject.entities.requests.creditCardRequests;

import javax.validation.constraints.NotNull;

import com.etiya.ReCapProject.business.constants.Messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {

	@NotNull(message = Messages.NOTNULL)
	private String creditCardNumber;
	
	@NotNull(message = Messages.NOTNULL)
	private String expirationDate;
	
	@NotNull(message = Messages.NOTNULL)
	private String cvc;

	@NotNull
	private int userId;
}