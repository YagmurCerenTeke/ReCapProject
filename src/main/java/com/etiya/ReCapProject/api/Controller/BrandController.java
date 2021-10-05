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

import com.etiya.ReCapProject.business.abstracts.BrandService;
import com.etiya.ReCapProject.core.utilities.results.*;
import com.etiya.ReCapProject.entities.dto.BrandDto;
import com.etiya.ReCapProject.entities.requests.brandRequests.CreateBrandRequest;
import com.etiya.ReCapProject.entities.requests.brandRequests.DeleteBrandRequest;
import com.etiya.ReCapProject.entities.requests.brandRequests.UpdateBrandRequest;

@RestController
@RequestMapping("api/brands")
public class BrandController {
	
	BrandService brandService;

	@Autowired
	public BrandController(BrandService brandService) {
		super();
		this.brandService = brandService;
	}
	
	@GetMapping("/getAll")
	public  DataResult<List<BrandDto>> getAll() {
		return this.brandService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<BrandDto> getById(int brandId) {
		return this.brandService.getById(brandId);
	}
	
	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateBrandRequest createBrandRequest) {
		return this.brandService.add(createBrandRequest);
	}
	
	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateBrandRequest updateBrandRequest) {
		return this.brandService.update(updateBrandRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		return this.brandService.delete(deleteBrandRequest);
	}
}
