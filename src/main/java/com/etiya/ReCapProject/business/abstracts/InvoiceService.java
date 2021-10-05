package com.etiya.ReCapProject.business.abstracts;

import java.util.Date;
import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.Invoice;
import com.etiya.ReCapProject.entities.requests.invoiceRequests.DeleteInvoiceRequest;

public interface InvoiceService {

	DataResult<List<Invoice>> getAll();
	
	DataResult<Invoice> getById(int invoiceId);
	
	Result insert(Invoice invoice);

	//Result update(UpdateInvoiceRequest updateInvoiceRequest);

	Result delete(DeleteInvoiceRequest deleteInvoiceRequest);
	
	DataResult<List<Invoice>> findInvoicesBetween(Date endDate, Date startDate);

	DataResult<List<Invoice>> getByRental_ApplicationUser_UserId(int userId);
}
