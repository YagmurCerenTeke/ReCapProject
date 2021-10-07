package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.Rental;
import com.etiya.ReCapProject.entities.dto.RentalDto;
import com.etiya.ReCapProject.entities.requests.rentalRequests.DeleteRentalRequest;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.CreateCreditCardRequest;
import com.etiya.ReCapProject.entities.requests.invoiceRequests.CreateInvoiceRequest;
import com.etiya.ReCapProject.entities.requests.rentalRequests.CreateRentalRequest;
import com.etiya.ReCapProject.entities.requests.rentalRequests.UpdateRentalRequest;

public interface RentalService {
	
	DataResult<List<Rental>> findAll();
	
	DataResult<List<RentalDto>> getAll();

	DataResult<Rental> findById(int rentalId);
	
	DataResult<RentalDto> getById(int rentalId);
	
	Result existsByUserId(int applicationUserId);

	Result insert(CreateRentalRequest createRentalRequest, CreateCreditCardRequest createCreditCardRequest);

	Result update(UpdateRentalRequest updateRentalRequest, CreateCreditCardRequest createCreditCardRequest);

	Result delete(DeleteRentalRequest deleteRentalRequest);
	
	DataResult<CreateInvoiceRequest> createRentalInvoiceRequest(CreateRentalRequest createRentalRequest, Rental rental);
}
