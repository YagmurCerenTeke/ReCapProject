package com.etiya.ReCapProject.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.IndividualCustomerService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.IndividualCustomerDao;
import com.etiya.ReCapProject.entities.concretes.IndividualCustomer;
import com.etiya.ReCapProject.entities.dto.IndividualCustomerDto;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private IndividualCustomerDao individualCustomerDao;
	private ModelMapper modelMapper;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapper modelMapper) {
		super();
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<IndividualCustomer>> findAll() {
		return new SuccessDataResult<List<IndividualCustomer>>(this.individualCustomerDao.findAll(),
				Messages.INDIVIDUALCUSTOMERS + Messages.LIST);
	}

	@Override
	public DataResult<List<IndividualCustomerDto>> getAll() {
		List<IndividualCustomer> individualCustomers = this.individualCustomerDao.findAll();
		List<IndividualCustomerDto> individualCustomersDto = new ArrayList<IndividualCustomerDto>();

		for (IndividualCustomer individualCustomer : individualCustomers) {
			IndividualCustomerDto mappedIndividualCustomer = modelMapper.map(individualCustomer,
					IndividualCustomerDto.class);

			individualCustomersDto.add(mappedIndividualCustomer);
		}
		return new SuccessDataResult<List<IndividualCustomerDto>>(individualCustomersDto,
				Messages.INDIVIDUALCUSTOMERS + Messages.LIST);
	}

	@Override
	public DataResult<IndividualCustomer> findById(int individualCustomerId) {
		return new SuccessDataResult<IndividualCustomer>(this.individualCustomerDao.getById(individualCustomerId),
				Messages.INDIVIDUALCUSTOMER + Messages.LIST);
	}

	@Override
	public DataResult<IndividualCustomerDto> getById(int individualCustomerId) {
		IndividualCustomerDto mappedIndividualCustomer = modelMapper
				.map(this.individualCustomerDao.getById(individualCustomerId), IndividualCustomerDto.class);

		return new SuccessDataResult<IndividualCustomerDto>(mappedIndividualCustomer,
				Messages.INDIVIDUALCUSTOMER + Messages.LIST);
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

		IndividualCustomer individualCustomer = modelMapper.map(createIndividualCustomerRequest,
				IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(Messages.INDIVIDUALCUSTOMER + Messages.ADD);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

		IndividualCustomer individualCustomer = modelMapper.map(updateIndividualCustomerRequest,
				IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(Messages.INDIVIDUALCUSTOMER + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {

		IndividualCustomer individualCustomer = this.individualCustomerDao
				.getById(deleteIndividualCustomerRequest.getIndividualCustomerId());

		this.individualCustomerDao.delete(individualCustomer);
		return new SuccessResult(Messages.INDIVIDUALCUSTOMER + Messages.DELETE);
	}

	@Override
	public Result existsByUserId(int applicationUserId) {
		if (this.individualCustomerDao.existsByApplicationUser_UserId(applicationUserId)) {
			return new SuccessResult();
		}
		return new ErrorResult();
	}

	@Override
	public DataResult<IndividualCustomer> getByApplicationUser_UserId(int applicationUserId) {
		return new SuccessDataResult<IndividualCustomer>(
				this.individualCustomerDao.getByApplicationUser_UserId(applicationUserId));
	}
}
