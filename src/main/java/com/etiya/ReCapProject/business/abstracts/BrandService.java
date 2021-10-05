package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.*;
import com.etiya.ReCapProject.entities.concretes.Brand;
import com.etiya.ReCapProject.entities.dto.BrandDto;
import com.etiya.ReCapProject.entities.requests.brandRequests.CreateBrandRequest;
import com.etiya.ReCapProject.entities.requests.brandRequests.DeleteBrandRequest;
import com.etiya.ReCapProject.entities.requests.brandRequests.UpdateBrandRequest;

public interface BrandService {
	
	DataResult<List<Brand>> findAll();

	DataResult<List<BrandDto>> getAll();
	
	DataResult<Brand> findById(int brandId);
	
	DataResult<BrandDto> getById(int brandId);
	
	Result add(CreateBrandRequest createBrandRequest);

	Result update(UpdateBrandRequest updateBrandRequest);

	Result delete(DeleteBrandRequest deleteBrandRequest);
}
