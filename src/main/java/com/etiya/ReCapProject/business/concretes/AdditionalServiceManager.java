package com.etiya.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.AdditionalServiceService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.AdditionalServiceDao;
import com.etiya.ReCapProject.entities.concretes.AdditionalService;
import com.etiya.ReCapProject.entities.dto.AdditionalServiceDto;
import com.etiya.ReCapProject.entities.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.etiya.ReCapProject.entities.requests.additionalServiceRequests.DeleteAdditionalServiceRequest;
import com.etiya.ReCapProject.entities.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;

	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao) {
		super();
		this.additionalServiceDao = additionalServiceDao;
	}

	@Override
	public DataResult<List<AdditionalService>> getAll() {
		return new SuccessDataResult<List<AdditionalService>>(this.additionalServiceDao.findAll(),
				Messages.BRANDS + Messages.LIST);
	}

	@Override
	public DataResult<AdditionalService> getById(int additionalServiceId) {
		return new SuccessDataResult<AdditionalService>(this.additionalServiceDao.getById(additionalServiceId),
				Messages.BRAND + Messages.LIST);
	}

//	@Override
//	public DataResult<List<AdditionalServiceDto>> getAdditionalServiceRequests(boolean selected) {
//		return new SuccessDataResult<List<AdditionalServiceDto>>(this.additionalServiceDao.getAdditionalServiceRequests(selected).getData(),
//				Messages.BRAND + Messages.LIST);
//	}
	
	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		var result = BusinessRules
				.run(this.checkIfAdditionalServiceNameExists(createAdditionalServiceRequest.getName()));

		if (result != null) {
			return result;
		}

		AdditionalService additionalService = new AdditionalService();
		additionalService.setDetails(createAdditionalServiceRequest.getDetails());
		additionalService.setAdditionalServiceName(createAdditionalServiceRequest.getName());
		additionalService.setPrice(createAdditionalServiceRequest.getPrice());
		
		
		this.additionalServiceDao.save(additionalService);
		return new SuccessResult(Messages.BRAND + Messages.ADD);

	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		
		AdditionalService additionalService = this.additionalServiceDao
				.getById(updateAdditionalServiceRequest.getAdditionalServiceId());
		additionalService.setAdditionalServiceName("");
		this.additionalServiceDao.save(additionalService);
		
		var result = BusinessRules.run(
				this.checkIfAdditionalServiceNameExists(updateAdditionalServiceRequest.getName()));

		if (result != null) {
			return result;
		}

		additionalService.setDetails(updateAdditionalServiceRequest.getDetails());
		additionalService.setAdditionalServiceName(updateAdditionalServiceRequest.getName());
		additionalService.setPrice(updateAdditionalServiceRequest.getPrice());

		this.additionalServiceDao.save(additionalService);
		return new SuccessResult(Messages.BRAND + Messages.UPDATE);
	}

	
	
	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		AdditionalService additionalService = this.additionalServiceDao.getById(deleteAdditionalServiceRequest.getAdditionalServiceId());
		
		this.additionalServiceDao.delete(additionalService);
		return new SuccessResult(Messages.BRAND + Messages.DELETE);
	}

	private Result checkIfAdditionalServiceNameExists(String additionalServiceName) {
		if (this.additionalServiceDao.existsByAdditionalServiceName(additionalServiceName)) {
			return new ErrorResult(Messages.ADDITIONAL_SERVICE + Messages.EXISTS);
		}
		return new SuccessResult();
	}

}
