package com.etiya.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.RepairService;
import com.etiya.ReCapProject.business.abstracts.CarService;
import com.etiya.ReCapProject.business.abstracts.RentalService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.RepairDao;
import com.etiya.ReCapProject.entities.concretes.Repair;
import com.etiya.ReCapProject.entities.requests.repairRequest.DeleteRepairRequest;
import com.etiya.ReCapProject.entities.requests.repairRequest.CreateRepairRequest;
import com.etiya.ReCapProject.entities.requests.repairRequest.UpdateRepairRequest;

@Service
public class RepairManager implements RepairService {

	private RepairDao repairDao;
	private CarService carService;
	private RentalService rentalService;

	@Autowired
	public RepairManager(RepairDao repairDao, CarService carService, RentalService rentalService) {
		super();
		this.repairDao = repairDao;
		this.carService = carService;
		this.rentalService = rentalService;
	}

	@Override
	public DataResult<List<Repair>> getAll() {
		return new SuccessDataResult<List<Repair>>(this.repairDao.findAll(), Messages.REPAIRS + Messages.LIST);
	}

	@Override
	public Result insert(CreateRepairRequest createRepairRequest) {
		var result = BusinessRules
				.run(this.rentalService.getByCarIdWhereReturnDateIsNull(createRepairRequest.getCarId()));

		if (result != null) {
			return result;
		}

		Repair repair = new Repair();
		repair.setRepairStartDate(createRepairRequest.getRepairStartDate());
		repair.setRepairFinishDate(createRepairRequest.getRepairFinishDate());
		repair.setCar(this.carService.getById(createRepairRequest.getCarId()).getData());
		this.setInRepairIfFinishDateIsNull(createRepairRequest.getCarId(), createRepairRequest.getRepairFinishDate());
		this.repairDao.save(repair);
		return new SuccessResult(Messages.REPAIR + Messages.ADD);

	}

	@Override
	public Result update(UpdateRepairRequest updateRepairRequest) {

		Repair repair = this.repairDao.getById(updateRepairRequest.getRepairId());
		repair.setCar(this.carService.getById(updateRepairRequest.getCarId()).getData());
		repair.setRepairStartDate(updateRepairRequest.getRepairStartDate());
		repair.setRepairFinishDate(updateRepairRequest.getRepairFinishDate());
		this.setInRepairIfFinishDateIsNull(updateRepairRequest.getCarId(), updateRepairRequest.getRepairFinishDate());
		this.repairDao.save(repair);
		return new SuccessResult(Messages.REPAIR + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteRepairRequest deleteRepairRequest) {
		this.repairDao.delete(this.repairDao.getById(deleteRepairRequest.getRepairId()));
		return new SuccessResult(Messages.REPAIR + Messages.DELETE);
	}

	private Result setInRepairIfFinishDateIsNull(int carId, String repairFinishDate) {
		if (repairFinishDate.isBlank() || repairFinishDate.isEmpty()) {
			this.carService.getById(carId).getData().setInRepair(false);
			return new SuccessResult();
		} else {
			this.carService.getById(carId).getData().setInRepair(false);
			return new SuccessResult();
		}
	}

	
}
