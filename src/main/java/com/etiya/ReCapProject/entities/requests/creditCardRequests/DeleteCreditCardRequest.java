package com.etiya.ReCapProject.entities.requests.creditCardRequests;

import javax.validation.constraints.NotNull;

import com.etiya.ReCapProject.business.constants.Messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCreditCardRequest {

	@NotNull(message = Messages.NOTNULL)
	private int creditCardId;
	
	@NotNull(message = Messages.NOTNULL)
	private int userId;
	
	@NotNull(message = Messages.NOTNULL)
	private String creditCardNumber;

	@NotNull(message = Messages.NOTNULL)
	private String expirationDate;

	@NotNull(message = Messages.NOTNULL)
	private String cvc;
}