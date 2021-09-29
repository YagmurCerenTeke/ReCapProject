package com.etiya.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.ColorService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.*;
import com.etiya.ReCapProject.dataAccess.abstracts.ColorDao;
import com.etiya.ReCapProject.entities.concretes.Color;
import com.etiya.ReCapProject.entities.requests.colorRequests.UpdateColorRequest;
import com.etiya.ReCapProject.entities.requests.colorRequests.CreateColorRequest;
import com.etiya.ReCapProject.entities.requests.colorRequests.DeleteColorRequest;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;

	@Autowired
	public ColorManager(ColorDao colorDao) {
		super();
		this.colorDao = colorDao;
	}

	@Override
	public DataResult<List<Color>> getAll() {
		return new SuccessDataResult<List<Color>>(this.colorDao.findAll(), Messages.COLORS + Messages.LIST);
	}

	@Override
	public DataResult<Color> getById(int colorId) {
		return new SuccessDataResult<Color>(this.colorDao.getById(colorId), Messages.COLOR + Messages.LIST);
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) {

		var result = BusinessRules.run(this.checkIfColorNameExists(createColorRequest.getColorName()));

		if (result != null) {
			return result;
		}
		
		Color color = new Color();
		color.setColorName(createColorRequest.getColorName());

		this.colorDao.save(color);
		return new SuccessResult(Messages.COLOR + Messages.ADD);

	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {

		var result = BusinessRules.run(this.checkIfColorNameExists(updateColorRequest.getColorName()));

		if (result != null) {
			return result;
		}
		
		Color color = this.colorDao.getById(updateColorRequest.getColorId());
		color.setColorName(updateColorRequest.getColorName());

		this.colorDao.save(color);
		return new SuccessResult(Messages.COLOR + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) {

		Color color = this.colorDao.getById(deleteColorRequest.getColorId());

		this.colorDao.delete(color);
		return new SuccessResult(Messages.COLOR + Messages.DELETE);
	}
	
	private Result checkIfColorNameExists(String colorName) {
		if (this.colorDao.existsByColorName(colorName)) {
			return new ErrorResult(Messages.COLOR + Messages.EXISTS);
		}
		return new SuccessResult();
	}
}
