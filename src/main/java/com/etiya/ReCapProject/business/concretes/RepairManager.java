package com.etiya.ReCapProject.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.RepairService;
import com.etiya.ReCapProject.business.abstracts.CarService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.RepairDao;
import com.etiya.ReCapProject.entities.concretes.Repair;
import com.etiya.ReCapProject.entities.dto.RepairDto;
import com.etiya.ReCapProject.entities.requests.repairRequest.DeleteRepairRequest;
import com.etiya.ReCapProject.entities.requests.repairRequest.CreateRepairRequest;
import com.etiya.ReCapProject.entities.requests.repairRequest.UpdateRepairRequest;

@Service
public class RepairManager implements RepairService {

	private RepairDao repairDao;
	private CarService carService;
	private ModelMapper modelMapper;

	@Autowired
	public RepairManager(RepairDao repairDao, CarService carService, ModelMapper modelMapper) {
		super();
		this.repairDao = repairDao;
		this.carService = carService;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<Repair>> findAll() {
		return new SuccessDataResult<List<Repair>>(this.repairDao.findAll(), Messages.REPAIRS + Messages.LIST);
	}

	@Override
	public DataResult<List<RepairDto>> getAll() {
		List<Repair> repairs = this.repairDao.findAll();
		List<RepairDto> repairsDto = new ArrayList<RepairDto>();
		
		for (Repair repair : repairs) {
			RepairDto mappedRepairDto = modelMapper.map(repair, RepairDto.class);
			
			repairsDto.add(mappedRepairDto);
		}		

		return new SuccessDataResult<List<RepairDto>>(repairsDto, Messages.REPAIRS + Messages.LIST);
	}

	@Override
	public Result insert(CreateRepairRequest createRepairRequest) {

		Repair repair = modelMapper.map(createRepairRequest, Repair.class);

		this.setInRepairIfFinishDateIsNull(createRepairRequest.getCarId(), createRepairRequest.getRepairFinishDate());
		this.repairDao.save(repair);
		return new SuccessResult(Messages.REPAIR + Messages.ADD);

	}

	@Override
	public Result update(UpdateRepairRequest updateRepairRequest) {

		Repair repair = modelMapper.map(updateRepairRequest, Repair.class);

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
		if (repairFinishDate == null) {
			this.carService.findById(carId).getData().setInRepair(true);
			return new SuccessResult();
		} else {
			this.carService.findById(carId).getData().setInRepair(false);
			return new SuccessResult();
		}
	}

}
