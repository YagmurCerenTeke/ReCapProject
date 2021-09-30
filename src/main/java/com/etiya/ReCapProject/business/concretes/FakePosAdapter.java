package com.etiya.ReCapProject.business.concretes;

import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.FakePosService;
import com.etiya.ReCapProject.core.webServices.PosService;

@Service
public class FakePosAdapter implements FakePosService{

	@Override
	public boolean fakePosService(String nameOnCard, String creditCardNumber, String expirationDate, String cvc) {
		PosService posService = new PosService();
		return posService.fakePosService(nameOnCard, creditCardNumber, expirationDate, cvc);
	}

}
