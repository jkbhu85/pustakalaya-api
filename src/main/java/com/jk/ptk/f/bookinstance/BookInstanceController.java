package com.jk.ptk.f.bookinstance;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jk.ptk.app.response.PtkResponse;
import com.jk.ptk.util.UserUtil;
import com.jk.ptk.validation.InvalidArgumentException;

@RestController
@RequestMapping("/ptk/book/instance")
public class BookInstanceController {
	private static final Logger log = LoggerFactory.getLogger(BookInstanceController.class);

	private BookInstanceService service;

	@Autowired
	public BookInstanceController(BookInstanceService service) {
		this.service = service;
	}

	@PostMapping("/assign")
	public ResponseEntity<PtkResponse> assignBook(@RequestBody Map<String, String> requestBody) {
		PtkResponse response = null;
		HttpStatus httpStatus = null;

		String bookInstanceId = requestBody.get("bookInstanceId");
		String email = requestBody.get("email");
		String requester = UserUtil.getEmail();

		try {
			service.assignBookInstance(bookInstanceId, email, requester);
			httpStatus = HttpStatus.OK;
		} catch (InvalidArgumentException e) {
			response = new PtkResponse();
			response.setResponseCode(e.getErrorCode()).setMessage("ERROR_ASSIGN_BOOK");
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			log.error("Error occurred while assigning book with id {} to {}.", bookInstanceId, email);
			log.error("Exception details.", e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(response, httpStatus);
	}
}
