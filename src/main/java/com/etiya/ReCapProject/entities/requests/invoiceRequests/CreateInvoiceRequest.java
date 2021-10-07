package com.etiya.ReCapProject.entities.requests.invoiceRequests;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.etiya.ReCapProject.entities.concretes.Rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {

	@NotNull
    private Rental rental;

	private List<Integer> additionalService;
}
