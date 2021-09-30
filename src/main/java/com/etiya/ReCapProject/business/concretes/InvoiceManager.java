package com.etiya.ReCapProject.business.concretes;

import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.InvoiceService;
import com.etiya.ReCapProject.business.abstracts.RentalService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.InvoiceDao;
import com.etiya.ReCapProject.entities.concretes.Invoice;
import com.etiya.ReCapProject.entities.concretes.Rental;
import com.etiya.ReCapProject.entities.requests.invoiceRequests.CreateInvoiceRequest;
import com.etiya.ReCapProject.entities.requests.invoiceRequests.DeleteInvoiceRequest;
import com.etiya.ReCapProject.entities.requests.invoiceRequests.UpdateInvoiceRequest;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao;
	private RentalService rentalService;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, RentalService rentalService) {
		super();
		this.invoiceDao = invoiceDao;
		this.rentalService = rentalService;
	}

	@Override
	public DataResult<List<Invoice>> getAll() {
		return new SuccessDataResult<List<Invoice>>(this.invoiceDao.findAll(), Messages.INVOICES + Messages.LIST);
	}

	@Override
	public DataResult<Invoice> getById(int invoiceId) {
		return new SuccessDataResult<Invoice>(this.invoiceDao.getById(invoiceId), Messages.INVOICE + Messages.LIST);
	}

	@Override
	public Result insert(CreateInvoiceRequest createInvoiceRequest) {
		var result = BusinessRules.run(this.checkInvoiceByRentalId(createInvoiceRequest.getRentalId())/*, checkIfCarIsReturned(createInvoiceRequest.getRentalId())*/);

		if (result != null) {
			return result;
		}

		this.invoiceDao.save(this.rentalService.createInvoiceRequest(createInvoiceRequest.getRentalId()).getData());

		return new SuccessResult(Messages.INVOICE + Messages.ADD);
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {

		Invoice invoice = this.invoiceDao.getById(updateInvoiceRequest.getInvoiceId());

		Rental rental = this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData();
		invoice.setRental(rental);

		invoice.setAmount(invoiceAmountCalculation(
				this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getCar().getDailyPrice(),
				this.calculateTotalRentalDay(
						this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getRentDate(),
						this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getReturnDate())));

		String randomInvoiceNo = java.util.UUID.randomUUID().toString();
		invoice.setInvoiceNo(randomInvoiceNo);

		Date dateNow = new java.sql.Date(new java.util.Date().getTime());
		invoice.setCreationDate(dateNow);

		invoice.setRentalRentDate(
				this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getRentDate());
		invoice.setRentalReturnDate(
				this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getReturnDate());
		invoice.setTotalRentalDay(this.calculateTotalRentalDay(
				this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getRentDate(),
				this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData().getReturnDate()));

		this.invoiceDao.save(invoice);

		return new SuccessResult(Messages.INVOICE + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		Invoice invoice = this.invoiceDao.getById(deleteInvoiceRequest.getInvoiceId());

		this.invoiceDao.delete(invoice);
		return new SuccessResult(Messages.INVOICE + Messages.DELETE);
	}

	@Override
	public DataResult<List<Invoice>> findInvoicesBetween(Date endDate, Date startDate) {
		return new SuccessDataResult<List<Invoice>>(this.invoiceDao.findAllByCreationDateBetween(endDate, startDate),
				Messages.INVOICES + Messages.LIST);
	}

	@Override
	public DataResult<List<Invoice>> getByRental_ApplicationUser_UserId(int userId) {
		return new SuccessDataResult<List<Invoice>>(this.invoiceDao.getByRental_ApplicationUser_UserId(userId),
				Messages.INVOICES + Messages.LIST);
	}

	private double invoiceAmountCalculation(double carDailyPrice, long totalRentalDay) {
		return carDailyPrice * totalRentalDay;
	}

	private Result checkInvoiceByRentalId(int rentalId) {
		if (this.invoiceDao.existsByRental_RentalId(rentalId)) {
			return new ErrorResult(Messages.INVOICEEXISTS);
		}
		return new SuccessResult();
	}

	private long calculateTotalRentalDay(String rentDateString, String returnDateString) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

		LocalDate rentDate = LocalDate.parse(rentDateString, formatter);
		LocalDate returnDate = LocalDate.parse(returnDateString, formatter);

		return ChronoUnit.DAYS.between(rentDate, returnDate);
	}
}
