package com.jk.ptk.f.user;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jk.ptk.app.ResourceExpiredException;
import com.jk.ptk.app.response.PtkResponse;
import com.jk.ptk.app.response.ResponseCode;
import com.jk.ptk.security.login.InvalidCredentialsException;
import com.jk.ptk.validation.ValidationException;

/**
 * API endpoint to manipulate instances of type {@link User}.
 *
 * @author Jitendra
 *
 */
@RestController("/ptk/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private UserService service;

	@Autowired
	public UserController(UserService userService) {
		this.service = userService;
	}

	/**
	 * Updates the user associated with specified email, {@code null} otherwise.
	 *
	 * @param email
	 *            the specified email
	 * @return the user associated with specified email, {@code null} otherwise
	 */
	@GetMapping("/{email}")
	public User getUser(@PathVariable("email") String email) {
		return service.findUser(email);
	}

	/**
	 * Adds the user to the system with data represented by the specified object if
	 * the data passes the validation.
	 *
	 * @param userFormValues
	 *            data of the user
	 * @return an instance of type {@link PtkResponse} encapsulating the situation
	 *         of what happened
	 */
	@PostMapping
	public ResponseEntity<PtkResponse> addUser(@RequestBody UserFormValues userFormValues) {
		HttpStatus httpStatus;
		PtkResponse response = new PtkResponse();

		try {
			service.addUser(userFormValues);
			response.setResponseCode(ResponseCode.OPERATION_SUCCESSFUL).setMessage("SUCCESS_ACCOUNT_CREATED");
			httpStatus = HttpStatus.OK;
		} catch (ResourceExpiredException e) {
			response.setResponseCode(ResponseCode.RESOURCE_HAS_EXPIRED).setMessage("ERROR_RESOURCE_EXPIRED");
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (ValidationException e) {
			response.setResponseCode(ResponseCode.OPERATION_UNSUCCESSFUL).setMessage("ERROR_INVALID_FIELDS")
					.setErrors(e.getErrorMap());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			response.setResponseCode(ResponseCode.UNKNOWN_ERROR).setMessage("ERROR_UNKNOWN");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			log.error("Error while adding user.{}", e);
		}

		ResponseEntity<PtkResponse> res = new ResponseEntity<>(response, httpStatus);
		return res;
	}

	/**
	 * Updates the password of the user.
	 *
	 * @param body
	 *            required fields to update the password
	 * @return an instance of type {@link PtkResponse} encapsulating the situation
	 *         of what happened
	 */
	@PutMapping("/password")
	public ResponseEntity<PtkResponse> updatePassword(@RequestBody Map<String, String> body) {
		HttpStatus httpStatus;
		PtkResponse response = new PtkResponse();
		String email = body.get("email");
		String oldPassword = body.get("currentPassword");
		String newPassword = body.get("newPassword");
		String confirmNewPassword = body.get("confirmNewPassword");

		try {
			service.updatePassword(email, oldPassword, newPassword, confirmNewPassword);
			response.setResponseCode(ResponseCode.OPERATION_SUCCESSFUL).setMessage("SUCCESS_PASSWORD_CHANGED");
			httpStatus = HttpStatus.OK;
		} catch (InvalidCredentialsException e) {
			response.setResponseCode(ResponseCode.INVALID_CREDENTIALS).setMessage("ERROR_INVALID_CREDENTIALS");
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (ValidationException e) {
			response.setResponseCode(ResponseCode.OPERATION_UNSUCCESSFUL).setMessage("ERROR_INVALID_FIELDS")
					.setErrors(e.getErrorMap());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			response.setResponseCode(ResponseCode.UNKNOWN_ERROR).setMessage("ERROR_UNKNOWN");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			log.error("Error while updating user password.{}", e);
		}
		ResponseEntity<PtkResponse> res = new ResponseEntity<>(response, httpStatus);
		return res;
	}

	/**
	 * Updates the security question and answer of the user.
	 *
	 * @param body
	 *            required fields to update the password
	 * @return an instance of type {@link PtkResponse} encapsulating the situation
	 *         of what happened.
	 */
	@PutMapping("/question")
	public ResponseEntity<PtkResponse> updateSecurityQuestion(@RequestBody Map<String, String> body) {
		HttpStatus httpStatus;
		PtkResponse response = new PtkResponse();
		String email = body.get("email");
		String password = body.get("password");
		String question = body.get("question");
		String answer = body.get("answer");

		try {
			service.updateSecurityQuestion(email, password, question, answer);
			response.setResponseCode(ResponseCode.OPERATION_SUCCESSFUL)
					.setMessage("SUCCESS_SECURITY_QUESTION_ANSWER_CHANGED");
			httpStatus = HttpStatus.OK;
		} catch (InvalidCredentialsException e) {
			response.setResponseCode(ResponseCode.INVALID_CREDENTIALS).setMessage("ERROR_INVALID_CREDENTIALS");
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (ValidationException e) {
			response.setResponseCode(ResponseCode.OPERATION_UNSUCCESSFUL).setMessage("ERROR_INVALID_FIELDS")
					.setErrors(e.getErrorMap());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			response.setResponseCode(ResponseCode.UNKNOWN_ERROR).setMessage("ERROR_UNKNOWN");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			log.error("Error while updating user security question and answer.{}", e);
		}
		ResponseEntity<PtkResponse> res = new ResponseEntity<>(response, httpStatus);
		return res;
	}
}
