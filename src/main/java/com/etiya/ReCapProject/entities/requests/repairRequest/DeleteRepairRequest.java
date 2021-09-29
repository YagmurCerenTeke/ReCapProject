package com.etiya.ReCapProject.entities.requests.repairRequest;


import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.etiya.ReCapProject.business.constants.Messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class DeleteRepairRequest {

	@NotNull(message = Messages.NOTNULL)
	private int repairId;
}
