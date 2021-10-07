package com.etiya.ReCapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
import com.etiya.ReCapProject.entities.dto.ApplicationUserDto;
import com.etiya.ReCapProject.entities.requests.applicationUserRequests.CreateApplicationUserRequest;
import com.etiya.ReCapProject.entities.requests.applicationUserRequests.DeleteApplicationUserRequest;
import com.etiya.ReCapProject.entities.requests.applicationUserRequests.UpdateApplicationUserRequest;

@Service
public class UserManager implements UserService {

	private ApplicationUserDao applicationUserDao;
	private ModelMapper modelMapper;

	@Autowired
	public UserManager(ApplicationUserDao applicationUserDao, ModelMapper modelMapper) {
		super();
		this.applicationUserDao = applicationUserDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<ApplicationUser>> findAll() {
		return new SuccessDataResult<List<ApplicationUser>>(this.applicationUserDao.findAll(),
				Messages.USERS + Messages.LIST);
	}

	@Override
	public DataResult<List<ApplicationUserDto>> getAll() {
		List<ApplicationUser> applicationUsers = this.applicationUserDao.findAll();
		List<ApplicationUserDto> applicationUsersDto = applicationUsers.stream().map(applicationUser -> modelMapper.map(applicationUser, ApplicationUserDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ApplicationUserDto>>(applicationUsersDto ,
				Messages.USERS + Messages.LIST);
	}

	@Override
	public DataResult<ApplicationUser> findById(int applicationUserId) {
		return new SuccessDataResult<ApplicationUser>(this.applicationUserDao.getById(applicationUserId),
				Messages.USER + Messages.LIST);
	}

	@Override
	public DataResult<ApplicationUserDto> getById(int applicationUserId) {
		ApplicationUser applicationUser = this.applicationUserDao.getById(applicationUserId);
		return new SuccessDataResult<ApplicationUserDto>(modelMapper.map(applicationUser, ApplicationUserDto.class),
				Messages.USER + Messages.LIST);
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

		this.applicationUserDao.save(applicationUser);
		return new SuccessResult(Messages.USER + Messages.ADD);
	}

	@Override
	public Result add(CreateApplicationUserRequest createApplicationUserRequest) {
		var result = BusinessRules.run(checkIfEmailExists(createApplicationUserRequest.getEmail()));

		if (result != null) {
			return result;
		}
		
		ApplicationUser applicationUser = modelMapper.map(createApplicationUserRequest, ApplicationUser.class);

		this.applicationUserDao.save(applicationUser);
		return new SuccessResult(Messages.USER + Messages.ADD);
	}

	@Override
	public Result update(UpdateApplicationUserRequest updateApplicationUserRequest) {

		ApplicationUser applicationUser = this.applicationUserDao
				.getById(updateApplicationUserRequest.getUserId());
		applicationUser.setEmail("");
		this.applicationUserDao.save(applicationUser);

		var result = BusinessRules.run(checkIfEmailExists(updateApplicationUserRequest.getEmail()));

		if (result != null) {
			return result;
		}
		
		ApplicationUser appUser = modelMapper.map(updateApplicationUserRequest, ApplicationUser.class);

		this.applicationUserDao.save(appUser);
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
			if (newEmail.equals(email)) {
				return new ErrorResult(Messages.EMAILEXISTS);
			}
		}
		return new SuccessResult();
	}

}
