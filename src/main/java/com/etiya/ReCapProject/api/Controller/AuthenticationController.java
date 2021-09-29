package com.etiya.ReCapProject.api.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.ReCapProject.business.abstracts.AuthenticationService;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.requests.authenticationRequests.CreateCorporateCustomerRegisterRequest;
import com.etiya.ReCapProject.entities.requests.authenticationRequests.CreateIndividualCustomerRegisterRequest;
import com.etiya.ReCapProject.entities.requests.authenticationRequests.CreateLoginRequest;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {
	private AuthenticationService authenticationService;

	@Autowired
	public AuthenticationController(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}
	
	@PostMapping("/individualCustomerRegister")
	Result individualCustomerRegister(@Valid @RequestBody CreateIndividualCustomerRegisterRequest registerIndividualCustomerRequest) {
		return this.authenticationService.individualCustomerRegister(registerIndividualCustomerRequest);
	}
	
	@PostMapping("/corporateCustomerRegister")
	Result corporateCustomerRegister(@Valid @RequestBody CreateCorporateCustomerRegisterRequest registerCorporateCustomerRequest) {
		return this.authenticationService.corporateCustomerRegister(registerCorporateCustomerRequest);
	}
	
	@PostMapping("/login")
	Result login(@Valid @RequestBody CreateLoginRequest createLoginRequest) {
		return this.authenticationService.login(createLoginRequest);
	}
	
}
