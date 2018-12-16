package com.jk.ptk.f.bookinstance;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.jk.ptk.util.UserUtil;
import com.jk.ptk.validation.InvalidArgumentException;

@RestController
@RequestMapping("/ptk/book/instance")
public class BookInstanceController {
	private static final Logger log = LogManager.getLogger(BookInstanceController.class);

	private BookInstanceService service;

	@Autowired
	public BookInstanceController(BookInstanceService service) {
		this.service = service;
	}
	
	@GetMapping("/{id}")
	public BookInstanceValue getBookInstace(@PathVariable("id") String id) {
		BookInstanceValue biv = null;
		try {
			BookInstance bi = service.findBookInstance(id);
			biv = BookInstanceUtil.toValue(bi);
		} catch (Exception e) {
			log.error("Exception occurred while fetching book instance with id {}", id, e);
		}
		return biv;
	}

	@PostMapping("/assign")
	public ResponseEntity<PtkResponse> assignBook(@RequestBody Map<String, String> requestBody) {
		PtkResponse response = null;
		HttpStatus httpStatus = null;

		String bookInstanceId = requestBody.get("bookInstanceId");
		String email = requestBody.get("username");
		String requester = UserUtil.getEmail();

		try {
			service.assignBookInstance(bookInstanceId, email, requester);
			httpStatus = HttpStatus.OK;
		} catch (InvalidArgumentException e) {
			Map<String, ResponseCode> errorMap = new HashMap<>();
			errorMap.put(e.getFieldName(), e.getErrorCode());
			
			response = 
				new PtkResponse()
				.setResponseCode(ResponseCode.OPERATION_UNSUCCESSFUL)
				.setMessage("ERROR_ASSIGN_BOOK")
				.setErrors(errorMap);
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			log.error("Error occurred while assigning book with id {} to {}.", bookInstanceId, email, e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(response, httpStatus);
	}
}
