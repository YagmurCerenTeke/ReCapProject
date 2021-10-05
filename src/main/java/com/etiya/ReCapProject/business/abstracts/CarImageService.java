package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.CarImage;
import com.etiya.ReCapProject.entities.dto.CarImageDto;
import com.etiya.ReCapProject.entities.requests.carImageRequests.CreateCarImageRequest;
import com.etiya.ReCapProject.entities.requests.carImageRequests.DeleteCarImageRequest;
import com.etiya.ReCapProject.entities.requests.carImageRequests.UpdateCarImageRequest;


public interface CarImageService {
	
	DataResult<List<CarImage>> findAll();

	DataResult<List<CarImageDto>> getAll();
	
	DataResult<CarImage> getById(int carImageId); 
	
	DataResult<List<CarImage>> findImagePathsByCarId(int carId);
	
	DataResult<List<CarImageDto>> getImagePathsByCarId(int carId);
	
	Result add(CreateCarImageRequest createCarImageRequest);

	Result update(UpdateCarImageRequest updateCarImageRequest);

	Result delete(DeleteCarImageRequest deleteCarImageRequest);
}
