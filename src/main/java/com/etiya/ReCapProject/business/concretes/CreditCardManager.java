package com.etiya.ReCapProject.business.concretes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.CreditCardService;
import com.etiya.ReCapProject.business.abstracts.UserService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.CreditCardDao;
import com.etiya.ReCapProject.entities.concretes.CreditCard;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.CreateCreditCardRequest;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.DeleteCreditCardRequest;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.UpdateCreditCardRequest;

@Service
public class CreditCardManager implements CreditCardService {

	private CreditCardDao creditCardDao;
	private UserService userService;

	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao, UserService userService) {
		super();
		this.creditCardDao = creditCardDao;
		this.userService = userService;
	}

	@Override
	public DataResult<List<CreditCard>> getAll() {
		return new SuccessDataResult<List<CreditCard>>(this.creditCardDao.findAll(),
				Messages.CREDITCARDS + Messages.LIST);
	}

	@Override
	public DataResult<CreditCard> getById(int creditCardId) {
		return new SuccessDataResult<CreditCard>(this.creditCardDao.getById(creditCardId),
				Messages.CREDITCARD + Messages.LIST);
	}

	@Override
	public DataResult<List<CreditCard>> getCreditCardsByApplicationUser_UserId(int applicationUserId) {
		return new SuccessDataResult<List<CreditCard>>(
				this.creditCardDao.getCreditCardByApplicationUser_UserId(applicationUserId),
				Messages.CREDITCARDS + Messages.LIST);
	}

	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest) {

		var result = BusinessRules.run(checkCreditCardFormat(createCreditCardRequest.getCreditCardNumber(),
				createCreditCardRequest.getCvc(), createCreditCardRequest.getExpirationDate()));

		if (result != null) {
			return result;
		}
		CreditCard creditCard = new CreditCard();
		creditCard.setCreditCardNumber(createCreditCardRequest.getCreditCardNumber());
		creditCard.setApplicationUser(this.userService.findById(createCreditCardRequest.getUserId()).getData());
		creditCard.setCvc(createCreditCardRequest.getCvc());
		creditCard.setExpirationDate(createCreditCardRequest.getExpirationDate());
		creditCard.setNameOnCard(createCreditCardRequest.getNameOnCard());

		this.creditCardDao.save(creditCard);
		return new SuccessResult(Messages.CREDITCARD + Messages.ADD);
	}

	@Override
	public Result update(UpdateCreditCardRequest updateCreditCardRequest) {
		var result = BusinessRules.run(checkCreditCardFormat(updateCreditCardRequest.getCreditCardNumber(),
				updateCreditCardRequest.getCvc(), updateCreditCardRequest.getExpirationDate()));

		if (result != null) {
			return result;
		}
		CreditCard creditCard = creditCardDao.getById(updateCreditCardRequest.getCreditCardId());
		creditCard.setCreditCardNumber(updateCreditCardRequest.getCreditCardNumber());
		creditCard.setApplicationUser(this.userService.findById(updateCreditCardRequest.getUserId()).getData());
		creditCard.setCvc(updateCreditCardRequest.getCvc());
		creditCard.setExpirationDate(updateCreditCardRequest.getExpirationDate());
		creditCard.setNameOnCard(updateCreditCardRequest.getNameOnCard());
		
		this.creditCardDao.save(creditCard);
		return new SuccessResult(Messages.CREDITCARD + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) {
		CreditCard creditCard = creditCardDao.getById(deleteCreditCardRequest.getCreditCardId());
		this.creditCardDao.delete(creditCard);
		return new SuccessResult(Messages.CREDITCARD + Messages.DELETE);
	}

	@Override
	public Result checkCreditCardFormat(String creditCardNumber, String cvc, String expirationDate) {

		var result = BusinessRules.run(checkCreditCardNumber(creditCardNumber), checkCreditCardCvc(cvc),
				checkCreditCardExpirationDate(expirationDate));

		if (result != null) {
			return result;
		}

		return new SuccessResult();

	}

	private Result checkCreditCardNumber(String creditCardNumber) {

		String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" + "(?<mastercard>5[1-5][0-9]{14})|"
				+ "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" + "(?<amex>3[47][0-9]{13})|"
				+ "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" + "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(creditCardNumber);

		if (!matcher.matches()) {
			return new ErrorResult(Messages.CREDITCARDNUMBER);
		}
		return new SuccessResult();
	}

	private Result checkCreditCardCvc(String cvc) {

		String regex = "^[0-9]{3,3}$";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(cvc);

		if (!matcher.matches()) {
			return new ErrorResult(Messages.CREDITCARDCVC);
		}
		return new SuccessResult();
	}

	private Result checkCreditCardExpirationDate(String expirationDate) {

		String regex = "^(0[1-9]|1[0-2])/?(([0-9]{4}|[0-9]{2})$)";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(expirationDate);

		if (!matcher.matches()) {
			return new ErrorResult(Messages.CREDITCARDEXPIRATIONDATE);
		}
		return new SuccessResult();
	}

}
