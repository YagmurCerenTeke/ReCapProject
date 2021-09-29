package com.etiya.ReCapProject.entities.requests.rentalRequests;

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
public class DeleteRentalRequest {
	
	@NotNull(message = Messages.NOTNULL)
	private int rentalId;

}
