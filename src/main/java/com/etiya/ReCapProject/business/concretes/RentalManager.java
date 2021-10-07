package com.etiya.ReCapProject.business.concretes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.AdditionalServiceService;
import com.etiya.ReCapProject.business.abstracts.CarService;
import com.etiya.ReCapProject.business.abstracts.CorporateCustomerService;
import com.etiya.ReCapProject.business.abstracts.CreditCardService;
import com.etiya.ReCapProject.business.abstracts.FakePosService;
import com.etiya.ReCapProject.business.abstracts.FindeksScoreService;
import com.etiya.ReCapProject.business.abstracts.IndividualCustomerService;
import com.etiya.ReCapProject.business.abstracts.InvoiceService;
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
import com.etiya.ReCapProject.entities.concretes.AdditionalService;
import com.etiya.ReCapProject.entities.concretes.ApplicationUser;
import com.etiya.ReCapProject.entities.concretes.Car;
import com.etiya.ReCapProject.entities.concretes.CorporateCustomer;
import com.etiya.ReCapProject.entities.concretes.CreditCard;
import com.etiya.ReCapProject.entities.concretes.IndividualCustomer;
import com.etiya.ReCapProject.entities.concretes.Invoice;
import com.etiya.ReCapProject.entities.concretes.Rental;
import com.etiya.ReCapProject.entities.dto.BrandDto;
import com.etiya.ReCapProject.entities.dto.RentalDetailDto;
import com.etiya.ReCapProject.entities.dto.RentalDto;
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
	private FakePosService fakePosService;
	private AdditionalServiceService additionalServiceService;
	private InvoiceService invoiceService;
	private ModelMapper modelMapper;

	@Autowired
	public RentalManager(RentalDao rentalDao, CarService carService, UserService userService,
			CorporateCustomerService corporateCustomerService, IndividualCustomerService individualCustomerService,
			FindeksScoreService findeksScoreService, CreditCardService creditCardService, FakePosService fakePosService,
			AdditionalServiceService additionalServiceService, InvoiceService invoiceService, ModelMapper modelMapper) {
		super();
		this.rentalDao = rentalDao;
		this.carService = carService;
		this.userService = userService;
		this.corporateCustomerService = corporateCustomerService;
		this.individualCustomerService = individualCustomerService;
		this.findeksScoreService = findeksScoreService;
		this.creditCardService = creditCardService;
		this.fakePosService = fakePosService;
		this.additionalServiceService = additionalServiceService;
		this.invoiceService = invoiceService;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<Rental>> findAll() {
		return new SuccessDataResult<List<Rental>>(this.rentalDao.findAll(), Messages.RENTALS + Messages.LIST);
	}

	@Override
	public DataResult<List<RentalDto>> getAll() {
		List<Rental> rentals = this.rentalDao.findAll();
		List<RentalDto> rentalsDto = rentals.stream().map(brand -> modelMapper.map(brand, RentalDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalDto>>(rentalsDto, Messages.RENTALS + Messages.LIST);
	}

	@Override
	public DataResult<Rental> findById(int rentalId) {
		return new SuccessDataResult<Rental>(rentalDao.getById(rentalId), Messages.RENTAL + Messages.LIST);
	}

	@Override
	public DataResult<RentalDto> getById(int rentalId) {
		Rental rental = this.rentalDao.getById(rentalId);

		return new SuccessDataResult<RentalDto>(modelMapper.map(rental, RentalDto.class),
				Messages.RENTAL + Messages.LIST);
	}

	@Override
	public Result insert(CreateRentalRequest createRentalRequest, CreateCreditCardRequest createCreditCardRequest) {
		var result = BusinessRules.run(checkIfCarInRepair(createRentalRequest.getCarId()),
				getByCarIdWhereReturnDateIsNull(createRentalRequest.getCarId()),
				checkCustomerFindeksScoreForCar(createRentalRequest.getApplicationUserUserUserId(), createRentalRequest.getCarId()),
				checkRentCity(createRentalRequest.getRentCity(),
						this.carService.getById(createRentalRequest.getCarId()).getData().getCity()),
				checkReturnCity(createRentalRequest.getReturnCity(),
						this.carService.getById(createRentalRequest.getCarId()).getData().getCity(),
						createRentalRequest.getCarId()),
				checkPosService(createCreditCardRequest.getNameOnCard(), createCreditCardRequest.getCreditCardNumber(),
						createCreditCardRequest.getExpirationDate(), createCreditCardRequest.getCvc()));

		if (result != null) {
			return result;
		}

		return this.checkToSaveCreditCard(createRentalRequest, createCreditCardRequest);
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest, CreateCreditCardRequest createCreditCardRequest) {

		var result = BusinessRules.run(checkIfCarInRepair(updateRentalRequest.getCarId()),
				checkRentCity(updateRentalRequest.getRentCity(),
						this.carService.getById(updateRentalRequest.getCarId()).getData().getCity()),
				checkReturnCity(updateRentalRequest.getReturnCity(),
						this.carService.getById(updateRentalRequest.getCarId()).getData().getCity(),
						updateRentalRequest.getCarId()),
				checkPosService(createCreditCardRequest.getNameOnCard(), createCreditCardRequest.getCreditCardNumber(),
						createCreditCardRequest.getExpirationDate(), createCreditCardRequest.getCvc()));

		if (result != null) {
			return result;
		}

		CreateRentalRequest createRentalRequest = modelMapper.map(updateRentalRequest, CreateRentalRequest.class);
		return this.checkToSaveCreditCard(createRentalRequest, createCreditCardRequest);
		
		//		Car car = this.carService.findById(updateRentalRequest.getCarId()).getData();
//
//		if (updateRentalRequest.getReturnKm() > updateRentalRequest.getRentKm()) {
//			car.setKm(updateRentalRequest.getReturnKm());
//		}
//
//		ApplicationUser applicationUser = this.userService.findById(updateRentalRequest.getUserId()).getData();
//
//		Rental rental = rentalDao.getById(updateRentalRequest.getRentalId());
//		rental.setCar(car);
//		rental.setApplicationUser(applicationUser);
//		rental.setRentDate(updateRentalRequest.getRentDate());
//		rental.setReturnDate(updateRentalRequest.getReturnDate());
//
//		this.rentalDao.save(rental);
//		this.createInvoiceRequest(createRentalRequest.getAdditionalService(), rental.getRentalId());
//		// this.getInvoiceIfCarIsReturned(rental.getRentalId(),
//		// updateRentalRequest.getReturnDate());
	//	return new SuccessResult(Messages.RENTAL + Messages.UPDATE);
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

	@Transactional
	@Override
	public DataResult<Invoice> createInvoiceRequest(List<Integer> additionalServices, int rentalId) {

		Rental rental = this.rentalDao.getById(rentalId);

		if (rental.isReturned() == true) {

			Invoice invoice = new Invoice();

			invoice.setRental(rental);

			invoice.setAmount(invoiceAmountCalculation(additionalServices,
					checkReturnCity(rental.getRentCity(), rental.getReturnCity(), rental.getCar().getCarId())
							.getMessage(),
					this.rentalDao.getById(rentalId).getCar().getDailyPrice(),
					this.calculateTotalRentalDay(this.rentalDao.getById(rentalId).getRentDate(),
							this.rentalDao.getById(rentalId).getReturnDate()),
					500));

			String randomInvoiceNo = java.util.UUID.randomUUID().toString();
			invoice.setInvoiceNo(randomInvoiceNo);

			Date dateNow = new java.sql.Date(new java.util.Date().getTime());
			invoice.setCreationDate(dateNow);

			invoice.setRentDate(this.rentalDao.getById(rentalId).getRentDate());
			invoice.setReturnDate(this.rentalDao.getById(rentalId).getReturnDate());
			invoice.setTotalRentalDay(this.calculateTotalRentalDay(this.rentalDao.getById(rentalId).getRentDate(),
					this.rentalDao.getById(rentalId).getReturnDate()));
			this.invoiceService.insert(invoice);
			return new SuccessDataResult<Invoice>(invoice, "Fatura talebi oluşturulmuştur");

		} else {
			return new SuccessDataResult<Invoice>();
		}
	}

	private Result insertRental(CreateRentalRequest createRentalRequest) {

		Car car = this.carService.findById(createRentalRequest.getCarId()).getData();

		if (createRentalRequest.getReturnKm() > createRentalRequest.getRentKm()) {
			car.setKm(createRentalRequest.getReturnKm());
		}

		Rental rental = modelMapper.map(createRentalRequest, Rental.class);
		
		this.rentalDao.save(rental);
		this.createInvoiceRequest(createRentalRequest.getAdditionalService(), rental.getRentalId());

		return new SuccessResult(Messages.RENTAL + Messages.ADD);
	}

	private Result checkCustomerFindeksScoreForCar(int applicationUserId, int carId) {

		if (this.individualCustomerService.existsByUserId(applicationUserId).isSuccess()) {
			IndividualCustomer individualCustomer = this.individualCustomerService
					.getByApplicationUser_UserId(applicationUserId).getData();

			if (this.carService.getById(carId).getData().getMinFindeksScore() > this.findeksScoreService
					.getIndividualFindeksScore(individualCustomer.getNationalIdentityNumber())) {

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

	private double invoiceAmountCalculation(List<Integer> additionalServices, String checkReturnCity,
			double carDailyPrice, long totalRentalDay, int amountToRaisedIfReturnedAnotherCity) {
		if (checkReturnCity.contains("true")) {
			return carDailyPrice * totalRentalDay + amountToRaisedIfReturnedAnotherCity
					+ this.additionalServiceCost(additionalServices).getData();
		} else {
			return carDailyPrice * totalRentalDay + this.additionalServiceCost(additionalServices).getData();
		}
	}

	private long calculateTotalRentalDay(String rentDateString, String returnDateString) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

		LocalDate rentDate = LocalDate.parse(rentDateString, formatter);
		LocalDate returnDate = LocalDate.parse(returnDateString, formatter);

		return ChronoUnit.DAYS.between(rentDate, returnDate);
	}

	private DataResult<Integer> additionalServiceCost(List<Integer> additionalServicesId) {
		int totalCost = 0;

		List<AdditionalService> additionalServices = this.additionalServiceService.findAll().getData();
		for (int i = 0; i < additionalServicesId.size(); i++) {
			for (AdditionalService additionalService : additionalServices) {
				if (additionalService.getAdditionalServiceId() == this.additionalServiceService
						.getById(additionalServicesId.get(i)).getData().getAdditionalServiceId()) {
					totalCost = totalCost + additionalService.getPrice();
					System.out.println(additionalService.getPrice());
					System.out.println(totalCost);
				}

			}
		}

		return new SuccessDataResult<Integer>(totalCost);
	}

	private Result checkPosService(String nameOnCard, String creditCardNumber, String expirationDate, String cvc) {

		if (this.fakePosService.fakePosService(nameOnCard, creditCardNumber, expirationDate, cvc)) {
			return new SuccessResult();
		} else {
			return new ErrorResult("Ödeme alınamadı.");
		}
	}

	private DataResult<List<CreditCard>> checkToSaveCreditCard(CreateRentalRequest createRentalRequest,
			CreateCreditCardRequest createCreditCardRequest) {

		List<CreditCard> creditCards = creditCardService
				.findCreditCardsByApplicationUser_UserId(createRentalRequest.getApplicationUserUserUserId()).getData();
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

	private Result checkIfCarInRepair(int carId) {
		if (carService.getById(carId).getData().isInRepair()) {
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
			return new SuccessResult("true");
		}
		return new SuccessResult("false");
	}
}