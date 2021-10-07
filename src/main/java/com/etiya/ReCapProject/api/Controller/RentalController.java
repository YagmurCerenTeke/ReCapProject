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

import com.etiya.ReCapProject.business.abstracts.RentalService;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.dto.RentalDto;
import com.etiya.ReCapProject.entities.requests.rentalRequests.DeleteRentalRequest;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.CreateCreditCardRequest;
import com.etiya.ReCapProject.entities.requests.rentalRequests.CreateRentalRequest;
import com.etiya.ReCapProject.entities.requests.rentalRequests.UpdateRentalRequest;

@RestController
@RequestMapping("api/rentals")
public class RentalController {
	
	RentalService rentalService;

	@Autowired
	public RentalController(RentalService rentalService) {
		super();
		this.rentalService = rentalService;
	}

	@GetMapping("/getAll")
	public DataResult<List<RentalDto>> getAll() {
		return this.rentalService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<RentalDto> getById(int rentalId) {
		return this.rentalService.getById(rentalId);
	}

	@GetMapping("/existsByUserId")
	public Result existsByUserId(int applicationUserId) {
		return this.rentalService.existsByUserId(applicationUserId);
	}
	
	@PostMapping("/add")
	public Result add(@Valid CreateCreditCardRequest createCreditCardRequest,@Valid @RequestBody CreateRentalRequest createRentalRequest) {
		return this.rentalService.insert(createRentalRequest, createCreditCardRequest);
	}

	@PostMapping("/update")
	public Result update(@Valid @RequestBody CreateCreditCardRequest createCreditCardRequest, UpdateRentalRequest updateRentalRequest) {
		return this.rentalService.update(updateRentalRequest, createCreditCardRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		return this.rentalService.delete(deleteRentalRequest);
	}
	
}
