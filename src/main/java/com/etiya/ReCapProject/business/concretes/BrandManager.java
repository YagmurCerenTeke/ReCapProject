package com.etiya.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.BrandService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.BrandDao;
import com.etiya.ReCapProject.entities.concretes.Brand;
import com.etiya.ReCapProject.entities.requests.brandRequests.CreateBrandRequest;
import com.etiya.ReCapProject.entities.requests.brandRequests.DeleteBrandRequest;
import com.etiya.ReCapProject.entities.requests.brandRequests.UpdateBrandRequest;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;

	@Autowired
	public BrandManager(BrandDao brandDao) {
		super();
		this.brandDao = brandDao;
	}

	@Override
	public DataResult<List<Brand>> getAll() {
		return new SuccessDataResult<List<Brand>>(this.brandDao.findAll(), Messages.BRANDS + Messages.LIST);
	}

	@Override
	public DataResult<Brand> getById(int brandId) {
		return new SuccessDataResult<Brand>(this.brandDao.getById(brandId), Messages.BRAND + Messages.LIST);
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {
		var result = BusinessRules.run(this.checkIfBrandNameExists(createBrandRequest.getBrandName()));

		if (result != null) {
			return result;
		}
		
		Brand brand = new Brand();
		brand.setBrandName(createBrandRequest.getBrandName());

		this.brandDao.save(brand);
		return new SuccessResult(Messages.BRAND + Messages.ADD);

	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		var result = BusinessRules.run(this.checkIfBrandNameExists(updateBrandRequest.getBrandName()));

		if (result != null) {
			return result;
		}
		
		Brand brand = this.brandDao.getById(updateBrandRequest.getBrandId());
		brand.setBrandName(updateBrandRequest.getBrandName());

		this.brandDao.save(brand);
		return new SuccessResult(Messages.BRAND + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		Brand brand = this.brandDao.getById(deleteBrandRequest.getBrandId());
		
		this.brandDao.delete(brand);
		return new SuccessResult(Messages.BRAND + Messages.DELETE);
	}
	
	private Result checkIfBrandNameExists(String brandName) {
		if (this.brandDao.existsByBrandName(brandName)) {
			return new ErrorResult(Messages.BRAND + Messages.EXISTS);
		}
		return new SuccessResult();
	}

}
