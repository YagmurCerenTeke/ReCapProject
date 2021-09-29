package com.etiya.ReCapProject.api.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.ReCapProject.business.abstracts.IndividualCustomerService;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.IndividualCustomer;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;

@RestController
@RequestMapping("api/individualcustomers")
public class IndividualCustomersController {
	IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		super();
		this.individualCustomerService = individualCustomerService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<IndividualCustomer>> getAll() {
		return this.individualCustomerService.getAll();
	}
	
	@GetMapping("/getbyid")
	public DataResult<IndividualCustomer> getById(int customerId) {
		return this.individualCustomerService.getById(customerId);
	}
	
	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}
	
	@PostMapping("/delete")
	public Result delte(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}
}