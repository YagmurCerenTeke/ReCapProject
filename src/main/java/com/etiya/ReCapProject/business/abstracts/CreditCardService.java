package com.etiya.ReCapProject.business.abstracts;

import java.util.List;

import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.CreditCard;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.CreateCreditCardRequest;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.DeleteCreditCardRequest;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.UpdateCreditCardRequest;

public interface CreditCardService {
	
	DataResult<List<CreditCard>> getAll();

	DataResult<CreditCard> getById(int cardInformationId); 
	
	DataResult<List<CreditCard>> getCreditCardsByApplicationUser_UserId(int applicationUserId);

	Result add(CreateCreditCardRequest createCreditCardRequest);

	Result update(UpdateCreditCardRequest updateCreditCardRequest);

	Result delete(DeleteCreditCardRequest deleteCreditCardRequest);
	
	Result checkCreditCardFormat(String creditCardNumber, String cvc,String expirationDate);
}
