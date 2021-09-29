package com.etiya.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.CarService;
import com.etiya.ReCapProject.business.abstracts.CorporateCustomerService;
import com.etiya.ReCapProject.business.abstracts.CreditCardService;
import com.etiya.ReCapProject.business.abstracts.FindeksScoreService;
import com.etiya.ReCapProject.business.abstracts.IndividualCustomerService;
import com.etiya.ReCapProject.business.abstracts.RentalService;
import com.etiya.ReCapProject.business.abstracts.UserService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.RentalDao;
import com.etiya.ReCapProject.entities.concretes.ApplicationUser;
import com.etiya.ReCapProject.entities.concretes.Car;
import com.etiya.ReCapProject.entities.concretes.CorporateCustomer;
import com.etiya.ReCapProject.entities.concretes.CreditCard;
import com.etiya.ReCapProject.entities.concretes.IndividualCustomer;
import com.etiya.ReCapProject.entities.concretes.Rental;
import com.etiya.ReCapProject.entities.dto.RentalDetailDto;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.CreateCreditCardRequest;
import com.etiya.ReCapProject.entities.requests.rentalRequests.CreateRentalRequest;
import com.etiya.ReCapProject.entities.requests.rentalRequests.DeleteRentalRequest;
import com.etiya.ReCapProject.entities.requests.rentalRequests.UpdateRentalRequest;

@Service
public class RentalManager implements RentalService {

	private RentalDao rentalDao;
	private CarService carService;
	private UserService userService;
	private CorporateCustomerService corporateCustomerService;
	private IndividualCustomerService individualCustomerService;
	private FindeksScoreService findeksScoreService;
	private CreditCardService creditCardService;

	@Autowired
	public RentalManager(RentalDao rentalDao, CarService carService, UserService userService,
			CorporateCustomerService corporateCustomerService, IndividualCustomerService individualCustomerService,
			FindeksScoreService findeksScoreService, CreditCardService creditCardService) {
		super();
		this.rentalDao = rentalDao;
		this.carService = carService;
		this.userService = userService;
		this.corporateCustomerService = corporateCustomerService;
		this.individualCustomerService = individualCustomerService;
		this.findeksScoreService = findeksScoreService;
		this.creditCardService = creditCardService;
	}

	@Override
	public DataResult<List<Rental>> getAll() {
		return new SuccessDataResult<List<Rental>>(this.rentalDao.findAll(), Messages.RENTALS + Messages.LIST);
	}

	@Override
	public DataResult<Rental> getById(int rentalId) {
		return new SuccessDataResult<Rental>(rentalDao.getById(rentalId), Messages.RENTAL + Messages.LIST);
	}

