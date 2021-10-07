package com.etiya.ReCapProject.business.concretes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.InvoiceService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.InvoiceDao;
import com.etiya.ReCapProject.entities.concretes.Invoice;
import com.etiya.ReCapProject.entities.dto.InvoiceDto;
import com.etiya.ReCapProject.entities.requests.invoiceRequests.DeleteInvoiceRequest;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao;
	private ModelMapper modelMapper;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapper modelMapper) {
		super();
		this.invoiceDao = invoiceDao;
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
		Invoice invoice =this.invoiceDao.getById(invoiceId);
		
		return new SuccessDataResult<InvoiceDto>(modelMapper.map(invoice, InvoiceDto.class), Messages.INVOICE + Messages.LIST);
	}

	@Override
	public Result insert(Invoice invoice) {
		var result = BusinessRules.run(this.checkInvoiceByRentalId(invoice.getRental().getRentalId())/*, checkIfCarIsReturned(createInvoiceRequest.getRentalId())*/);

		if (result != null) {
			return result;
		}

		this.invoiceDao.save(invoice);
//this.rentalService.createInvoiceRequest(createInvoiceRequest.getAdditionalService(), createInvoiceRequest.getRentalId()).getData()
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

	@Override
	public DataResult<List<InvoiceDto>> findInvoicesBetween(Date endDate, Date startDate) {
		List<Invoice> invoices = this.invoiceDao.findAllByCreationDateBetween(endDate, startDate);
		List<InvoiceDto> invoicesDto = invoices.stream().map(brand -> modelMapper.map(brand, InvoiceDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<InvoiceDto>>(invoicesDto,
				Messages.INVOICES + Messages.LIST);
	}

	@Override
	public DataResult<List<InvoiceDto>> getByRental_ApplicationUser_UserId(int userId) {
		List<Invoice> invoices = this.invoiceDao.getByRental_ApplicationUser_UserId(userId);
		List<InvoiceDto> invoicesDto = invoices.stream().map(brand -> modelMapper.map(brand, InvoiceDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<InvoiceDto>>(invoicesDto,
				Messages.INVOICES + Messages.LIST);
	}
//
//	private double invoiceAmountCalculation(double carDailyPrice, long totalRentalDay) {
//		return carDailyPrice * totalRentalDay;
//	}

	private Result checkInvoiceByRentalId(int rentalId) {
		if (this.invoiceDao.existsByRental_RentalId(rentalId)) {
			return new ErrorResult(Messages.INVOICEEXISTS);
		}
		return new SuccessResult();
	}
//
//	private long calculateTotalRentalDay(String rentDateString, String returnDateString) {
//
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
//
//		LocalDate rentDate = LocalDate.parse(rentDateString, formatter);
//		LocalDate returnDate = LocalDate.parse(returnDateString, formatter);
//
//		return ChronoUnit.DAYS.between(rentDate, returnDate);
//	}
}
