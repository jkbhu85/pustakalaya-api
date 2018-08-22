package com.jk.ptk.f.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jk.ptk.app.response.PtkResponse;
import com.jk.ptk.app.response.ResponseCode;
import com.jk.ptk.validation.ValidationException;

@RestController
@RequestMapping("/ptk/book")
public class BookController {
	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	private final BookService service;
	
	public BookController(BookService service) {
		this.service = service;
	}
	
	@GetMapping("/{bookId}")
	public Book find(@PathVariable("bookId") String bookId) {
		try {
			return service.find(bookId);
		} catch (Exception e) {
			log.error("Error while finding book.", e);
		}
		
		return null;
	}
	
	@PostMapping
	public ResponseEntity<PtkResponse> save(@RequestBody BookV bookValues) {
		HttpStatus httpStatus;
		PtkResponse ptkResponse = null;
		
		try {
			service.save(bookValues);
			httpStatus = HttpStatus.OK;
		} catch (ValidationException e) {
			ptkResponse = new PtkResponse();
			ptkResponse.setErrors(e.getErrorMap()).setResponseCode(ResponseCode.OPERATION_UNSUCCESSFUL);
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			log.error("Error while saving book.", e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		ResponseEntity<PtkResponse> res = new ResponseEntity<>(ptkResponse, httpStatus);
		return res;
	}
}
