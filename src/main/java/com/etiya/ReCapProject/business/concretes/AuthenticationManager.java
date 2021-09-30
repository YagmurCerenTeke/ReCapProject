package com.etiya.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.AuthenticationService;
import com.etiya.ReCapProject.business.abstracts.CorporateCustomerService;
import com.etiya.ReCapProject.business.abstracts.IndividualCustomerService;
import com.etiya.ReCapProject.business.abstracts.UserService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.entities.concretes.ApplicationUser;
import com.etiya.ReCapProject.entities.requests.authenticationRequests.CreateCorporateCustomerRegisterRequest;
import com.etiya.ReCapProject.entities.requests.authenticationRequests.CreateIndividualCustomerRegisterRequest;
import com.etiya.ReCapProject.entities.requests.authenticationRequests.CreateLoginRequest;
import com.etiya.ReCapProject.entities.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.CreateIndividualCustomerRequest;

@Service
public class AuthenticationManager implements AuthenticationService {

	private UserService userService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public AuthenticationManager(UserService userService, IndividualCustomerService individualCustomerService,
			CorporateCustomerService corporateCustomerService) {
		super();
		this.userService = userService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
	}

	@Override
	public Result individualCustomerRegister(
			CreateIndividualCustomerRegisterRequest createIndividualCustomerRegisterRequest) {
		var result = BusinessRules.run(checkIfEmailExists(createIndividualCustomerRegisterRequest.getEmail()),
				confirmPassword(createIndividualCustomerRegisterRequest.getPassword(),
						createIndividualCustomerRegisterRequest.getPasswordConfirm()));

		if (result != null) {
			return result;
		}

		CreateIndividualCustomerRequest individualCustomerRequest = new CreateIndividualCustomerRequest();
		individualCustomerRequest.setFirstName(createIndividualCustomerRegisterRequest.getFirstName());
		individualCustomerRequest.setLastName(createIndividualCustomerRegisterRequest.getLastName());
		individualCustomerRequest
				.setNationalIdentityNumber(createIndividualCustomerRegisterRequest.getNationalIdentityNumber());
		individualCustomerRequest.setUserId(this.createUser(createIndividualCustomerRegisterRequest.getEmail(),
				createIndividualCustomerRegisterRequest.getPassword()).getData().getUserId());
		this.individualCustomerService.add(individualCustomerRequest);

		return new SuccessResult(Messages.INDIVIDUALCUSTOMERREGISTER);
	}

	@Override
	public Result corporateCustomerRegister(CreateCorporateCustomerRegisterRequest createCorporateCustomerRequest) {
		var result = BusinessRules.run(checkIfEmailExists(createCorporateCustomerRequest.getEmail()), confirmPassword(
				createCorporateCustomerRequest.getPassword(), createCorporateCustomerRequest.getPasswordConfirm()));

		if (result != null) {
			return result;
		}

		CreateCorporateCustomerRequest corporateCustomerRequest = new CreateCorporateCustomerRequest();
		corporateCustomerRequest.setCompanyName(createCorporateCustomerRequest.getCompanyName());
		corporateCustomerRequest.setTaxNumber(createCorporateCustomerRequest.getTaxNumber());
		corporateCustomerRequest.setUserId(
				this.createUser(createCorporateCustomerRequest.getEmail(), createCorporateCustomerRequest.getPassword())
						.getData().getUserId());

		this.corporateCustomerService.add(corporateCustomerRequest);

		return new SuccessResult(Messages.CORPORATECUSTOMERREGISTER);
	}

	@Override
	public Result login(CreateLoginRequest createLoginRequest) {

		var result = BusinessRules.run(checkPassword(createLoginRequest));

		if (result != null) {
			return result;
		}

		return new SuccessResult(Messages.LOGINOK);
	}

	private DataResult<ApplicationUser> createUser(String email, String password) {

		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setEmail(email);
		applicationUser.setPassword(password);

		this.userService.add(applicationUser);
		return new SuccessDataResult<ApplicationUser>(applicationUser);
	}
	
	private Result checkIfEmailExists(String registerEmail) {
		List<String> emails = this.userService.findAllEmail().getData();
		for (String email : emails) {
			if (registerEmail.equals(email)) {
				return new ErrorResult(Messages.EMAILEXISTS);
			}
		}
		return new SuccessResult();
	}

	private Result checkPassword(CreateLoginRequest createLoginRequest) {
		if (this.userService.getPasswordByEmail(createLoginRequest.getEmail()).getMessage().toString()
				.equals(createLoginRequest.getPassword())) {
			return new SuccessResult();
		} else {
			return new ErrorResult(Messages.USERNAMEANDPASSWORDNOTMATCH);
		}
	}

	private Result confirmPassword(String password, String passwordConfirm) {
		if (!password.equals(passwordConfirm)) {
			return new ErrorResult(Messages.PASSWORDNOTMATCH);
		}
		return new SuccessResult();
	}



}
