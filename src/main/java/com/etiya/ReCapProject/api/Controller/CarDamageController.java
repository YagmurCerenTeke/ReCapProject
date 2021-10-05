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

import com.etiya.ReCapProject.business.abstracts.CarDamageService;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.dto.CarDamageDto;
import com.etiya.ReCapProject.entities.requests.carDamageRequests.CreateCarDamageRequest;
import com.etiya.ReCapProject.entities.requests.carDamageRequests.DeleteCarDamageRequest;
import com.etiya.ReCapProject.entities.requests.carDamageRequests.UpdateCarDamageRequest;



@RestController
@RequestMapping("api/carDamages")
public class CarDamageController {
	
	CarDamageService carDamageService;

	@Autowired
	public CarDamageController(CarDamageService carDamageService) {
		super();
		this.carDamageService = carDamageService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CarDamageDto>> getAll() {
		return this.carDamageService.getAll();
	}
	
	@GetMapping("/getByCarId")
	public DataResult<List<CarDamageDto>> getByCarId(int carId) {
		return this.carDamageService.getByCarId(carId);
	}
	
	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateCarDamageRequest createCarDamageRequest) {
		return this.carDamageService.add(createCarDamageRequest);
	}
	
	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateCarDamageRequest updateCarDamageRequest) {
		return this.carDamageService.update(updateCarDamageRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delte(DeleteCarDamageRequest deleteCarDamageRequest) {
		return this.carDamageService.delete(deleteCarDamageRequest);
	}
}
