package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.Repair;
import com.etiya.ReCapProject.entities.dto.RepairDto;
import com.etiya.ReCapProject.entities.requests.repairRequest.DeleteRepairRequest;
import com.etiya.ReCapProject.entities.requests.repairRequest.CreateRepairRequest;
import com.etiya.ReCapProject.entities.requests.repairRequest.UpdateRepairRequest;

public interface RepairService {
	
	DataResult<List<Repair>> findAll();

	DataResult<List<RepairDto>> getAll();
	
	Result insert(CreateRepairRequest createRepairRequest);
	
	Result update(UpdateRepairRequest updateRepairRequest);

	Result delete(DeleteRepairRequest deleteRepairRequest);
	
}
