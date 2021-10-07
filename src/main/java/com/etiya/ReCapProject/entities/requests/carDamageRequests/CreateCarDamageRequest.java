package com.etiya.ReCapProject.entities.requests.carDamageRequests;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarDamageRequest {

	@JsonIgnore
	private int damageId;
	
	@NotNull
	private int carId;
	
	@NotNull
	private String damageDetail;
	
}