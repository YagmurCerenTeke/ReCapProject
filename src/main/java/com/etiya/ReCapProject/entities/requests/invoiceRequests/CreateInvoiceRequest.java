package com.etiya.ReCapProject.entities.requests.invoiceRequests;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.etiya.ReCapProject.entities.concretes.AdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {

	@NotNull
    private int rentalId;

	private List<AdditionalService> additionalService;
}
