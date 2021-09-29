package com.etiya.ReCapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.etiya.ReCapProject.entities.concretes.Rental;
import com.etiya.ReCapProject.entities.dto.RentalDetailDto;

@Repository
public interface RentalDao extends JpaRepository<Rental, Integer> {

	@Query("Select new com.etiya.ReCapProject.entities.dto.RentalDetailDto"
			+ "(c.carId, r.returnDate) " 
			+ 	"From Car c Inner Join c.rentals r where c.carId=:carId and r.returnDate is null")
	RentalDetailDto getByCarIdWhereReturnDateIsNull(int carId);
	
}
