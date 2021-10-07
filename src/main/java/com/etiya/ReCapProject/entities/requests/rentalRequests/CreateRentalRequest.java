package com.etiya.ReCapProject.entities.requests.rentalRequests;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.etiya.ReCapProject.business.constants.Messages;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class CreateRentalRequest {

	@JsonIgnore
	private int rentalId;
	
	@NotNull(message = Messages.NOTNULL)
	private int carId;
	
	@NotNull(message = Messages.NOTNULL)
	private String rentDate;
	
	private String returnDate;
	
	@NotBlank(message = Messages.NOTNULL)
	private String rentCity;

	private String returnCity;
	
	@NotNull(message = Messages.NOTNULL)
	private int rentKm;
	
	private int returnKm;
	
	@NotNull(message = Messages.NOTNULL)
	private boolean returned;
	
	@NotNull(message = Messages.NOTNULL)
	private boolean saveCreditCard;
	
	@NotNull(message = Messages.NOTNULL)
	private int applicationUserUserUserId;
	
	private List<Integer> additionalService;
	
}