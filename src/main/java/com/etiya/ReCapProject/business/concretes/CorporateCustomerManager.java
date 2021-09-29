package com.etiya.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.CorporateCustomerService;
import com.etiya.ReCapProject.business.abstracts.UserService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.CorporateCustomerDao;
import com.etiya.ReCapProject.entities.concretes.ApplicationUser;
import com.etiya.ReCapProject.entities.concretes.CorporateCustomer;
import com.etiya.ReCapProject.entities.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.etiya.ReCapProject.entities.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.etiya.ReCapProject.entities.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private CorporateCustomerDao corporateCustomerDao;
	private UserService userService;

	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, UserService userService) {
		super();
		this.corporateCustomerDao = corporateCustomerDao;
		this.userService = userService;
	}

	@Override
	public DataResult<List<CorporateCustomer>> getAll() {
		return new SuccessDataResult<List<CorporateCustomer>>(this.corporateCustomerDao.findAll(),
				Messages.CORPORATECUSTOMERS + Messages.LIST);
	}

	@Override
	public DataResult<CorporateCustomer> getById(int corporateCustomerId) {
		return new SuccessDataResult<CorporateCustomer>(this.corporateCustomerDao.getById(corporateCustomerId),
				Messages.CORPORATECUSTOMER + Messages.LIST);
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		
		ApplicationUser applicationUser = this.userService.getById(createCorporateCustomerRequest.getUserId())
				.getData();

		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setCompanyName(createCorporateCustomerRequest.getCompanyName());
		corporateCustomer.setTaxNumber(createCorporateCustomerRequest.getTaxNumber());

		corporateCustomer.setApplicationUser(applicationUser);

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.CORPORATECUSTOMER + Messages.ADD);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		
		ApplicationUser applicationUser = this.userService.getById(updateCorporateCustomerRequest.getUserId())
				.getData();

		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setCompanyName(updateCorporateCustomerRequest.getCompanyName());
		corporateCustomer.setTaxNumber(updateCorporateCustomerRequest.getTaxNumber());

		corporateCustomer.setApplicationUser(applicationUser);

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.CORPORATECUSTOMER + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao
				.getById(deleteCorporateCustomerRequest.getCorporateCustomerId());

		this.corporateCustomerDao.delete(corporateCustomer);
		return new SuccessResult(Messages.CORPORATECUSTOMER + Messages.DELETE);
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
