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

/**
 * REST controller for entity {@link NewUser}.
 * 
 * @author Jitendra
 *
 */

@RestController
@RequestMapping("/api/newUser")
public class NewUserController {
	/*
	 * msg format:
	 * [SUCCESS|ERROR]_[ENTITY]_[OPERATION]_[SHORT_ERROR DESCRIPTION]
	 */
	private static final String MSG_SUCCESS_USER_ADD = "SUCCESS_NEWUSER_ADD";
	private static final String MSG_ERROR_USER_ADD = "ERROR_NEWUSER_ADD";
	private static final String MSG_ERROR_USER_ADD_UNKNOWN = "ERROR_NEWUSER_ADD_UNKNOWN";
	
	@Autowired
	private NewUserService service;

	/**
	 * Adds a new user to the system.
	 * 
	 * @param newUser
	 *            the speicified user to add to system.
	 */
	@PostMapping
	public ResponseEntity<String> addNewUser(@RequestBody NewUser newUser) {
		ResponseEntity<String> rs;
		
		try {
			service.addNewUser(newUser);
			rs = new ResponseEntity<>(MSG_SUCCESS_USER_ADD, HttpStatus.ACCEPTED);
		} catch (ValidationException e) {
			rs = new ResponseEntity<>(MSG_ERROR_USER_ADD + "_INVALID_" + e.getMessage(), HttpStatus.PRECONDITION_FAILED);
		} catch (Exception e) {
			rs = new ResponseEntity<>(MSG_ERROR_USER_ADD_UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return rs;
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
