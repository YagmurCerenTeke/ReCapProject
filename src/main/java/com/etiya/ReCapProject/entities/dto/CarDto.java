package com.etiya.ReCapProject.entities.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

	private int carId;

	private String carName;

	private String modelYear;

	private double dailyPrice;

	private String description;
	
	private int minFindeksScore;
	
	private String city;
	
	private int km;
	
	private boolean inRepair;

	private String brandName;

	private String colorName;
	
	private List<CarImageDto> carImages;
	
	private List<CarDamageDto> carDamages;
	
}
