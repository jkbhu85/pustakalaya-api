package com.jk.ptk.f.newuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.jk.ptk.util.mail.MailNotSentException;
import com.jk.ptk.validation.ValidationException;

/**
 * API end point for the type {@link NewUser}.
 *
 * @author Jitendra
 */

@RestController
@RequestMapping("/ptk/newUser")
public class NewUserController {
	private static final Logger log = LoggerFactory.getLogger(NewUserController.class);

	@Autowired
	private NewUserService service;

	@PostMapping
	public ResponseEntity<PtkResponse> addNewUser(@RequestBody NewUserV newUser) {
		HttpStatus httpStatus;
		PtkResponse response = new PtkResponse();

		try {
			service.save(newUser);

			response.setResponseCode(ResponseCode.OPERATION_SUCCESSFUL).setMessage("SUCCESS_NEWUSER_ADDED");

			httpStatus = HttpStatus.OK;
		} catch (ValidationException e) {
			response.setResponseCode(ResponseCode.OPERATION_UNSUCCESSFUL).setMessage("ERROR_INVALID_FIELDS")
					.setErrors(e.getErrorMap());

			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (MailNotSentException e) {
			response.setResponseCode(ResponseCode.MAIL_NOT_SENT_INVALID_EMAIL).setMessage("ERROR_EMAIL_NOT_SENT");

			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			log.error("Exception in adding new user.", e);
		}

		ResponseEntity<PtkResponse> res = new ResponseEntity<>(response, httpStatus);
		return res;
	}

	@GetMapping("/{id}")
	public ResponseEntity<PtkResponse> getNewUser(@PathVariable("id") String id) {
		PtkResponse response = new PtkResponse();
		HttpStatus httpStatus;

		try {
			NewUser user = service.find(id);
			httpStatus = HttpStatus.OK;
			response.setData(user);
		} catch (ValidationException e) {
			response.setResponseCode(ResponseCode.OPERATION_UNSUCCESSFUL).setMessage("ERROR_INVALID_FIELDS")
					.setData(e.getErrorMap());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			log.error("Exception in fetching new user.", e);
		}

		ResponseEntity<PtkResponse> res = new ResponseEntity<>(response, httpStatus);

		return res;
	}
}
