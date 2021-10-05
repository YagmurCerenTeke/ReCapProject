package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.ApplicationUser;
import com.etiya.ReCapProject.entities.dto.ApplicationUserDto;
import com.etiya.ReCapProject.entities.requests.applicationUserRequests.CreateApplicationUserRequest;
import com.etiya.ReCapProject.entities.requests.applicationUserRequests.DeleteApplicationUserRequest;
import com.etiya.ReCapProject.entities.requests.applicationUserRequests.UpdateApplicationUserRequest;

public interface UserService {
	
	DataResult<List<ApplicationUser>> findAll();
	
	DataResult<List<ApplicationUserDto>> getAll();
	
	DataResult<ApplicationUser> findById(int applicationUserId);
	
	DataResult<ApplicationUserDto> getById(int applicationUserId);
	
	DataResult<List<String>> findAllEmail();
	
	Result add(ApplicationUser applicationUser);
	
	Result add(CreateApplicationUserRequest createApplicationUserRequest);

	Result update(UpdateApplicationUserRequest updateApplicationUserRequest);

	Result delete(DeleteApplicationUserRequest deleteApplicationUserRequest);
	
	Result getPasswordByEmail(String email);
	
}
