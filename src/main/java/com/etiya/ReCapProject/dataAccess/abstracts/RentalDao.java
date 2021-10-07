package com.etiya.ReCapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etiya.ReCapProject.entities.concretes.Rental;

@Repository
public interface RentalDao extends JpaRepository<Rental, Integer> {

//	@Query("Select new com.etiya.ReCapProject.entities.dto.RentalDetailDto"
//			+ "(r.returned) " 
//			+ 	"From Car c Inner Join c.rentals r where c.carId=:carId and r.returned is false")
//	RentalDetailDto getByCarIdWhereIsReturnedFalse(int carId);
	
	List<Rental> getByCar_CarId(int carId);
	
	boolean existsByApplicationUser_UserId(int applicationUserId);
	
}
