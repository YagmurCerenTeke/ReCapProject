package com.etiya.ReCapProject.business.abstracts;

import java.util.Date;
import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.Invoice;
import com.etiya.ReCapProject.entities.dto.InvoiceDto;
import com.etiya.ReCapProject.entities.requests.invoiceRequests.CreateInvoiceRequest;
import com.etiya.ReCapProject.entities.requests.invoiceRequests.DeleteInvoiceRequest;

public interface InvoiceService {

	DataResult<List<Invoice>> findAll();
	
	DataResult<List<InvoiceDto>> getAll();
	
	DataResult<Invoice> findById(int invoiceId);

	DataResult<InvoiceDto> getById(int invoiceId);
	
	Result insert(CreateInvoiceRequest createInvoiceRequest, List<Integer> additionalServices);

	//Result update(UpdateInvoiceRequest updateInvoiceRequest);

	Result delete(DeleteInvoiceRequest deleteInvoiceRequest);
	
	DataResult<List<InvoiceDto>> findInvoicesBetween(Date endDate, Date startDate);

	DataResult<List<InvoiceDto>> getByRental_ApplicationUser_UserId(int userId);
}
