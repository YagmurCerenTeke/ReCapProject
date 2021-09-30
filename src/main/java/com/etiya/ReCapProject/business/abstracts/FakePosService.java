package com.etiya.ReCapProject.business.abstracts;


public interface FakePosService {

	boolean fakePosService(String nameOnCard, String creditCardNumber, String expirationDate, String cvc);
}
