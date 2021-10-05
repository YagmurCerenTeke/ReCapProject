package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.CorporateCustomer;
import com.etiya.ReCapProject.entities.dto.CorporateCustomerDto;
import com.etiya.ReCapProject.entities.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.etiya.ReCapProject.entities.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.etiya.ReCapProject.entities.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;

public interface CorporateCustomerService {
	
	DataResult<List<CorporateCustomer>> findAll();
	
	DataResult<List<CorporateCustomerDto>> getAll();

	DataResult<CorporateCustomer> findById(int corporateCustomerId);

	DataResult<CorporateCustomerDto> getById(int corporateCustomerId);

	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);

	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest);

	Result existsByUserId(int applicationUserId);
	
	DataResult<CorporateCustomer> getByApplicationUser_UserId(int applicationUserId);
	
}
