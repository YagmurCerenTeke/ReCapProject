package com.etiya.ReCapProject.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.CarService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.CarDao;
import com.etiya.ReCapProject.entities.concretes.Car;
import com.etiya.ReCapProject.entities.dto.CarDetailDto;
import com.etiya.ReCapProject.entities.dto.CarDto;
import com.etiya.ReCapProject.entities.requests.carRequests.CreateCarRequest;
import com.etiya.ReCapProject.entities.requests.carRequests.DeleteCarRequest;
import com.etiya.ReCapProject.entities.requests.carRequests.UpdateCarRequest;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapper modelMapper;

	@Autowired
	public CarManager(CarDao carDao, ModelMapper modelMapper) {
		super();
		this.carDao = carDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<Car>> findAll() {
		return new SuccessDataResult<List<Car>>(this.carDao.findAll(), Messages.CARS + Messages.LIST);
	}

	@Override
	public DataResult<List<CarDto>> getAll() {
		List<Car> cars = this.carDao.findAll();
		return new SuccessDataResult<List<CarDto>>(this.mappedCarList(cars), Messages.BRANDS + Messages.LIST);
	}

	@Override
	public DataResult<Car> findById(int carId) {
		return new SuccessDataResult<Car>(this.carDao.getById(carId), Messages.CAR + Messages.LIST);
	}

	@Override
	public DataResult<CarDto> getById(int carId) {
		Car car = this.carDao.getById(carId);
		return new SuccessDataResult<CarDto>(this.mappedCar(car), Messages.CAR + Messages.LIST);
	}

	@Override
	public DataResult<List<Car>> findCarsByColorId(int colorId) {
		return new SuccessDataResult<List<Car>>(this.carDao.getByColor_ColorId(colorId), Messages.CARS + Messages.LIST);
	}

	@Override
	public DataResult<List<CarDto>> getCarsByColorId(int colorId) {
		List<Car> cars = this.carDao.getByColor_ColorId(colorId);
		return new SuccessDataResult<List<CarDto>>(this.mappedCarList(cars), Messages.BRANDS + Messages.LIST);
	}

	@Override
	public DataResult<List<Car>> findCarsByBrandId(int brandId) {
		return new SuccessDataResult<List<Car>>(this.carDao.getByBrand_BrandId(brandId), Messages.CARS + Messages.LIST);
	}

	@Override
	public DataResult<List<CarDto>> getCarsByBrandId(int brandId) {
		List<Car> cars = this.carDao.getByBrand_BrandId(brandId);
		return new SuccessDataResult<List<CarDto>>(this.mappedCarList(cars), Messages.BRANDS + Messages.LIST);
	}

	@Override
	public DataResult<List<Car>> findByCity(String city) {
		return new SuccessDataResult<List<Car>>(this.carDao.getByCity(city), Messages.CARS + Messages.LIST);
	}

	@Override
	public DataResult<List<CarDto>> getByCity(String city) {
		List<Car> cars = this.carDao.getByCity(city);
		return new SuccessDataResult<List<CarDto>>(this.mappedCarList(cars), Messages.CARS + Messages.LIST);
	}

	@Override
	public DataResult<List<Car>> findAllAvailableCars() {
		return new SuccessDataResult<List<Car>>(this.carDao.findByInRepairFalse(), Messages.CARS + Messages.LIST);
	}

	@Override
	public DataResult<List<CarDto>> getAllAvailableCars() {
		return new SuccessDataResult<List<CarDto>>(this.mappedCarList(this.carDao.findByInRepairFalse()),
				Messages.CARS + Messages.LIST);
	}

	@Override
	public DataResult<List<CarDetailDto>> getCarsWithDetails() {
		return new SuccessDataResult<List<CarDetailDto>>(this.carDao.getCarsWithDetails(),
				Messages.CARS + Messages.LIST);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		Car car = modelMapper.map(createCarRequest, Car.class);

		this.carDao.save(car);
		return new SuccessResult(Messages.CAR + Messages.ADD);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {
		Car car = modelMapper.map(updateCarRequest, Car.class);

		this.carDao.save(car);
		return new SuccessResult(Messages.CAR + Messages.UPDATE);

	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		Car car = this.carDao.getById(deleteCarRequest.getCarId());

		this.carDao.delete(car);
		return new SuccessResult(Messages.CAR + Messages.DELETE);

	}

	private List<CarDto> mappedCarList(List<Car> cars) {
		List<CarDto> carsDto = new ArrayList<CarDto>();

		for (Car car : cars) {
			CarDto mappedCar = modelMapper.map(car, CarDto.class);

			carsDto.add(mappedCar);
		}
		return carsDto;
	}

	private CarDto mappedCar(Car car) {
		CarDto mappedCar = modelMapper.map(car, CarDto.class);

		return mappedCar;
	}

}

//@Override
//public DataResult<List<CarDetailWithImagesDto>> getCarDetailsByCarId(int carId) {
//	if (this.carDao.getCarDetailsByCarId(carId).isEmpty()) {
//		List<CarDetailWithImagesDto> listCar = new ArrayList<CarDetailWithImagesDto>();
//		CarDetailWithImagesDto carDetailWithImagesDto = new CarDetailWithImagesDto();
//		carDetailWithImagesDto.setBrandName(carDao.getById(carId).getBrand().getBrandName());
//		carDetailWithImagesDto.setCarName(carDao.getById(carId).getCarName());
//		carDetailWithImagesDto.setColorName(carDao.getById(carId).getColor().getColorName());
//		carDetailWithImagesDto.setDailyPrice(carDao.getById(carId).getDailyPrice());
//		carDetailWithImagesDto.setId(carId);
//		carDetailWithImagesDto.setImagePath("C:/Users/yagmur.teke/Desktop/Image/default.jpg");
//
//		listCar.add(carDetailWithImagesDto);
//
//		return new SuccessDataResult<List<CarDetailWithImagesDto>>(listCar, Messages.CAR + Messages.LIST);
//	} else {
//		return new SuccessDataResult<List<CarDetailWithImagesDto>>(this.carDao.getCarDetailsByCarId(carId),
//				Messages.CAR + Messages.LIST);
//	}
//}
