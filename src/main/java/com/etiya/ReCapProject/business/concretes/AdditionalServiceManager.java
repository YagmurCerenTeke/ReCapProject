package com.etiya.ReCapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
	private ModelMapper modelMapper;

	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapper modelMapper) {
		super();
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<AdditionalService>> findAll() {
		return new SuccessDataResult<List<AdditionalService>>(this.additionalServiceDao.findAll(),
				Messages.ADDITIONAL_SERVICES + Messages.LIST);
	}

	@Override
	public DataResult<List<AdditionalServiceDto>> getAll() {
		return new SuccessDataResult<List<AdditionalServiceDto>>(
				this.additionalServiceDao.findAll().stream().map(additionalService -> 
				modelMapper.map(additionalService, AdditionalServiceDto.class)).collect(Collectors.toList()),
				Messages.ADDITIONAL_SERVICES + Messages.LIST);
	}

	@Override
	public DataResult<AdditionalService> findById(int additionalServiceId) {
		return new SuccessDataResult<AdditionalService>(this.additionalServiceDao.getById(additionalServiceId),
				Messages.ADDITIONAL_SERVICE + Messages.LIST);
	}

	@Override
	public DataResult<AdditionalServiceDto> getById(int additionalServiceId) {
		return new SuccessDataResult<AdditionalServiceDto>(modelMapper.map(this.additionalServiceDao.
				getById(additionalServiceId), AdditionalServiceDto.class),
				Messages.ADDITIONAL_SERVICE + Messages.LIST);
	}
	
//	@Override
//	public DataResult<List<AdditionalServiceDto>> getAdditionalServiceRequests(boolean selected) {
//		return new SuccessDataResult<List<AdditionalServiceDto>>(this.additionalServiceDao.getAdditionalServiceRequests(selected).getData(),
//				Messages.BRAND + Messages.LIST);
//	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		var result = BusinessRules
				.run(this.checkIfAdditionalServiceNameExists(createAdditionalServiceRequest.getAdditionalServiceName()));

		if (result != null) {
			return result;
		}

		AdditionalService additionalService = modelMapper.map(createAdditionalServiceRequest, AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		return new SuccessResult(Messages.ADDITIONAL_SERVICE + Messages.ADD);

	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {

		AdditionalService additionalService = this.additionalServiceDao
				.getById(updateAdditionalServiceRequest.getAdditionalServiceId());
		additionalService.setAdditionalServiceName("");
		this.additionalServiceDao.save(additionalService);

		var result = BusinessRules
				.run(this.checkIfAdditionalServiceNameExists(updateAdditionalServiceRequest.getAdditionalServiceName()));

		if (result != null) {
			return result;
		}
		
		additionalService = modelMapper.map(updateAdditionalServiceRequest, AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		return new SuccessResult(Messages.ADDITIONAL_SERVICE + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		AdditionalService additionalService = this.additionalServiceDao
				.getById(deleteAdditionalServiceRequest.getAdditionalServiceId());

		this.additionalServiceDao.delete(additionalService);
		return new SuccessResult(Messages.ADDITIONAL_SERVICE + Messages.DELETE);
	}

	private Result checkIfAdditionalServiceNameExists(String additionalServiceName) {
		if (this.additionalServiceDao.existsByAdditionalServiceName(additionalServiceName)) {
			return new ErrorResult(Messages.ADDITIONAL_SERVICE + Messages.EXISTS);
		}
		return new SuccessResult();
	}

}
