package com.etiya.ReCapProject.entities.requests.repairRequest;

import javax.validation.constraints.NotNull;

import com.etiya.ReCapProject.business.constants.Messages;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRepairRequest {
	
	@JsonIgnore
	private int repairId;

	@NotNull(message = Messages.NOTNULL)
	private String repairStartDate;
	
	private String repairFinishDate;
	
	@NotNull(message = Messages.NOTNULL)
	private int carId;
}
