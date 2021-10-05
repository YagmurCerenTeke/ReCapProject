package com.etiya.ReCapProject.entities.requests.additionalServiceRequests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.etiya.ReCapProject.business.constants.Messages;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalServiceRequest {
	
	@NotNull
	private int additionalServiceId;
	
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String additionalServiceName;

	@Size(max = 100)
	@NotBlank(message = Messages.NOTNULL)
	@NotNull
	private String details;

	@NotNull
	private int price;
}
