package com.etiya.ReCapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.CarDamageService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.CarDamageDao;
import com.etiya.ReCapProject.entities.concretes.CarDamage;
import com.etiya.ReCapProject.entities.dto.CarDamageDto;
import com.etiya.ReCapProject.entities.requests.carDamageRequests.CreateCarDamageRequest;
import com.etiya.ReCapProject.entities.requests.carDamageRequests.DeleteCarDamageRequest;
import com.etiya.ReCapProject.entities.requests.carDamageRequests.UpdateCarDamageRequest;

@Service
public class CarDamageManager implements CarDamageService {

	private CarDamageDao carDamageDao;
	private ModelMapper modelMapper;
	
	@Autowired
	public CarDamageManager(CarDamageDao carDamageDao, ModelMapper modelMapper) {
		super();
		this.carDamageDao = carDamageDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<CarDamage>> findAll() {
		return new SuccessDataResult<List<CarDamage>>(this.carDamageDao.findAll(), Messages.DAMAGES + Messages.LIST);
	}
	
	@Override
	public DataResult<List<CarDamageDto>> getAll() {
		
		List<CarDamage> carDamages = this.carDamageDao.findAll();
		List<CarDamageDto> carDamagesDto = carDamages.stream().map(carDamage -> modelMapper.map(carDamage, CarDamageDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarDamageDto>>(carDamagesDto, Messages.DAMAGES + Messages.LIST);
	}

	@Override
	public DataResult<List<CarDamage>> findByCarId(int carId) {
		return new SuccessDataResult<List<CarDamage>>(this.carDamageDao.getByCar_CarId(carId), Messages.DAMAGES + Messages.LIST);
	}
	
	@Override
	public DataResult<List<CarDamageDto>> getByCarId(int carId) {
		List<CarDamageDto> carDamagesDto = this.carDamageDao.getByCar_CarId(carId).stream().map(carDamage -> 
		modelMapper.map(carDamage, CarDamageDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarDamageDto>>(carDamagesDto, Messages.DAMAGES + Messages.LIST);
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) {
		CarDamage carDamage = modelMapper.map(createCarDamageRequest, CarDamage.class);
		
		this.carDamageDao.save(carDamage);
		return new SuccessResult(Messages.DAMAGE + Messages.ADD);
	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) {
		CarDamage carDamage = modelMapper.map(updateCarDamageRequest, CarDamage.class);

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
