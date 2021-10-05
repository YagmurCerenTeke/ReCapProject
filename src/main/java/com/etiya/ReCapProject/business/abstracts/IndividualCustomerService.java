package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.IndividualCustomer;
import com.etiya.ReCapProject.entities.dto.IndividualCustomerDto;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;

public interface IndividualCustomerService {
	
	DataResult<List<IndividualCustomer>> findAll();

	DataResult<List<IndividualCustomerDto>> getAll();

	DataResult<IndividualCustomer> findById(int individualCustomerId);
	
	DataResult<IndividualCustomerDto> getById(int individualCustomerId);

	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);

	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);

	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest);
	
	Result existsByUserId(int applicationUserId);
	
	DataResult<IndividualCustomer> getByApplicationUser_UserId(int applicationUserId);
}
