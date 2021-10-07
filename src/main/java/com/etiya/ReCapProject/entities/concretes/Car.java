package com.etiya.ReCapProject.entities.concretes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler","rentals"})
@Table(name = "cars")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_id")
	private int carId;

	@Column(name = "car_name")
	private String carName;

	@Column(name = "model_year")
	private String modelYear;

	@Column(name = "daily_price")
	private double dailyPrice;

	@Column(name = "description")
	private String description;
	
	@Column(name = "min_findeks_score")
	private int minFindeksScore;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "km")
	private int km;
	
	@Column(name = "is_in_repair" , columnDefinition = "boolean default false")
	private boolean inRepair;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "color_id")
	private Color color;
	
	@OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CarImage> carImages;
	
	@JsonIgnore
	@OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Rental> rentals;
	
	@JsonIgnore
	@OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Repair> repairs;
	
	@OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CarDamage> carDamages;
}
