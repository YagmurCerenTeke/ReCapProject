package com.etiya.ReCapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.ReCapProject.entities.concretes.Repair;

public interface RepairDao extends JpaRepository<Repair, Integer> {

}
