package com.etiya.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.IndividualCustomerService;
import com.etiya.ReCapProject.business.abstracts.UserService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.IndividualCustomerDao;
import com.etiya.ReCapProject.entities.concretes.ApplicationUser;
import com.etiya.ReCapProject.entities.concretes.IndividualCustomer;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.etiya.ReCapProject.entities.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private IndividualCustomerDao individualCustomerDao;
	private UserService userService;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, UserService userService) {
		super();
		this.individualCustomerDao = individualCustomerDao;
		this.userService = userService;
	}

	@Override
	public DataResult<List<IndividualCustomer>> getAll() {
		return new SuccessDataResult<List<IndividualCustomer>>(this.individualCustomerDao.findAll(),
				Messages.INDIVIDUALCUSTOMERS + Messages.LIST);
	}

	@Override
	public DataResult<IndividualCustomer> getById(int individualCustomerId) {
		return new SuccessDataResult<IndividualCustomer>(this.individualCustomerDao.getById(individualCustomerId),
				Messages.INDIVIDUALCUSTOMER + Messages.LIST);
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

		ApplicationUser applicationUser = this.userService.getById(createIndividualCustomerRequest.getUserId())
				.getData();

		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setFirstName(createIndividualCustomerRequest.getFirstName());
		individualCustomer.setLastName(createIndividualCustomerRequest.getLastName());
		individualCustomer.setIdentityNumber(createIndividualCustomerRequest.getNationalIdentityNumber());

		individualCustomer.setApplicationUser(applicationUser);

		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(Messages.INDIVIDUALCUSTOMER + Messages.ADD);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

		ApplicationUser applicationUser = this.userService.getById(updateIndividualCustomerRequest.getUserId())
				.getData();

		IndividualCustomer individualCustomer = this.individualCustomerDao
				.getById(updateIndividualCustomerRequest.getIndividualCustomerId());
		individualCustomer.setFirstName(updateIndividualCustomerRequest.getFirstName());
		individualCustomer.setLastName(updateIndividualCustomerRequest.getLastName());
		individualCustomer.setIdentityNumber(updateIndividualCustomerRequest.getNationalIdentityNumber());

		individualCustomer.setApplicationUser(applicationUser);

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
