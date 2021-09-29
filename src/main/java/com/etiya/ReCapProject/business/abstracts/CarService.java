package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.Car;
import com.etiya.ReCapProject.entities.dto.CarDetailDto;
import com.etiya.ReCapProject.entities.dto.CarDetailWithImagesDto;
import com.etiya.ReCapProject.entities.requests.carRequests.CreateCarRequest;
import com.etiya.ReCapProject.entities.requests.carRequests.DeleteCarRequest;
import com.etiya.ReCapProject.entities.requests.carRequests.UpdateCarRequest;

public interface CarService {
	DataResult<List<Car>> getAll();

	DataResult<Car> getById(int carId);

	Result add(CreateCarRequest createCarRequest);

	Result update(UpdateCarRequest updateCarRequest);

	Result delete(DeleteCarRequest deleteCarRequest);
	
	DataResult<List<CarDetailDto>> getCarsWithDetails();
	
	DataResult<List<CarDetailWithImagesDto>> getCarDetailsByCarId(int carId) ;
	
	DataResult<List<Car>> getCarsByColorId(int colorId);
	
	DataResult<List<Car>> getCarsByBrandId(int brandId);
	
	DataResult<List<Car>> getByCity(String city);
	
	DataResult<List<Car>> getAllAvailableCars();
}
