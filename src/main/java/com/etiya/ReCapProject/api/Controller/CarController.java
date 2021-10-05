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

import com.etiya.ReCapProject.business.abstracts.CarService;
import com.etiya.ReCapProject.core.utilities.results.*;
import com.etiya.ReCapProject.entities.dto.CarDetailDto;
import com.etiya.ReCapProject.entities.dto.CarDto;
import com.etiya.ReCapProject.entities.requests.carRequests.CreateCarRequest;
import com.etiya.ReCapProject.entities.requests.carRequests.DeleteCarRequest;
import com.etiya.ReCapProject.entities.requests.carRequests.UpdateCarRequest;

@RestController
@RequestMapping("api/cars")
public class CarController {
	CarService carService;

	@Autowired
	public CarController(CarService carService) {
		super();
		this.carService = carService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<CarDto>> getAll() {
		return this.carService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<CarDto> getById(int carId) {
		return this.carService.getById(carId);
	}
	
	@GetMapping("/getCarsByColorId")
	public DataResult<List<CarDto>> getCarsByColorId(int colorId) {
		return this.carService.getCarsByColorId(colorId);
	}
	
	@GetMapping("/getCarsByBrandId")
	public DataResult<List<CarDto>> getCarsByBrandId(int brandId) {
		return this.carService.getCarsByBrandId(brandId);
	}
	
	@GetMapping("/getCarsByCity")
	public DataResult<List<CarDto>> getByCity(String city){
		return this.carService.getByCity(city); 
	}
	
	@GetMapping("/getAllAvailableCars")
	public DataResult<List<CarDto>> getAllAvailableCars(){
		return this.carService.getAllAvailableCars(); 
	}
	
	@GetMapping("/getCarsWithDetails")
	public DataResult<List<CarDetailDto>> getCarsWithDetails() {
		return this.carService.getCarsWithDetails();
	}
	
//	@GetMapping("/getCarDetailsWithImagesByCarId")
//	DataResult<List<CarDetailWithImagesDto>> getCarDetailsWithImagesByCarId(int carId){
//		return this.carService.getCarDetailsByCarId(carId);
//	}
	
	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateCarRequest createCarRequest) {
		return this.carService.add(createCarRequest);
	}
	
	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateCarRequest updateCarRequest) {
		return this.carService.update(updateCarRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(DeleteCarRequest deleteCarRequest) {
		return this.carService.delete(deleteCarRequest);
	}
}
