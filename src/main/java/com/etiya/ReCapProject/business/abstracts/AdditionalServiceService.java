package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.AdditionalService;
import com.etiya.ReCapProject.entities.dto.AdditionalServiceDto;
import com.etiya.ReCapProject.entities.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.etiya.ReCapProject.entities.requests.additionalServiceRequests.DeleteAdditionalServiceRequest;
import com.etiya.ReCapProject.entities.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;

public interface AdditionalServiceService {

	DataResult<List<AdditionalService>> findAll();

	DataResult<List<AdditionalServiceDto>> getAll();

	DataResult<AdditionalService> findById(int additionalServiceId);

	DataResult<AdditionalServiceDto> getById(int additionalServiceId);

//	DataResult<List<AdditionalServiceDto>> getAdditionalServiceRequests(boolean selected);

	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest);

	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);

	Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest);
}