	@Override
	public Result insert(CreateRentalRequest createRentalRequest, CreateCreditCardRequest createCreditCardRequest) {
		var result = BusinessRules.run(checkIfCarInRepair(createRentalRequest),
				getByCarIdWhereReturnDateIsNull(createRentalRequest.getCarId()),
				checkCustomerFindeksScoreForCar(createRentalRequest.getUserId(), createRentalRequest.getCarId()),
				checkRentCity(createRentalRequest.getRentCity(),
						this.carService.getById(createRentalRequest.getCarId()).getData().getCity()),
				checkReturnCity(createRentalRequest.getReturnCity(),
						this.carService.getById(createRentalRequest.getCarId()).getData().getCity(),
						createRentalRequest.getCarId()));

		if (result != null) {
			return result;
		}

		return this.checkToSaveCreditCard(createRentalRequest, createCreditCardRequest);
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		Car car = this.carService.getById(updateRentalRequest.getCarId()).getData();

		if(updateRentalRequest.getReturnKm()>updateRentalRequest.getRentKm()) {
			car.setKm(updateRentalRequest.getReturnKm());
		}
		
		ApplicationUser applicationUser = this.userService.getById(updateRentalRequest.getUserId()).getData();

		Rental rental = rentalDao.getById(updateRentalRequest.getRentalId());
		rental.setCar(car);
		rental.setApplicationUser(applicationUser);
		rental.setRentDate(updateRentalRequest.getRentDate());
		rental.setReturnDate(updateRentalRequest.getReturnDate());

		this.rentalDao.save(rental);
		//this.getInvoiceIfCarIsReturned(rental.getRentalId(), updateRentalRequest.getReturnDate());
		return new SuccessResult(Messages.RENTAL + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		this.rentalDao.delete(rentalDao.getById(deleteRentalRequest.getRentalId()));
		return new SuccessResult(Messages.RENTAL + Messages.DELETE);
	}

	@Override
	public Result getByCarIdWhereReturnDateIsNull(int carId) {
		RentalDetailDto rentalDetailsDto = this.rentalDao.getByCarIdWhereReturnDateIsNull(carId);
		if (rentalDetailsDto != null) {
			return new ErrorResult(Messages.CAR + Messages.NOTAVAILABLE);
		}
		return new SuccessResult();
	}

	private Result checkCustomerFindeksScoreForCar(int applicationUserId, int carId) {

		if (this.individualCustomerService.existsByUserId(applicationUserId).isSuccess()) {
			IndividualCustomer individualCustomer = this.individualCustomerService
					.getByApplicationUser_UserId(applicationUserId).getData();

			if (this.carService.getById(carId).getData().getMinFindeksScore() > this.findeksScoreService
					.getIndividualFindeksScore(individualCustomer.getIdentityNumber())) {

				return new ErrorResult(Messages.FINDEKSSCORENOTENOUGH);
			}

		}

		if (this.corporateCustomerService.existsByUserId(applicationUserId).isSuccess()) {

			CorporateCustomer corporateCustomer = this.corporateCustomerService
					.getByApplicationUser_UserId(applicationUserId).getData();

			if (this.carService.getById(carId).getData().getMinFindeksScore() > this.findeksScoreService
					.getCorporateFindeksScore(corporateCustomer.getTaxNumber())) {

				return new ErrorResult(Messages.FINDEKSSCORENOTENOUGH);
			}
		}

		return new SuccessResult();

	}

	private Result insertRental(CreateRentalRequest createRentalRequest) {

		Car car = this.carService.getById(createRentalRequest.getCarId()).getData();

		if(createRentalRequest.getReturnKm()>createRentalRequest.getRentKm()) {
			car.setKm(createRentalRequest.getReturnKm());
		}
		
		ApplicationUser user = new ApplicationUser();
		user.setUserId(createRentalRequest.getUserId());

		Rental rental = new Rental();
		rental.setCar(car);
		rental.setApplicationUser(user);
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setReturnDate(createRentalRequest.getReturnDate());
		rental.setRentCity(createRentalRequest.getRentCity());
		rental.setReturnCity(createRentalRequest.getReturnCity());

		this.rentalDao.save(rental);
		//this.getInvoiceIfCarIsReturned(rentalCheck.getRentalId(), createRentalRequest.getReturnDate());
		return new SuccessResult(Messages.RENTAL + Messages.ADD);
	}

	private DataResult<List<CreditCard>> checkToSaveCreditCard(CreateRentalRequest createRentalRequest,
			CreateCreditCardRequest createCreditCardRequest) {

		List<CreditCard> creditCards = creditCardService
				.getCreditCardsByApplicationUser_UserId(createRentalRequest.getUserId()).getData();
		this.insertRental(createRentalRequest);
		if (!creditCards.isEmpty()) {

			return new SuccessDataResult<List<CreditCard>>(creditCards, Messages.RENTAL + Messages.ADD);

		} else {
			this.saveCreditCard(createRentalRequest.isSaveCreditCard(), createCreditCardRequest);
			return new SuccessDataResult<List<CreditCard>>(Messages.RENTAL + Messages.ADD);
		}
	}

	private void saveCreditCard(boolean isSaveCreditCard, CreateCreditCardRequest createCreditCardRequest) {
		if (isSaveCreditCard) {
			this.creditCardService.add(createCreditCardRequest);
		}
	}

	private Result checkIfCarInRepair(CreateRentalRequest createRentalRequest) {
		if (carService.getById(createRentalRequest.getCarId()).getData().isInRepair()) {
			return new ErrorResult(Messages.CARINREPAIR);
		}
		return new SuccessResult();
	}

	private Result checkRentCity(String rentCity, String carCity) {
		if (!rentCity.equals(carCity)) {
			return new ErrorResult(Messages.CARINANOTHERCITY);
		}
		return new SuccessResult();
	}

	private Result checkReturnCity(String returnCity, String carCity, int carId) {
		if (!returnCity.equals(carCity)) {
			this.carService.getById(carId).getData().setCity(returnCity);
			return new SuccessResult();
		}
		return new SuccessResult();
	}

//	private Result getInvoiceIfCarIsReturned(int rentalId, String returnDate) {
//
//		if (returnDate == null) {
//			this.rentalDao.getById(rentalId).setReturned(true);
//			return new SuccessResult();
//			
//		} else {
//			this.rentalDao.getById(rentalId).setReturned(false);
//			return new SuccessResult();
//		}
//	}

}