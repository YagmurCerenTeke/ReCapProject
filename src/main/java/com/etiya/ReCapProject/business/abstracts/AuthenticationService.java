package com.etiya.ReCapProject.business.abstracts;

import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.requests.authenticationRequests.CreateCorporateCustomerRegisterRequest;
import com.etiya.ReCapProject.entities.requests.authenticationRequests.CreateIndividualCustomerRegisterRequest;
import com.etiya.ReCapProject.entities.requests.authenticationRequests.CreateLoginRequest;


public interface AuthenticationService {
	
	Result individualCustomerRegister(CreateIndividualCustomerRegisterRequest registerIndividualCustomerRequest);

	Result corporateCustomerRegister(CreateCorporateCustomerRegisterRequest registerCorporateCustomerRequest);

	Result login(CreateLoginRequest createLoginRequest);
}
