package com.etiya.ReCapProject.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.CorporateCustomerService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.CorporateCustomerDao;
import com.etiya.ReCapProject.entities.concretes.CorporateCustomer;

import com.etiya.ReCapProject.entities.dto.CorporateCustomerDto;
import com.etiya.ReCapProject.entities.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.etiya.ReCapProject.entities.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.etiya.ReCapProject.entities.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private CorporateCustomerDao corporateCustomerDao;
	private ModelMapper modelMapper;

	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao,
			ModelMapper modelMapper) {
		super();
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<CorporateCustomer>> findAll() {
		return new SuccessDataResult<List<CorporateCustomer>>(this.corporateCustomerDao.findAll(),
				Messages.CORPORATE_CUSTOMERS + Messages.LIST);
	}

	@Override
	public DataResult<List<CorporateCustomerDto>> getAll() {
		List<CorporateCustomer> corporateCustomers = this.corporateCustomerDao.findAll();
		List<CorporateCustomerDto> corporateCustomersDto = new ArrayList<CorporateCustomerDto>();

		for (CorporateCustomer corporateCustomer : corporateCustomers) {
			CorporateCustomerDto mappedCorporateCustomer = modelMapper.map(corporateCustomer,
					CorporateCustomerDto.class);

			corporateCustomersDto.add(mappedCorporateCustomer);
		}
		return new SuccessDataResult<List<CorporateCustomerDto>>(corporateCustomersDto,
				Messages.CORPORATE_CUSTOMERS + Messages.LIST);
	}

	@Override
	public DataResult<CorporateCustomer> findById(int corporateCustomerId) {
		return new SuccessDataResult<CorporateCustomer>(this.corporateCustomerDao.getById(corporateCustomerId),
				Messages.CORPORATE_CUSTOMER + Messages.LIST);
	}

	@Override
	public DataResult<CorporateCustomerDto> getById(int corporateCustomerId) {
		CorporateCustomerDto mappedCorporateCustomer = modelMapper
				.map(this.corporateCustomerDao.getById(corporateCustomerId), CorporateCustomerDto.class);

		return new SuccessDataResult<CorporateCustomerDto>(mappedCorporateCustomer,
				Messages.CORPORATE_CUSTOMER + Messages.LIST);
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {

		CorporateCustomer corporateCustomer = modelMapper.map(createCorporateCustomerRequest, CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.CORPORATE_CUSTOMER + Messages.ADD);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {

		CorporateCustomer corporateCustomer = modelMapper.map(updateCorporateCustomerRequest, CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.CORPORATE_CUSTOMER + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {

		CorporateCustomer corporateCustomer = this.corporateCustomerDao
				.getById(deleteCorporateCustomerRequest.getCorporateCustomerId());

		this.corporateCustomerDao.delete(corporateCustomer);
		return new SuccessResult(Messages.CORPORATE_CUSTOMER + Messages.DELETE);
	}

	@Override
	public Result existsByUserId(int applicationUserId) {

		if (this.corporateCustomerDao.existsByApplicationUser_UserId(applicationUserId)) {
			return new SuccessResult();
		}
		return new ErrorResult();
	}

	@Override
	public DataResult<CorporateCustomer> getByApplicationUser_UserId(int applicationUserId) {
		return new SuccessDataResult<CorporateCustomer>(
				this.corporateCustomerDao.getByApplicationUser_UserId(applicationUserId));
	}
}
