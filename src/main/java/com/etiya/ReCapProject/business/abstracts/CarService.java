package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.Car;
import com.etiya.ReCapProject.entities.dto.CarDetailDto;
import com.etiya.ReCapProject.entities.dto.CarDto;
import com.etiya.ReCapProject.entities.requests.carRequests.CreateCarRequest;
import com.etiya.ReCapProject.entities.requests.carRequests.DeleteCarRequest;
import com.etiya.ReCapProject.entities.requests.carRequests.UpdateCarRequest;

public interface CarService {

	DataResult<List<Car>> findAll();

	DataResult<List<CarDto>> getAll();

	DataResult<Car> findById(int carId);  

	DataResult<CarDto> getById(int carId);

	DataResult<List<Car>> findCarsByColorId(int colorId);

	DataResult<List<CarDto>> getCarsByColorId(int colorId);
	
	DataResult<List<Car>> findCarsByBrandId(int brandId);

	DataResult<List<CarDto>> getCarsByBrandId(int brandId);

	DataResult<List<Car>> findByCity(String city);

	DataResult<List<CarDto>> getByCity(String city);

	DataResult<List<Car>> findAllAvailableCars();

	DataResult<List<CarDto>> getAllAvailableCars();

	DataResult<List<CarDetailDto>> getCarsWithDetails();

//	DataResult<List<CarDetailWithImagesDto>> getCarDetailsByCarId(int carId);

	Result add(CreateCarRequest createCarRequest);

	Result update(UpdateCarRequest updateCarRequest);

	Result delete(DeleteCarRequest deleteCarRequest);

}
