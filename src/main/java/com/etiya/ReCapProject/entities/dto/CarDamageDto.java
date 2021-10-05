package com.etiya.ReCapProject.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageDto {

	private int carDamageId;
	
	private String damageDetail;
	
	private int carId;
}
