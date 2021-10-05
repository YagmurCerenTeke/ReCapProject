package com.etiya.ReCapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.ReCapProject.entities.concretes.Car;
import com.etiya.ReCapProject.entities.dto.CarDetailDto;

public interface CarDao extends JpaRepository<Car, Integer> {
	
	@Query("Select new com.etiya.ReCapProject.entities.dto.CarDetailDto"
			+ "(c.id, c.carName, b.brandName, cl.colorName, c.dailyPrice ) " 
			+ 	"From Brand b Inner Join b.cars c Inner Join c.color cl ")
	List<CarDetailDto> getCarsWithDetails();

//	@Query("Select new com.etiya.ReCapProject.entities.dto.CarDetailWithImagesDto"
//			+ "(c.id, c.carName, b.brandName, cl.colorName, c.dailyPrice, ci.imagePath) " 
//			+ 	"From Brand b Inner Join b.cars c Inner Join c.color cl Inner Join c.carImages ci"
//			+ " Where c.carId=:carId")
//	List<CarDetailWithImagesDto> getCarDetailsByCarId(int carId);
	
	List<Car> getByColor_ColorId(int colorId);

	List<Car> getByBrand_BrandId(int brandId);
	
	List<Car> findByInRepairFalse();
	
	List<Car> getByCity(String city);

}
