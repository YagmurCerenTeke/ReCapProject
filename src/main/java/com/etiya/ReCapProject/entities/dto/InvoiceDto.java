package com.etiya.ReCapProject.entities.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
	
	private int invoiceId;

	private String invoiceNo;

	private Date creationDate;

	private String rentalRentDate;

	private String rentalReturnDate;

	private long totalRentalDay;

	private Double amount;
	
	private int rentalId;

}
