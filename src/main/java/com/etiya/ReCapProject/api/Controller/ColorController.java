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

import com.etiya.ReCapProject.business.abstracts.ColorService;
import com.etiya.ReCapProject.core.utilities.results.*;
import com.etiya.ReCapProject.entities.dto.ColorDto;
import com.etiya.ReCapProject.entities.requests.colorRequests.UpdateColorRequest;
import com.etiya.ReCapProject.entities.requests.colorRequests.CreateColorRequest;
import com.etiya.ReCapProject.entities.requests.colorRequests.DeleteColorRequest;

@RestController
@RequestMapping("api/colors")
public class ColorController {
	ColorService colorService;

	@Autowired
	public ColorController(ColorService colorService) {
		super();
		this.colorService = colorService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ColorDto>> getAll() {
		return this.colorService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<ColorDto> getById(int colorId) {
		return this.colorService.getById(colorId);
	}
	
	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateColorRequest createColorRequest) {
		return this.colorService.add(createColorRequest);
	}
	
	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateColorRequest updateColorRequest) {
		return this.colorService.update(updateColorRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delte(DeleteColorRequest deleteColorRequest) {
		return this.colorService.delete(deleteColorRequest);
	}
}
