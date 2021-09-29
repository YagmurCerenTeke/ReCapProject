package com.etiya.ReCapProject.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.BrandService;
import com.etiya.ReCapProject.business.abstracts.CarService;
import com.etiya.ReCapProject.business.abstracts.ColorService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.CarDao;
import com.etiya.ReCapProject.entities.concretes.Brand;
import com.etiya.ReCapProject.entities.concretes.Car;
import com.etiya.ReCapProject.entities.concretes.Color;
import com.etiya.ReCapProject.entities.dto.CarDetailDto;
import com.etiya.ReCapProject.entities.dto.CarDetailWithImagesDto;
import com.etiya.ReCapProject.entities.requests.carRequests.CreateCarRequest;
import com.etiya.ReCapProject.entities.requests.carRequests.DeleteCarRequest;
import com.etiya.ReCapProject.entities.requests.carRequests.UpdateCarRequest;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ColorService colorService;
	private BrandService brandService;

	@Autowired
	public CarManager(CarDao carDao, ColorService colorService, BrandService brandService) {
		super();
		this.carDao = carDao;
		this.colorService = colorService;
		this.brandService = brandService;
	}

	@Override
	public DataResult<List<Car>> getAll() {
		return new SuccessDataResult<List<Car>>(this.carDao.findAll(), Messages.CARS + Messages.LIST);

	}

	@Override
	public DataResult<List<Car>> getAllAvailableCars() {
		return new SuccessDataResult<List<Car>>(this.carDao.findByInRepairFalse(),
				Messages.CARS + Messages.LIST);
	}
	
	@Override
	public DataResult<Car> getById(int carId) {
		return new SuccessDataResult<Car>(this.carDao.getById(carId), Messages.CAR + Messages.LIST);

	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		Car car = new Car();
		
		Brand brand = this.brandService.getById(createCarRequest.getBrandId()).getData();
		car.setBrand(brand);
		
		Color color = this.colorService.getById(createCarRequest.getColorId()).getData();
		car.setColor(color);
		car.setDailyPrice(createCarRequest.getDailyPrice());
		car.setDescription(createCarRequest.getDescription());
		car.setCarName(createCarRequest.getCarName());
		car.setModelYear(createCarRequest.getModelYear());
		car.setMinFindeksScore(createCarRequest.getMinFindeksScore());
		car.setCity(createCarRequest.getCity());
		car.setKm(createCarRequest.getKm());
		car.setInRepair(false);

		this.carDao.save(car);
		return new SuccessResult(Messages.CAR + Messages.ADD);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {
		Car car = this.carDao.getById(updateCarRequest.getCarId());
		
		Brand brand = this.brandService.getById(updateCarRequest.getBrandId()).getData();
		car.setBrand(brand);
		
		Color color = this.colorService.getById(updateCarRequest.getColorId()).getData();
		car.setColor(color);
		
		car.setDailyPrice(updateCarRequest.getDailyPrice());
		car.setDescription(updateCarRequest.getDescription());
		car.setCarName(updateCarRequest.getCarName());
		car.setModelYear(updateCarRequest.getModelYear());
		car.setMinFindeksScore(updateCarRequest.getMinFindeksScore());
		car.setCity(updateCarRequest.getCity());
		car.setKm(updateCarRequest.getKm());
		car.setInRepair(false);

		this.carDao.save(car);
		return new SuccessResult(Messages.CAR + Messages.UPDATE);

	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		Car car = this.carDao.getById(deleteCarRequest.getCarId());

		this.carDao.delete(car);
		return new SuccessResult(Messages.CAR + Messages.DELETE);

	}

	@Override
	public DataResult<List<CarDetailDto>> getCarsWithDetails() {
		return new SuccessDataResult<List<CarDetailDto>>(this.carDao.getCarsWithDetails(),
				Messages.CARS + Messages.LIST);
	}

	@Override
	public DataResult<List<Car>> getCarsByColorId(int colorId) {
		return new SuccessDataResult<List<Car>>(this.carDao.getByColor_ColorId(colorId),
				Messages.CARS + Messages.LIST);
	}

	@Override
	public DataResult<List<Car>> getCarsByBrandId(int brandId) {
		return new SuccessDataResult<List<Car>>(this.carDao.getByBrand_BrandId(brandId),
				Messages.CARS + Messages.LIST);
	}
	
	@Override
	public DataResult<List<CarDetailWithImagesDto>> getCarDetailsByCarId(int carId) {
		if(this.carDao.getCarDetailsByCarId(carId).isEmpty()) {
			List<CarDetailWithImagesDto> listCar = new ArrayList<CarDetailWithImagesDto>();
			CarDetailWithImagesDto carDetailWithImagesDto = new CarDetailWithImagesDto();
			carDetailWithImagesDto.setBrandName(carDao.getById(carId).getBrand().getBrandName());
			carDetailWithImagesDto.setCarName(carDao.getById(carId).getCarName());
			carDetailWithImagesDto.setColorName(carDao.getById(carId).getColor().getColorName());
			carDetailWithImagesDto.setDailyPrice(carDao.getById(carId).getDailyPrice());
			carDetailWithImagesDto.setId(carId);
			carDetailWithImagesDto.setImagePath("C:/Users/yagmur.teke/Desktop/Image/default.jpg");
			
			listCar.add(carDetailWithImagesDto);
			
		return new SuccessDataResult<List<CarDetailWithImagesDto>>(listCar,
				Messages.CAR + Messages.LIST);
		}else {
		return new SuccessDataResult<List<CarDetailWithImagesDto>>(this.carDao.getCarDetailsByCarId(carId),
				Messages.CAR + Messages.LIST);
	}}

	@Override
	public DataResult<List<Car>> getByCity(String city) {
		return new SuccessDataResult<List<Car>>(this.carDao.getByCity(city),
				Messages.CARS + Messages.LIST);
	}

	

}
