package com.etiya.ReCapProject.api.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.ReCapProject.business.abstracts.IndividualCustomerService;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.dto.IndividualCustomerDto;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;

@RestController
@RequestMapping("api/individualcustomers")
public class IndividualCustomerController {
	IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomerController(IndividualCustomerService individualCustomerService) {
		super();
		this.individualCustomerService = individualCustomerService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<IndividualCustomerDto>> getAll() {
		return this.individualCustomerService.getAll();
	}
	
	@GetMapping("/getbyid")
	public DataResult<IndividualCustomerDto> getById(int customerId) {
		return this.individualCustomerService.getById(customerId);
	}
	
	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}
}
