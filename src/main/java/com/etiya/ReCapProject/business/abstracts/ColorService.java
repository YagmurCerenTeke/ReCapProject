package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.*;
import com.etiya.ReCapProject.entities.concretes.Color;
import com.etiya.ReCapProject.entities.requests.colorRequests.UpdateColorRequest;
import com.etiya.ReCapProject.entities.requests.colorRequests.CreateColorRequest;
import com.etiya.ReCapProject.entities.requests.colorRequests.DeleteColorRequest;

public interface ColorService {
	DataResult<List<Color>> getAll();

	DataResult<Color> getById(int colorId);

	Result add(CreateColorRequest createColorRequest);

	Result update(UpdateColorRequest updateColorRequest);

	Result delete(DeleteColorRequest deleteColorRequest);
}
