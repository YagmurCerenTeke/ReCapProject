package com.etiya.ReCapProject.business.concretes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.AdditionalServiceService;
import com.etiya.ReCapProject.business.abstracts.CarService;
import com.etiya.ReCapProject.business.abstracts.InvoiceService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.InvoiceDao;
import com.etiya.ReCapProject.entities.concretes.AdditionalService;
import com.etiya.ReCapProject.entities.concretes.Invoice;
import com.etiya.ReCapProject.entities.concretes.Rental;
import com.etiya.ReCapProject.entities.dto.InvoiceDto;
import com.etiya.ReCapProject.entities.requests.invoiceRequests.CreateInvoiceRequest;
import com.etiya.ReCapProject.entities.requests.invoiceRequests.DeleteInvoiceRequest;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao;
	private CarService carService;
	private AdditionalServiceService additionalServiceService;
	private ModelMapper modelMapper;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, CarService carService,
			AdditionalServiceService additionalServiceService, ModelMapper modelMapper) {
		super();
		this.invoiceDao = invoiceDao;
		this.carService = carService;
		this.additionalServiceService = additionalServiceService;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<Invoice>> findAll() {
		return new SuccessDataResult<List<Invoice>>(this.invoiceDao.findAll(), Messages.INVOICES + Messages.LIST);
	}

	@Override
	public DataResult<List<InvoiceDto>> getAll() {
		List<Invoice> invoices = this.invoiceDao.findAll();
		List<InvoiceDto> invoicesDto = invoices.stream().map(brand -> modelMapper.map(brand, InvoiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceDto>>(invoicesDto, Messages.INVOICES + Messages.LIST);
	}

	@Override
	public DataResult<Invoice> findById(int invoiceId) {
		return new SuccessDataResult<Invoice>(this.invoiceDao.getById(invoiceId), Messages.INVOICE + Messages.LIST);
	}

	@Override
	public DataResult<InvoiceDto> getById(int invoiceId) {
		Invoice invoice = this.invoiceDao.getById(invoiceId);

		return new SuccessDataResult<InvoiceDto>(modelMapper.map(invoice, InvoiceDto.class),
				Messages.INVOICE + Messages.LIST);
	}

	@Override
	public DataResult<List<InvoiceDto>> findInvoicesBetween(Date endDate, Date startDate) {
		List<Invoice> invoices = this.invoiceDao.findAllByCreationDateBetween(endDate, startDate);
		List<InvoiceDto> invoicesDto = invoices.stream().map(invoice -> modelMapper.map(invoice, InvoiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceDto>>(invoicesDto, Messages.INVOICES + Messages.LIST);
	}

	@Override
	public DataResult<List<InvoiceDto>> getByRental_ApplicationUser_UserId(int userId) {
		List<Invoice> invoices = this.invoiceDao.getByRental_ApplicationUser_UserId(userId);
		List<InvoiceDto> invoicesDto = invoices.stream().map(invoice -> modelMapper.map(invoice, InvoiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceDto>>(invoicesDto, Messages.INVOICES + Messages.LIST);
	}

	@Override
	public Result insert(CreateInvoiceRequest createInvoiceRequest, List<Integer> additionalServices) {
		var result = BusinessRules.run(this.checkInvoiceByRentalId(createInvoiceRequest.getRental()
				.getRentalId()));

		if (result != null) {
			return result;
		}

		Invoice invoice = modelMapper.map(createInvoiceRequest, Invoice.class);

		invoice.setAmount(invoiceAmountCalculation(additionalServices,
				createInvoiceRequest, 500));

		invoice.setInvoiceNo(java.util.UUID.randomUUID().toString());

		Date dateNow = new java.sql.Date(new java.util.Date().getTime());
		invoice.setCreationDate(dateNow);

		invoice.setRentDate(createInvoiceRequest.getRental().getRentDate());
		invoice.setReturnDate(createInvoiceRequest.getRental().getReturnDate());
		invoice.setTotalRentalDay(this.calculateTotalRentalDay(createInvoiceRequest));

		this.invoiceDao.save(invoice);

		return new SuccessResult(Messages.INVOICE + Messages.ADD);
	}

//	@Override
//	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
//
//		Invoice invoice = this.invoiceDao.getById(updateInvoiceRequest.getInvoiceId());
//
//		Rental rental = this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData();
//		invoice.setRental(rental);
//
//		invoice.setAmount(invoiceAmountCalculation(
//				this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getCar().getDailyPrice(),
//				this.calculateTotalRentalDay(
//						this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getRentDate(),
//						this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getReturnDate())));
//
//		String randomInvoiceNo = java.util.UUID.randomUUID().toString();
//		invoice.setInvoiceNo(randomInvoiceNo);
//
//		Date dateNow = new java.sql.Date(new java.util.Date().getTime());
//		invoice.setCreationDate(dateNow);
//
//		invoice.setRentalRentDate(
//				this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getRentDate());
//		invoice.setRentalReturnDate(
//				this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getReturnDate());
//		invoice.setTotalRentalDay(this.calculateTotalRentalDay(
//				this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getRentDate(),
//				this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getReturnDate()));
//
//		this.invoiceDao.save(invoice);
//
//		return new SuccessResult(Messages.INVOICE + Messages.UPDATE);
//	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		Invoice invoice = this.invoiceDao.getById(deleteInvoiceRequest.getInvoiceId());

		this.invoiceDao.delete(invoice);
		return new SuccessResult(Messages.INVOICE + Messages.DELETE);
	}

	private Result checkInvoiceByRentalId(int rentalId) {
		if (this.invoiceDao.existsByRental_RentalId(rentalId)) {
			return new ErrorResult(Messages.INVOICEEXISTS);
		}
		return new SuccessResult();
	}

	private double invoiceAmountCalculation(List<Integer> additionalServices, CreateInvoiceRequest createInvoiceRequest,
			int amountToRaisedIfReturnedAnotherCity) {
		double carDailyPrice = this.carService.getById(createInvoiceRequest.getRental().getCar().getCarId()).getData()
				.getDailyPrice();
		long totalRentalDay = this.calculateTotalRentalDay(createInvoiceRequest);

		if (this.checkReturnCity(createInvoiceRequest.getRental()).getMessage().contains("true")) {

			return carDailyPrice * totalRentalDay + amountToRaisedIfReturnedAnotherCity
					+ this.additionalServiceCost(additionalServices).getData();
		} else {
			return carDailyPrice * totalRentalDay + this.additionalServiceCost(additionalServices).getData();
		}
	}

	private long calculateTotalRentalDay(CreateInvoiceRequest createInvoiceRequest) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

		LocalDate rentDate = LocalDate.parse(createInvoiceRequest.getRental().getRentDate(), formatter);
		LocalDate returnDate = LocalDate.parse(createInvoiceRequest.getRental().getReturnDate(), formatter);

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
				}
			}
		}

		return new SuccessDataResult<Integer>(totalCost);
	}

	private Result checkReturnCity(Rental rental) {
		String returnCity = rental.getReturnCity();
		String rentCity = rental.getRentCity();

		if (!returnCity.equals(rentCity)) {
			return new SuccessResult("true");
		}
		return new SuccessResult("false");
	}

}
