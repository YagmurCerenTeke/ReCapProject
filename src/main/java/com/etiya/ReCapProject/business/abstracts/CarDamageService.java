package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.CarDamage;
import com.etiya.ReCapProject.entities.dto.CarDamageDto;
import com.etiya.ReCapProject.entities.requests.carDamageRequests.CreateCarDamageRequest;
import com.etiya.ReCapProject.entities.requests.carDamageRequests.DeleteCarDamageRequest;
import com.etiya.ReCapProject.entities.requests.carDamageRequests.UpdateCarDamageRequest;

public interface CarDamageService {
	
	DataResult<List<CarDamage>> findAll();
	
	DataResult<List<CarDamageDto>> getAll();
	
	DataResult<List<CarDamage>> findByCarId(int carId);
	
	DataResult<List<CarDamageDto>> getByCarId(int carId);

	Result add(CreateCarDamageRequest createCarDamageRequest);

	Result update(UpdateCarDamageRequest updateCarDamageRequest);

	Result delete(DeleteCarDamageRequest deleteCarDamageRequest);
}
