package com.etiya.ReCapProject.api.Controller;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.ReCapProject.business.abstracts.InvoiceService;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.entities.concretes.Invoice;
import com.etiya.ReCapProject.entities.requests.invoiceRequests.DeleteInvoiceRequest;

@RestController
@RequestMapping("api/invoices")
public class InvoiceController {
	
	private InvoiceService invoiceService;

	@Autowired
	public InvoiceController(InvoiceService invoiceService) {
		super();
		this.invoiceService = invoiceService;
	}

	@GetMapping("/getAll")
	public DataResult<List<Invoice>> getAll() {
		return this.invoiceService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<Invoice> getById(int invoiceId) {
		return this.invoiceService.getById(invoiceId);
	}
	
	
	@PostMapping("/insert")
	public Result insert(@Valid @RequestBody Invoice createInvoiceRequest) {
		return this.invoiceService.insert(createInvoiceRequest);
	}

//	@PostMapping("/update")
//	public Result update(@Valid @RequestBody UpdateInvoiceRequest updateInvoiceRequest) {
//		return this.invoiceService.update(updateInvoiceRequest);
//	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteInvoiceRequest deleteInvoiceRequest) {
		return this.invoiceService.delete(deleteInvoiceRequest);
	}

	@GetMapping("/getInvoicesByUserId")
	public DataResult<List<Invoice>> getInvoicesByUserId(int userId) {

		return this.invoiceService.getByRental_ApplicationUser_UserId(userId);
	}

	@GetMapping("/findInvoicesBetween")
	public DataResult<List<Invoice>> findInvoicesBetween(String endDate, String startDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date end = dateFormat.parse(endDate);
		Date start = dateFormat.parse(startDate);

		return this.invoiceService.findInvoicesBetween(start, end);
	}

}
