package com.jk.ptk.f.newuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jk.ptk.app.ValidationException;
import com.jk.ptk.f.user.User;
import com.jk.ptk.util.mail.MailNotSentException;

/**
 * API endpoint to manipulate instances of type {@link NewUser}.
 *
 * @author Jitendra
 *
 */

@RestController
@RequestMapping("/ptk/newUser")
public class NewUserController {
	@Autowired
	private NewUserService service;

	/**
	 * Stores the specified instance to the storage.
	 *
	 * @param newUser
	 *            the speicified instance to be stored
	 */
	@PostMapping
	public ResponseEntity<String> addNewUser(@RequestBody NewUser newUser) {
		ResponseEntity<String> response;

		try {
			User acCreatedBy = null;
			newUser.setAcCreatedBy(acCreatedBy);

			service.addNewUser(newUser);

			response = new ResponseEntity<>("10:SUCCESS_NEWUSER_ADDED", HttpStatus.ACCEPTED);
		} catch (ValidationException e) {
			response = new ResponseEntity<>(e.getErrorCode() + ":ERROR_INVALID_" + e.getFieldName(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (MailNotSentException e) {
			response = new ResponseEntity<>("42:ERROR_EMAIL_NOT_SENT", HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			response = new ResponseEntity<>("41:ERROR_UNKNOWN", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	/**
	 * Returns new user associated with {@code id}.
	 *
	 * @param id
	 *            the speicifed id
	 * @return new user associated with {@code id}
	 */
	@GetMapping("/{id}")
	public NewUser getNewUser(@PathVariable("id") String id) {
		return service.getNewUser(id);
	}
}
