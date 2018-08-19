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
import com.jk.ptk.f.user.User;
import com.jk.ptk.f.user.UserService;
import com.jk.ptk.util.UserUtil;
import com.jk.ptk.util.mail.MailNotSentException;
import com.jk.ptk.validation.ValidationException;

/**
 * API endpoint to manipulate instances of type {@link NewUser}.
 *
 * @author Jitendra
 *
 */

@RestController
@RequestMapping("/ptk/newUser")
public class NewUserController {
	private static final Logger log = LoggerFactory.getLogger(NewUserController.class);

	@Autowired
	private NewUserService service;

	@Autowired
	private UserService userService;

	/**
	 * Stores the specified instance to the storage.
	 *
	 * @param newUser
	 *            the specified instance to be stored
	 */
	@PostMapping
	public ResponseEntity<PtkResponse> addNewUser(@RequestBody NewUser newUser) {
		HttpStatus httpStatus;
		PtkResponse response = new PtkResponse();

		try {
			User acCreatedBy = userService.findUser(UserUtil.getEmail());
			newUser.setAcCreatedBy(acCreatedBy);
			service.addNewUser(newUser);

			response.setResponseCode(ResponseCode.OPERATION_SUCCESSFUL).setMessage("SUCCESS_NEWUSER_ADDED");

			httpStatus = HttpStatus.ACCEPTED;

		} catch (ValidationException e) {
			response.setResponseCode(ResponseCode.OPERATION_UNSUCCESSFUL).setMessage("ERROR_INVALID_FIELDS")
					.setErrors(e.getErrorMap());

			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (MailNotSentException e) {
			response.setResponseCode(ResponseCode.MAIL_NOT_SENT_INVALID_EMAIL).setMessage("ERROR_EMAIL_NOT_SENT");

			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			response.setResponseCode(ResponseCode.UNKNOWN_ERROR).setMessage("ERROR_UNKNOWN");

			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

			log.error("Exception in adding new user.", e);
		}

		ResponseEntity<PtkResponse> res = new ResponseEntity<>(response, httpStatus);
		return res;
	}

	/**
	 * Returns new user associated with the {@code id}.
	 *
	 * @param id
	 *            the specified id
	 * @return new user associated with the {@code id}
	 */
	@GetMapping("/{id}")
	public ResponseEntity<PtkResponse> getNewUser(@PathVariable("id") String id) {
		PtkResponse response = new PtkResponse();
		HttpStatus httpStatus;

		try {
			NewUser user = service.getNewUser(id);
			httpStatus = HttpStatus.OK;
			response.setData(user);
		} catch (ValidationException e) {
			response.setResponseCode(ResponseCode.OPERATION_UNSUCCESSFUL).setMessage("ERROR_INVALID_FIELDS")
					.setData(e.getErrorMap());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			response.setResponseCode(ResponseCode.UNKNOWN_ERROR).setMessage("ERROR_UNKNOWN");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

			log.error("Exception in fetching new user.", e);
		}

		ResponseEntity<PtkResponse> res = new ResponseEntity<>(response, httpStatus);

		return res;
	}
}
