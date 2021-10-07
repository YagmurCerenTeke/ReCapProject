package com.etiya.ReCapProject.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {

	private int rentalId;

	private String rentDate;

	private String returnDate;

	private String rentCity;

	private String returnCity;
	
	private boolean returned;
	
	private int carId;

	private ApplicationUserDto applicationUser;

}
