package com.etiya.ReCapProject.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalServiceDto {

	private int additionalServiceId;
	
	private String additionalServiceName;
	
	private String details;
	
	private int price;
	
}
