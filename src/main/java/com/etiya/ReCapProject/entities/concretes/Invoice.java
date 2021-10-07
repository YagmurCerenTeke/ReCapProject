package com.etiya.ReCapProject.entities.concretes;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "rental" })
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private int invoiceId;

	@Column(name = "invoice_no")
	private String invoiceNo;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "rental_rent_date")
	private String rentDate;

	@Column(name = "rental_return_date")
	private String returnDate;

	@Column(name = "total_rental_day")
	private long totalRentalDay;

	@Column(name = "amount")
	private Double amount;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "rental_id")
	private Rental rental;
}
