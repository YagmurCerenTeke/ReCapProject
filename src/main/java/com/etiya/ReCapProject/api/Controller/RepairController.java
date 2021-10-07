package com.etiya.ReCapProject.api.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.ReCapProject.business.abstracts.RepairService;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.dto.RepairDto;
import com.etiya.ReCapProject.entities.requests.repairRequest.DeleteRepairRequest;
import com.etiya.ReCapProject.entities.requests.repairRequest.CreateRepairRequest;
import com.etiya.ReCapProject.entities.requests.repairRequest.UpdateRepairRequest;

@RestController
@RequestMapping("api/repairs")
public class RepairController {
	RepairService repairService;

	@Autowired
	public RepairController(RepairService repairService) {
		super();
		this.repairService = repairService;
	}

	@GetMapping("/getAll")
	public DataResult<List<RepairDto>> getAll(){
		return this.repairService.getAll(); 
	}
	
	@PostMapping("/insert")
	public Result insert(@Valid @RequestBody CreateRepairRequest createRepairRequest){
		return this.repairService.insert(createRepairRequest); 
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateRepairRequest updateRepairRequest){
		return this.repairService.update(updateRepairRequest); 
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteRepairRequest deleteRepairRequest){
		return this.repairService.delete(deleteRepairRequest); 
	}	
}
