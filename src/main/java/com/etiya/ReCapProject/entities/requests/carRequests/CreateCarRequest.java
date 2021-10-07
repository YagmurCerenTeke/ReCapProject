package com.etiya.ReCapProject.entities.requests.carRequests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.etiya.ReCapProject.business.constants.Messages;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

	@JsonIgnore
	private String carId;
	
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String carName;

	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String modelYear;

	@NotNull
	@Min(0)
	private double dailyPrice;
	
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	@Size(max = 100)
	private String description;
	
	@NotNull
	private int minFindeksScore;

	@NotNull
	private int brandId;

	@NotNull
	private int colorId;
	
	@NotBlank(message = Messages.NOTNULL)
	private String city;
	
	@NotNull
	private int km;
}
