package com.jk.pustakalaya.f.newuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for entity {@link NewUser}.
 * 
 * @author Jitendra
 *
 */

@RestController
@RequestMapping("/api/newUser")
public class NewUserController {
	@Autowired
	private NewUserService service;

	/**
	 * Adds a new user to the system.
	 * 
	 * @param newUser
	 *            the speicified user to add to system.
	 */
	@PostMapping
	public void addNewUser(@RequestBody NewUser newUser) {
		if (newUser.getFirstName() != null) {
			throw new RuntimeException("test exception");
		}
		//service.addNewUser(newUser);
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
