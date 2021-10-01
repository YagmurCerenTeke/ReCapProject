package com.etiya.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.UserService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.ApplicationUserDao;
import com.etiya.ReCapProject.entities.concretes.ApplicationUser;
import com.etiya.ReCapProject.entities.requests.applicationUserRequests.CreateApplicationUserRequest;
import com.etiya.ReCapProject.entities.requests.applicationUserRequests.DeleteApplicationUserRequest;
import com.etiya.ReCapProject.entities.requests.applicationUserRequests.UpdateApplicationUserRequest;

@Service
public class UserManager implements UserService {

	private ApplicationUserDao applicationUserDao;

	@Autowired
	public UserManager(ApplicationUserDao applicationUserDao) {
		super();
		this.applicationUserDao = applicationUserDao;
	}

	@Override
	public DataResult<List<ApplicationUser>> getAll() {
		return new SuccessDataResult<List<ApplicationUser>>(this.applicationUserDao.findAll(), Messages.USERS + Messages.LIST);
	}
	
	@Override
	public DataResult<ApplicationUser> getById(int applicationUserId) {
		return new SuccessDataResult<ApplicationUser>(this.applicationUserDao.getById(applicationUserId), Messages.USER + Messages.LIST);
	}

	@Override
	public DataResult<List<String>> findAllEmail() {
		return new SuccessDataResult<List<String>>(this.applicationUserDao.findAllEmail(),
				Messages.EMAILS + Messages.LIST);
	}
	
	@Override
	public Result add(ApplicationUser applicationUser) {
		var result = BusinessRules.run(checkIfEmailExists(applicationUser.getEmail()));

		if (result != null) {
			return result;
		}
		ApplicationUser appUser = new ApplicationUser();
		appUser.setEmail(applicationUser.getEmail());
		appUser.setPassword(applicationUser.getPassword());

		this.applicationUserDao.save(applicationUser);
		return new SuccessResult(Messages.USER + Messages.ADD);
	}
	
	@Override
	public Result add(CreateApplicationUserRequest createApplicationUserRequest) {
		var result = BusinessRules.run(checkIfEmailExists(createApplicationUserRequest.getEmail()));

		if (result != null) {
			return result;
		}
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setEmail(createApplicationUserRequest.getEmail());
		applicationUser.setPassword(createApplicationUserRequest.getPassword());

		this.applicationUserDao.save(applicationUser);
		return new SuccessResult(Messages.USER + Messages.ADD);
	}

	@Override
	public Result update(UpdateApplicationUserRequest updateApplicationUserRequest) {
		
		
//		AdditionalService additionalService = this.additionalServiceDao
//				.getById(updateAdditionalServiceRequest.getAdditionalServiceId());
//		additionalService.setAdditionalServiceName("");
//		this.additionalServiceDao.save(additionalService);

		ApplicationUser applicationUser = this.applicationUserDao.getById(updateApplicationUserRequest.getUserId());
		applicationUser.setEmail(updateApplicationUserRequest.getEmail());
		applicationUser.setPassword(updateApplicationUserRequest.getPassword());

		this.applicationUserDao.save(applicationUser);
		return new SuccessResult(Messages.USER + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteApplicationUserRequest deleteApplicationUserRequest) {

		ApplicationUser applicationUser = this.applicationUserDao.getById(deleteApplicationUserRequest.getUserId());

		this.applicationUserDao.delete(applicationUser);
		return new SuccessResult(Messages.USER + Messages.DELETE);
	}

	@Override
	public Result getPasswordByEmail(String email) {
		return new SuccessResult(this.applicationUserDao.getPasswordByEmail(email));
	}
	
	private Result checkIfEmailExists(String newEmail) {
		List<String> emails = this.applicationUserDao.findAllEmail();
		for (String email : emails) {
			System.out.println(email);
			if(newEmail.equals(email)) {
				return new ErrorResult(Messages.EMAILEXISTS);
			}
		}
		return new SuccessResult();
	}


}
