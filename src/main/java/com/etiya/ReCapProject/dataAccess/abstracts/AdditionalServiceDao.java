package com.etiya.ReCapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.entities.concretes.AdditionalService;
import com.etiya.ReCapProject.entities.dto.AdditionalServiceDto;

public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer> {

	boolean existsByAdditionalServiceName(String name);
	
	@Query("Select new com.etiya.ReCapProject.entities.dto.AdditionalServiceDto"
			+ "(a.additionalServiceId, a.additionalServiceName, selected) " 
			+ 	"From AdditionalService a where selected = :selected")
	DataResult<List<AdditionalServiceDto>> getAdditionalServiceRequests(boolean selected);
}

