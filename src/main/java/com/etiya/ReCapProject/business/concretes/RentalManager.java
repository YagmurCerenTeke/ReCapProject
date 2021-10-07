package com.etiya.ReCapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.CarService;
import com.etiya.ReCapProject.business.abstracts.CorporateCustomerService;
import com.etiya.ReCapProject.business.abstracts.CreditCardService;
import com.etiya.ReCapProject.business.abstracts.FakePosService;
import com.etiya.ReCapProject.business.abstracts.FindeksScoreService;
import com.etiya.ReCapProject.business.abstracts.IndividualCustomerService;
import com.etiya.ReCapProject.business.abstracts.InvoiceService;
import com.etiya.ReCapProject.business.abstracts.RentalService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.RentalDao;
import com.etiya.ReCapProject.entities.concretes.Car;
import com.etiya.ReCapProject.entities.concretes.CorporateCustomer;
import com.etiya.ReCapProject.entities.concretes.IndividualCustomer;
import com.etiya.ReCapProject.entities.concretes.Rental;
import com.etiya.ReCapProject.entities.dto.RentalDto;
import com.etiya.ReCapProject.entities.requests.creditCardRequests.CreateCreditCardRequest;
import com.etiya.ReCapProject.entities.requests.invoiceRequests.CreateInvoiceRequest;
import com.etiya.ReCapProject.entities.requests.rentalRequests.CreateRentalRequest;
import com.etiya.ReCapProject.entities.requests.rentalRequests.DeleteRentalRequest;
import com.etiya.ReCapProject.entities.requests.rentalRequests.UpdateRentalRequest;

@Service
public class RentalManager implements RentalService {

	private RentalDao rentalDao;
	private CarService carService;
	private CorporateCustomerService corporateCustomerService;
	private IndividualCustomerService individualCustomerService;
	private FindeksScoreService findeksScoreService;
	private CreditCardService creditCardService;
	private FakePosService fakePosService;
	private InvoiceService invoiceService;
	private ModelMapper modelMapper;

