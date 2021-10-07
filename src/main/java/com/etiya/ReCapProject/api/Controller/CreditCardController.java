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

import com.etiya.ReCapProject.business.abstracts.CreditCardService;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.dto.CreditCardDto;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.CreateCreditCardRequest;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.DeleteCreditCardRequest;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.UpdateCreditCardRequest;

@RestController
@RequestMapping("api/creditCards")
public class CreditCardController {

	CreditCardService creditCardService;

	@Autowired
	public CreditCardController(CreditCardService creditCardService) {
		super();
		this.creditCardService = creditCardService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CreditCardDto>> getAll() {
		return this.creditCardService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<CreditCardDto> getById(int carInformationId) {
		return this.creditCardService.getById(carInformationId);
	}

	@GetMapping("/getCardInformationsByApplicationUser_UserId")
	DataResult<List<CreditCardDto>> getCardInformationsByApplicationUser_UserId(int applicationUserId) {
		return this.creditCardService.getCreditCardsByApplicationUser_UserId(applicationUserId);
	}

	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateCreditCardRequest createCardInformationRequest) {
		return this.creditCardService.add(createCardInformationRequest);
	}

	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateCreditCardRequest updateCardInformationRequest) {
		return this.creditCardService.update(updateCardInformationRequest);
	}

	@DeleteMapping("/delete")
	public Result delte(DeleteCreditCardRequest deleteCardInformationRequest) {
		return this.creditCardService.delete(deleteCardInformationRequest);
	}

}
