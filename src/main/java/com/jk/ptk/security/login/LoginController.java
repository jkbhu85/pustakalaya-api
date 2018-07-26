package com.jk.ptk.security.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jk.ptk.app.response.PtkResponse;
import com.jk.ptk.app.response.ResponseCode;

@RestController
@RequestMapping("/ptk/login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping
	public ResponseEntity<PtkResponse> login(@RequestBody LoginCredentials loginCred) {
		PtkResponse response = new PtkResponse();
		HttpStatus status;

		try {
			String jwt = loginService.login(loginCred);
			response.setData(jwt);
			status = HttpStatus.ACCEPTED;
		} catch (InvalidCredentialsException e) {
			response
				.setResponseCode(ResponseCode.INVALID_CREDENTIALS)
				.setMessage("ERROR_LOGIN_UNSUCCESSFUL");
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (AccountLockedException e) {
			response
				.setResponseCode(ResponseCode.ACCOUNT_LOCKED)
				.setMessage("ERROR_LOGIN_UNSUCCESSFUL");
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (AccountRevokedException e) {
			response
				.setResponseCode(ResponseCode.ACCOUNT_ACCESS_REVOKED)
				.setMessage("ERROR_LOGIN_UNSUCCESSFUL");
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			response
				.setResponseCode(ResponseCode.UNKNOWN_ERROR)
				.setMessage("ERROR_UNKNOWN");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		ResponseEntity<PtkResponse> re = new ResponseEntity<>(response, status);
		return re;
	}
}
