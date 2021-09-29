package com.etiya.ReCapProject.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDetailDto {
	
	private int id;
	
	private String carName;
	
	private String brandName;
	
	private String colorName;
	
	private double dailyPrice;
	
}
