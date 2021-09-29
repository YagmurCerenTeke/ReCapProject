package com.etiya.ReCapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.ReCapProject.entities.concretes.CarDamage;

public interface CarDamageDao extends JpaRepository<CarDamage, Integer>{

	List<CarDamage> getByCar_CarId(int carId);
}
