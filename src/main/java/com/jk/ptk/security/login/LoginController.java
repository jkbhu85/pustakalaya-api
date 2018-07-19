package com.jk.ptk.security.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ptk/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> login(@RequestBody LoginCredentials loginCred) {
		ResponseEntity<String> response;
		
		try {
			String jwt = loginService.login(loginCred);
			response = new ResponseEntity<>(jwt, HttpStatus.ACCEPTED);
		} catch (InvalidCredentialsException e) {
			response = new ResponseEntity<>("21:ERROR_LOGIN_UNSUCCESSFUL", HttpStatus.UNPROCESSABLE_ENTITY);			
		} catch (AccountLockedException e) {
			response = new ResponseEntity<>("22:ERROR_LOGIN_UNSUCCESSFUL", HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (AccountRevokedException e) {
			response = new ResponseEntity<>("23:ERROR_LOGIN_UNSUCCESSFUL", HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			response = new ResponseEntity<>("41:ERROR_UNKNOWN", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
