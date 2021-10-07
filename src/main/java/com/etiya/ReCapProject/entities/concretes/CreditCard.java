package com.etiya.ReCapProject.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit_cards")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class CreditCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "credit_card_id")
	private int creditCardId;
	
	@Column(name = "name_on_card")
	private String nameOnCard;
	
	@Column(name = "credit_card_number")
	private String creditCardNumber;
	
	@Column(name = "expiration_date")
	private String expirationDate;
	
	@Column(name = "cvc")
	private String cvc;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private ApplicationUser applicationUser;

}
