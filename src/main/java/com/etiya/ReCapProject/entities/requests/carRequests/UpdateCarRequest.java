package com.etiya.ReCapProject.entities.requests.carRequests;

import javax.validation.constraints.NotBlank;
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
public class UpdateCarRequest {
	
	@NotNull
	private int carId;
	
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String carName;
	
	@NotNull
	private int brandId;
	
	@NotNull
	private int colorId;

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String modelYear;

	@NotNull
	private double dailyPrice;

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String description;
	
	@NotNull
	private int minFindeksScore;
	
	@NotBlank(message = Messages.NOTNULL)
	private String city;
	
	@NotNull
	private int km;
	
}