	@Autowired
	public RentalManager(RentalDao rentalDao, CarService carService, CorporateCustomerService corporateCustomerService,
			IndividualCustomerService individualCustomerService, FindeksScoreService findeksScoreService,
			CreditCardService creditCardService, FakePosService fakePosService, InvoiceService invoiceService,
			ModelMapper modelMapper) {
		super();
		this.rentalDao = rentalDao;
		this.carService = carService;
		this.corporateCustomerService = corporateCustomerService;
		this.individualCustomerService = individualCustomerService;
		this.findeksScoreService = findeksScoreService;
		this.creditCardService = creditCardService;
		this.fakePosService = fakePosService;

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
	public Result existsByUserId(int applicationUserId) {
		if (this.rentalDao.existsByApplicationUser_UserId(applicationUserId)) {
			return new SuccessResult();
		}
		return new ErrorResult();
	}

	@Override
	public Result insert(CreateRentalRequest createRentalRequest, CreateCreditCardRequest createCreditCardRequest) {
		var result = BusinessRules.run(checkIfCarInRepair(createRentalRequest),
				checkIfCarIsCurrentlyRented(createRentalRequest), checkRentCityByCarCity(createRentalRequest),
				checkCustomerFindeksScoreForCar(createRentalRequest),
				this.creditCardService.checkCreditCardFormat(createCreditCardRequest.getCreditCardNumber(),
						createCreditCardRequest.getCvc(), createCreditCardRequest.getExpirationDate()),
				checkPosService(createCreditCardRequest), checkIsReturned(createRentalRequest));

		if (result != null) {
			return result;
		}

		this.insertRental(createRentalRequest);
		this.saveCreditCard(createRentalRequest, createCreditCardRequest);

		return new SuccessResult(Messages.RENTAL + Messages.ADD);
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest, CreateCreditCardRequest createCreditCardRequest) {
		CreateRentalRequest createRentalRequest = modelMapper.map(updateRentalRequest, CreateRentalRequest.class);
		var result = BusinessRules.run(checkIfCarInRepair(createRentalRequest),
				checkRentCityByCarCity(createRentalRequest), checkCustomerFindeksScoreForCar(createRentalRequest),
				this.creditCardService.checkCreditCardFormat(createCreditCardRequest.getCreditCardNumber(),
						createCreditCardRequest.getCvc(), createCreditCardRequest.getExpirationDate()),
				checkPosService(createCreditCardRequest), checkIsReturned(createRentalRequest));

		if (result != null) {
			return result;
		}

		this.insertRental(createRentalRequest);
		this.saveCreditCard(createRentalRequest, createCreditCardRequest);
		return new SuccessResult(Messages.RENTAL + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		this.rentalDao.delete(rentalDao.getById(deleteRentalRequest.getRentalId()));
		return new SuccessResult(Messages.RENTAL + Messages.DELETE);
	}

	private Result checkIfCarInRepair(CreateRentalRequest createRentalRequest) {
		if (carService.getById(createRentalRequest.getCarId()).getData().isInRepair()) {
			return new ErrorResult(Messages.CARINREPAIR);
		}
		return new SuccessResult();
	}

	private Result checkIfCarIsCurrentlyRented(CreateRentalRequest createRentalRequest) {

		for (Rental rental : this.rentalDao.getByCar_CarId(createRentalRequest.getCarId())) {
			if (!rental.isReturned()) {
				return new ErrorResult(Messages.CAR + Messages.NOTAVAILABLE);
			}
		}
		return new SuccessResult();
	}

	private Result checkRentCityByCarCity(CreateRentalRequest createRentalRequest) {
		String rentCity = createRentalRequest.getRentCity();
		String carCity = this.carService.getById(createRentalRequest.getCarId()).getData().getCity();
		if (!rentCity.equals(carCity)) {
			return new ErrorResult(Messages.CARINANOTHERCITY);
		}
		return new SuccessResult();
	}

	private Result checkCustomerFindeksScoreForCar(CreateRentalRequest createRentalRequest) {

		if (this.individualCustomerService.existsByUserId(createRentalRequest.getApplicationUserUserUserId())
				.isSuccess()) {
			IndividualCustomer individualCustomer = this.individualCustomerService
					.getByApplicationUser_UserId(createRentalRequest.getApplicationUserUserUserId()).getData();

			if (this.carService.getById(createRentalRequest.getCarId()).getData()
					.getMinFindeksScore() > this.findeksScoreService
							.getIndividualFindeksScore(individualCustomer.getNationalIdentityNumber())) {

				return new ErrorResult(Messages.FINDEKSSCORENOTENOUGH);
			}

		}

		if (this.corporateCustomerService.existsByUserId(createRentalRequest.getApplicationUserUserUserId())
				.isSuccess()) {

			CorporateCustomer corporateCustomer = this.corporateCustomerService
					.getByApplicationUser_UserId(createRentalRequest.getApplicationUserUserUserId()).getData();

			if (this.carService.getById(createRentalRequest.getCarId()).getData()
					.getMinFindeksScore() > this.findeksScoreService
							.getCorporateFindeksScore(corporateCustomer.getTaxNumber())) {

				return new ErrorResult(Messages.FINDEKSSCORENOTENOUGH);
			}
		}

		return new SuccessResult();

	}

	private Result checkPosService(CreateCreditCardRequest createCreditCardRequest) {

		if (this.fakePosService.fakePosService(createCreditCardRequest.getNameOnCard(),
				createCreditCardRequest.getCreditCardNumber(), createCreditCardRequest.getExpirationDate(),
				createCreditCardRequest.getCvc())) {
			return new SuccessResult();
		} else {
			return new ErrorResult(Messages.PAYMENTFAILED);
		}
	}

	private Result checkIsReturned(CreateRentalRequest createRentalRequest) {

		if (createRentalRequest.isReturned()) {

			this.checkReturnCity(createRentalRequest);
			this.setCarReturnKm(createRentalRequest);

			return new SuccessResult();

		} else {
			return new SuccessResult();
		}
	}

	private Result checkReturnCity(CreateRentalRequest createRentalRequest) {
		String returnCity = createRentalRequest.getReturnCity();
		String rentCity = createRentalRequest.getRentCity();

		if (!returnCity.equals(rentCity)) {
			this.carService.findById(createRentalRequest.getCarId()).getData().setCity(returnCity);
			return new SuccessResult("true");
		}
		return new SuccessResult("false");
	}

	private Result setCarReturnKm(CreateRentalRequest createRentalRequest) {

		Car car = this.carService.findById(createRentalRequest.getCarId()).getData();

		if (createRentalRequest.getReturnKm() > createRentalRequest.getRentKm()) {
			car.setKm(createRentalRequest.getReturnKm());
		}
		return new SuccessResult();
	}

	private Result insertRental(CreateRentalRequest createRentalRequest) {

		Rental rental = modelMapper.map(createRentalRequest, Rental.class);
		this.rentalDao.save(rental);

		this.createRentalInvoiceRequest(createRentalRequest, rental);

		return new SuccessResult(Messages.RENTAL + Messages.ADD);
	}

	public DataResult<CreateInvoiceRequest> createRentalInvoiceRequest(CreateRentalRequest createRentalRequest,
			Rental rental) {

		if (createRentalRequest.isReturned()) {
			CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
			createInvoiceRequest.setAdditionalService(createRentalRequest.getAdditionalService());
			createInvoiceRequest.setRental(rental);

			this.invoiceService.insert(createInvoiceRequest, createInvoiceRequest.getAdditionalService());
			return new SuccessDataResult<CreateInvoiceRequest>(createInvoiceRequest, Messages.INVOICEREQUESTCREATED);
		}
		return new SuccessDataResult<CreateInvoiceRequest>();

	}

	private void saveCreditCard(CreateRentalRequest createRentalRequest,
			CreateCreditCardRequest createCreditCardRequest) {
		if (createRentalRequest.isSaveCreditCard()) {
			this.creditCardService.add(createCreditCardRequest);
		}
	}

}