package com.etiya.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.CarDamageService;
import com.etiya.ReCapProject.business.abstracts.CarService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.CarDamageDao;
import com.etiya.ReCapProject.entities.concretes.CarDamage;
import com.etiya.ReCapProject.entities.requests.carDamageRequests.CreateCarDamageRequest;
import com.etiya.ReCapProject.entities.requests.carDamageRequests.DeleteCarDamageRequest;
import com.etiya.ReCapProject.entities.requests.carDamageRequests.UpdateCarDamageRequest;

@Service
public class CarDamageManager implements CarDamageService {

	private CarDamageDao carDamageDao;
	private CarService carService;
	
	@Autowired
	public CarDamageManager(CarDamageDao carDamageDao, CarService carService) {
		super();
		this.carDamageDao = carDamageDao;
		this.carService = carService;
	}

	@Override
	public DataResult<List<CarDamage>> getAll() {
		return new SuccessDataResult<List<CarDamage>>(this.carDamageDao.findAll(), Messages.DAMAGES + Messages.LIST);
	}

	@Override
	public DataResult<List<CarDamage>> getByCarId(int carId) {
		return new SuccessDataResult<List<CarDamage>>(this.carDamageDao.getByCar_CarId(carId), Messages.DAMAGES + Messages.LIST);
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) {
		CarDamage carDamage = new CarDamage();
		carDamage.setCar(this.carService.getById(createCarDamageRequest.getCarId()).getData());
		carDamage.setDamageDetail(createCarDamageRequest.getDamageDetail());

		this.carDamageDao.save(carDamage);
		return new SuccessResult(Messages.DAMAGE + Messages.ADD);
	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) {
		CarDamage carDamage = this.carDamageDao.getById(updateCarDamageRequest.getCarDamageId());
		carDamage.setCar(this.carService.getById(updateCarDamageRequest.getCarId()).getData());
		carDamage.setDamageDetail(updateCarDamageRequest.getDamageDetail());

		this.carDamageDao.save(carDamage);
		return new SuccessResult(Messages.DAMAGE + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteCarDamageRequest deleteCarDamageRequest) {
		CarDamage carDamage = this.carDamageDao.getById(deleteCarDamageRequest.getCarDamageId());

		this.carDamageDao.delete(carDamage);
		return new SuccessResult(Messages.DAMAGE + Messages.DELETE);
	}

}
