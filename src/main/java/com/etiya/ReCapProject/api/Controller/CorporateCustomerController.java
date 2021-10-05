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

import com.etiya.ReCapProject.business.abstracts.CorporateCustomerService;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.dto.CorporateCustomerDto;
import com.etiya.ReCapProject.entities.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.etiya.ReCapProject.entities.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;

@RestController
@RequestMapping("api/corporateCustomers")
public class CorporateCustomerController {
	CorporateCustomerService corporateCustomerService;

	@Autowired
	public CorporateCustomerController(CorporateCustomerService corporateCustomerService) {
		super();
		this.corporateCustomerService = corporateCustomerService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<CorporateCustomerDto>> getAll() {
		return this.corporateCustomerService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<CorporateCustomerDto> getById(int customerId) {
		return this.corporateCustomerService.getById(customerId);
	}
	
	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		return this.corporateCustomerService.update(updateCorporateCustomerRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);
	}
}