package com.etiya.ReCapProject.entities.requests.rentalRequests;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.etiya.ReCapProject.business.constants.Messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {

	@NotNull(message = Messages.NOTNULL)
	private int rentalId;
	
	@NotNull(message = Messages.NOTNULL)
	private int carId;
	
	@NotNull(message = Messages.NOTNULL)
	private int applicationUserUserUserId;
	
	@NotNull(message = Messages.NOTNULL)
	private String rentDate;
	
	private String returnDate;
	
	@NotNull(message = Messages.NOTNULL)
	private int rentKm;
	
	private int returnKm;
	
	@NotBlank(message = Messages.NOTNULL)
	private String rentCity;

	private String returnCity;
	
	@NotNull(message = Messages.NOTNULL)
	private boolean saveCreditCard;
	
	@NotNull(message = Messages.NOTNULL)
	private boolean returned;
	
	private List<Integer> additionalService;
}
