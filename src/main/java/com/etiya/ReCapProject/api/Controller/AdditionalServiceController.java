package com.etiya.ReCapProject.api.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.ReCapProject.business.abstracts.AdditionalServiceService;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.AdditionalService;
import com.etiya.ReCapProject.entities.dto.AdditionalServiceDto;
import com.etiya.ReCapProject.entities.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.etiya.ReCapProject.entities.requests.additionalServiceRequests.DeleteAdditionalServiceRequest;
import com.etiya.ReCapProject.entities.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;

@RestController
@RequestMapping("api/additionalServices")
public class AdditionalServiceController {
	
	AdditionalServiceService additionalServiceService;

	@Autowired
	public AdditionalServiceController(AdditionalServiceService additionalServiceService) {
		super();
		this.additionalServiceService = additionalServiceService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<AdditionalService>> getAll() {
		return this.additionalServiceService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<AdditionalService> getById(int additionalServiceId) {
		return this.additionalServiceService.getById(additionalServiceId);
	}
	
//	@GetMapping("/getAdditionalServiceRequests")
//	public DataResult<List<AdditionalServiceDto>> getAdditionalServiceRequests(boolean selected) {
//		return this.additionalServiceService.getAdditionalServiceRequests(selected);
//	}
	
	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		return this.additionalServiceService.add(createAdditionalServiceRequest);
	}
	
	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		return this.additionalServiceService.update(updateAdditionalServiceRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		return this.additionalServiceService.delete(deleteAdditionalServiceRequest);
	}
}
